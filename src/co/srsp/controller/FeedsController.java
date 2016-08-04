package co.srsp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import co.srsp.rss.handlers.RSSHandlerImpl;
import co.srsp.rss.handlers.RSSHandlerInterface;
import co.srsp.rss.model.FeedMessage;

@Controller
public class FeedsController {

	private final static Logger log = Logger.getLogger(FeedsController.class); 
	
	@RequestMapping(value = { "/getFeeds"}, method = RequestMethod.GET)
	public @ResponseBody FeedMessage[] getFeeds(HttpServletRequest request, HttpServletResponse response){
		log.info("in getFeeds...."+request.getParameter("rssFeedURL"));
		System.out.println("in getFeeds...."+request.getParameter("rssFeedURL"));
		RSSHandlerInterface rssHandler = new RSSHandlerImpl();
		return rssHandler.readRSSFeed(request.getParameter("rssFeedURL"));
	}
}
