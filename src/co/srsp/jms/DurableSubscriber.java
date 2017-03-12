package co.srsp.jms;


import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DurableSubscriber implements Runnable, MessageListener{

    private static final Logger log = LoggerFactory
            .getLogger(DurableSubscriber.class);

    private static final String NO_GREETING = "no greeting";
    private boolean keepAlive = true;
    private String clientId;
    private Connection connection;
    private Session session;
    private MessageConsumer messageConsumer;

    private String subscriptionName;

    private static DurableSubscriber instance = null;
    
    public static DurableSubscriber getInstance(String clientId, String topicName, String subscriptionName) throws JMSException{
    	if(instance == null){
    		instance = new DurableSubscriber(clientId, topicName, subscriptionName);
    	}
    	
    	instance.startTopicListening();
    	
    	return instance;
    }
    
    private void startTopicListening(){
    	log.info("starting topic listening");
    	Thread t = new Thread(instance);
    	t.start();
    }
    
    private DurableSubscriber(String clientId, String topicName, String subscriptionName) throws JMSException {
        this.clientId = clientId;
        this.subscriptionName = subscriptionName;

        // create a Connection Factory
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                ActiveMQConnection.DEFAULT_BROKER_URL);

        // create a Connection
        connection = connectionFactory.createConnection();
        
        String clientID = connection.getClientID();
        
        //see if client ID already exists        
        if(clientID == null || !clientID.equalsIgnoreCase(clientId)){
        	connection.setClientID(clientId);
        }

        // create a Session
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        log.info("before create topic");
        // create the Topic from which messages will be received
        Topic topic = session.createTopic(topicName);
        log.info("after create topic");
        // create a MessageConsumer for receiving messages
        
        
        
        messageConsumer = session.createDurableSubscriber(topic,
                subscriptionName);

        // start the connection in order to receive messages
        connection.start();
    }

    public void removeDurableSubscriber() throws JMSException {
        messageConsumer.close();
        session.unsubscribe(subscriptionName);
    }

    public void closeConnection() throws JMSException {
    	//TODO terminate message checker thread
    	keepAlive = false;
        connection.close();
    }

    public String receiveMessage(int timeout) throws JMSException {

        String greeting = NO_GREETING;

        // read a message from the topic destination
        Message message = messageConsumer.receive(timeout);

        // check if a message was received
        if (message != null) {
            // cast the message to the correct type
            TextMessage textMessage = (TextMessage) message;

            // retrieve the message content
            String text = textMessage.getText();
            log.info(clientId + ":  received message with text='{}'", text);

            // create greeting
            greeting = "Hello " + text + "!";
        } else {
            log.info(clientId + ": no message received");
        }
        System.out.println("greeting={} "+greeting);
        log.info("greeting={}", greeting);
        return greeting;
    }

	@Override
	public void onMessage(Message message) {
		// TODO Auto-generated method stub
		//Message message = messageConsumer.receive(1000);
		log.info("onMessage !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		try{
			String msgTxt = "";
	        // check if a message was received
	        if (message != null) {
	            // cast the message to the correct type
	            TextMessage textMessage = (TextMessage) message;
	
	            // retrieve the message content
	            String text = textMessage.getText();
	            log.info(clientId + ":  received message with text='{}'", text);
	            System.out.println(clientId + ":  received message with text='{}' : "+text);
	            // create greeting
	            msgTxt = "Message Text is : " + text + "!";
	        } else {
	            log.info(clientId + ": no message received");
	        }
	        System.out.println("greeting={} : "+msgTxt);
	        log.info("greeting={}", msgTxt);
		}catch(Exception e){
			e.printStackTrace();
			log.error("error here : "+e.getMessage());
		}
	}

	@Override
	public void run() {

		while(keepAlive){
			
			try{
				Thread.sleep(30000);
				receiveMessage(500);
			}catch(InterruptedException te){
				te.printStackTrace();
			    log.error("error while thread sleeping "+te.getMessage());
			}catch(JMSException je){
				je.printStackTrace();
			    log.error("error while checking for message on topic "+je.getMessage());
			}
				
		}
	}
}