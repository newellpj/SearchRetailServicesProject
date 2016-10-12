package co.srsp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import co.srsp.config.ConfigHandler;
import co.srsp.hibernate.orm.NotificationSubscribers;
import co.srsp.service.NotificationsService;
import co.srsp.viewmodel.BookReviewsModel;

@Controller
public class PushNotificationsController {

	private final static Logger log = Logger.getLogger(PushNotificationsController.class); 
	
	//method for storing 
	@RequestMapping(value = { "/addSubscription"}, method = RequestMethod.GET)
	public @ResponseBody String[] addSubscription(HttpServletRequest request, HttpServletResponse response){
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
		notificationServices.save(notificationSubscribers);
		
		String[] returnArray = new String[1];
		returnArray[0] = "You have been successfully subscribed";
		
		return returnArray;
	}
	
	//this will generate notification for sake of testing - normally have a poller that checks for updates to notify subscribers
	
	private void sendNotificationsToSubscribers(){
		//Server key:	AIzaSyC_twu_3RmawPiC6hL4h5KTAb0sz33HaNQ
		//Sender ID:	954512341945
		
		String rootURL = ConfigHandler.getInstance().readApplicationProperty("rootFirebaseURL");
		String key = ConfigHandler.getInstance().readApplicationProperty("firebaseServerKey");
		String ContentType = "application/json"; 
		rootURL += "";
		
		//ttps://android.googleapis.com/gcm/send/e7ze8FD3Itg:APA91bHG8GvyNo24jQPksssRQkqZyF2cGzbJGmJpfgn8qgzDCJ07eVFOoV7T2S96jdj6qKnWUfwQJhO9HXSYc78PkgkuZDDuQoibEzavWii5JQF5R-VPSJvUQ_9anIqaugjTVHDgCzaw
		
	}
	
	@RequestMapping(value = { "/removeSub"}, method = RequestMethod.GET)
	public @ResponseBody BookReviewsModel notifiySubscribersaddBookReview(HttpServletRequest request, HttpServletResponse response){
		return null;
	}
}
