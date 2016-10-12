package co.srsp.controller;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

import co.srsp.config.ConfigHandler;
import co.srsp.hibernate.orm.NotificationSubscribers;
import co.srsp.service.NotificationsService;
import co.srsp.viewmodel.BookReviewsModel;


@Controller
public class PushNotificationsController {


	
	private final static Logger log = Logger.getLogger(PushNotificationsController.class); 
	
	//method for storing 
	@RequestMapping(value = { "/addRemoveSubscription"}, method = RequestMethod.GET)
	public @ResponseBody String[] addRemoveSubscription(HttpServletRequest request, HttpServletResponse response){
		log.info("addSubscription !!!!");
		log.info("statusType :: "+request.getParameter("statusType"));
		log.info("username :: "+request.getParameter("username"));
		log.info("endpoint :: "+request.getParameter("endpoint"));
		log.info("subscriptionId :: "+request.getParameter("subscriptionId"));	
		log.info("key :: "+request.getParameter("key"));
		
		NotificationsService notificationServices = new NotificationsService();
		
		NotificationSubscribers notificationSubscribers = new NotificationSubscribers();
		//notificatioSubscibers.setIdnotification_subscribers(idnotification_subscribers);
		notificationSubscribers.setEvent_name("ALL");
		
		String subscriberID = request.getParameter("endpoint");
		
		if(subscriberID != null){
			subscriberID = subscriberID.substring(subscriberID.lastIndexOf("/")+1);
		}else{
			String[] returnArray = new String[1];
			returnArray[0] = "no subscriber ID could be found";
			return returnArray;
		}
		
		notificationSubscribers.setSubscriptionID(subscriberID);
		notificationSubscribers.setUsername(request.getParameter("username"));
		
		String status = request.getParameter("statusType");
		
		if("subscribe".equalsIgnoreCase(status)){
			notificationServices.save(notificationSubscribers);
		}else{
			notificationServices.delete(notificationSubscribers);
		}
		
		String[] returnArray = new String[1];
		returnArray[0] = "You have been successfully "+status;
		
		return returnArray;
	}
	
	private class FCMSender extends Sender {

	    public FCMSender(String key) {
	        super(key);
	    }

	    @Override
	    protected HttpURLConnection getConnection(String url) throws IOException {
	        String fcmUrl = "https://fcm.googleapis.com/fcm/send";
	        return (HttpURLConnection) new URL(fcmUrl).openConnection();
	    }
	}
	
	//TODO
	//this will generate notification for sake of testing - normally have a poller that checks for updates to notify subscribers
	//event to notify and then retrieve list of subscribers
	public void sendNotificationsToSubscribers(){
		//Server key:	AIzaSyC_twu_3RmawPiC6hL4h5KTAb0sz33HaNQ
		//Sen
		
		
//park this for now
		String rootURL = ConfigHandler.getInstance().readApplicationProperty("rootFirebaseURL");
		String key = ConfigHandler.getInstance().readApplicationProperty("firebaseServerKey");
		
		  try {
              Sender sender = new FCMSender(key);
              Message message = new Message.Builder()
                                .collapseKey("message")
                                .timeToLive(3)
                                .delayWhileIdle(true)
                                .addData("message", "Test Notification from Java application")
                                .build();  

            //   Use the same token(or registration id) that was earlier
             //  used to send the message to the client directly from
             //  Firebase Console's Notification tab.
                                
            //registration IDs.
                                
              Result result = sender.send(message,
          "dRzT9QLVFQo:APA91bGNAhAXoSFr0uuvrMKp5eWhU5WzOt7TwwrP_qrIHZmPiuROG4fNDhfpdU3Ri3dCwMOM_qonacmkc0Vw-dIqoUkacHDQJsjyxEjBqg6r-aAof9uTcB_58Y931D6HZjmXi0l0z4AQ",
                  1);
              System.out.println("Result: " + result.toString());
          } catch (Exception e) {
              e.printStackTrace();
          }
		
		
		
		
		//String ContentType = "application/json"; 
		rootURL += "";
	
		
//		try{
//			URL obj = new URL(rootURL);
//			HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
//			con.setRequestMethod("POST");
//			
//			con.setDoOutput(true);
//			con.setDoInput(true);
//			con.setRequestProperty("User-Agent", "Mozilla/5.0");
//			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
//			con.setRequestProperty("Content-Type", "application/json");
//			con.setRequestProperty("Accept", "application/json");
//			con.setRequestProperty("Authorization:", key="+key");
//		
//			JSONObject parent = new JSONObject();
//			JSONObject cred   = new JSONObject();
//			JSONObject subIDs = new JSONObject();
//			
//			
//			String[] subIDArray = new String[2]; //TODO 2 for now just to test
//			subIDArray[0] = "dRzT9QLVFQo:APA91bGNAhAXoSFr0uuvrMKp5eWhU5WzOt7TwwrP_qrIHZmPiuROG4fNDhfpdU3Ri3dCwMOM_qonacmkc0Vw-dIqoUkacHDQJsjyxEjBqg6r-aAof9uTcB_58Y931D6HZjmXi0l0z4AQ";
//			subIDArray[1] = "dRzT9QLVFQo:APA91bGNAhAXoSFr0uuvrMKp5eWhU5WzOt7TwwrP_qrIHZmPiuROG4fNDhfpdU3Ri3dCwMOM_qonacmkc0Vw-dIqoUkacHDQJsjyxEjBqg6r-aAof9uTcB_58Y931D6HZjmXi0l0z4AQ";
//			
//			subIDs.put("registration_ids", subIDArray);
//			
//
//			String urlParameters = "";
//			parent.put("cred", cred.toString());
//
//			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
//			wr.writeBytes(urlParameters);
//			wr.flush();
//			wr.close();
//			
//			int responseCode = con.getResponseCode();
//			log.info("\nSending 'POST' request to URL : " + rootURL);
//			log.info("Post parameters : " + urlParameters);
//			log.info("Response Code : " + responseCode);
//			
//			
//			HttpClient client = new DefaultHttpClient();
//	
//			
//		}catch(Exception e){
//			e.printStackTrace();
//			log.error(e.getMessage());
//		}
		
		
		//https://android.googleapis.com/gcm/send/e7ze8FD3Itg:APA91bHG8GvyNo24jQPksssRQkqZyF2cGzbJGmJpfgn8qgzDCJ07eVFOoV7T2S96jdj6qKnWUfwQJhO9HXSYc78PkgkuZDDuQoibEzavWii5JQF5R-VPSJvUQ_9anIqaugjTVHDgCzaw
		
	}
	
	@RequestMapping(value = { "/removeSub"}, method = RequestMethod.GET)
	public @ResponseBody BookReviewsModel notifiySubscribers(HttpServletRequest request, HttpServletResponse response){
		
		
		
		return null;
	}
	
	public static void main(String args[]){
		
		PushNotificationsController controller = new PushNotificationsController();
		controller.sendNotificationsToSubscribers();
	}
}
