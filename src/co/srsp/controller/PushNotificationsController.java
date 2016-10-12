package co.srsp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import co.srsp.viewmodel.BookReviewsModel;

@Controller
public class PushNotificationsController {

	private final static Logger log = Logger.getLogger(PushNotificationsController.class); 
	
	//method for storing 
	@RequestMapping(value = { "/addSubscription"}, method = RequestMethod.GET)
	public @ResponseBody BookReviewsModel addSubscription(HttpServletRequest request, HttpServletResponse response){
		log.info("addSubscription !!!!");
		log.info("statusType :: "+request.getParameter("statusType"));
		log.info("username :: "+request.getParameter("username"));
		log.info("endpoint :: "+request.getParameter("endpoint"));
		log.info("subscriptionId :: "+request.getParameter("subscriptionId"));	
		log.info("key :: "+request.getParameter("key"));
		return null;
	}
	
	@RequestMapping(value = { "/removeSub"}, method = RequestMethod.GET)
	public @ResponseBody BookReviewsModel notifiySubscribersaddBookReview(HttpServletRequest request, HttpServletResponse response){
		return null;
	}
}