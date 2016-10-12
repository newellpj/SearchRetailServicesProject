package co.srsp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import co.srsp.viewmodel.BookReviewsModel;

@Controller
public class PushNotificationsController {

	@RequestMapping(value = { "/addSubscription"}, method = RequestMethod.GET)
	public @ResponseBody BookReviewsModel addSubscription(HttpServletRequest request, HttpServletResponse response){
		return null;
	}
	
	@RequestMapping(value = { "/removeSub"}, method = RequestMethod.GET)
	public @ResponseBody BookReviewsModel notifiySubscribersaddBookReview(HttpServletRequest request, HttpServletResponse response){
		return null;
	}
}
