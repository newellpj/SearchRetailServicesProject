package co.srsp.jms;

	import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


	public class Publisher {

	    private static final Logger log = LoggerFactory
	            .getLogger(Publisher.class);

	    private String clientId;
	    private Connection connection;
	    private Session session;
	    private MessageProducer messageProducer;
	    
	    private static Publisher instance = null;
	    
	    public static Publisher getInstance(String clientId, String topicName){
	    	if(instance == null){
	    		instance = new Publisher(clientId, topicName);
	    	}
	    	
	    	return instance;
	    }
	    
	    
	    private Publisher(String clientId, String topicName) {
	        this.clientId = clientId;

	        // create a Connection Factory
	     

	        // create a Connection
	        try{
	        	
	           ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
	   	                ActiveMQConnection.DEFAULT_BROKER_URL);
	        	
		        connection = connectionFactory.createConnection();
		        
		        String clientID = connection.getClientID();
		        
		        //see if client ID already exists
		        
		        if(clientID == null || !clientID.equalsIgnoreCase(clientId)){
		        	connection.setClientID(clientId);
		        }
		        
		        // create a Session
		        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		        // create the Topic to which messages will be sent
		        
		        log.info("before create topic");
		        Topic topic = session.createTopic(topicName);
		        log.info("after create topic");
		        // create a MessageProducer for sending messages
		        messageProducer = session.createProducer(topic);
		        
	        }catch(JMSException jmse){
	        	log.error(jmse.getMessage());
	        	jmse.printStackTrace();
	        }

	    }

	    public void closeConnection() throws JMSException {
	        connection.close();
	    }

	    public void sendName(String firstName, String lastName) throws JMSException {
	        String text = firstName + " " + lastName;

	        // create a JMS TextMessage
	        TextMessage textMessage = session.createTextMessage(text);

	        // send the message to the topic destination
	        messageProducer.send(textMessage);
	        System.out.println(clientId + ": sent message with text='{}' "+text);
	        log.info(clientId + ": sent message with text='{}'", text);
	    }
}

