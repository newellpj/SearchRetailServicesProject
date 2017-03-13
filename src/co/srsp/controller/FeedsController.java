package co.srsp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import co.srsp.markup.handlers.RSSHandlerImpl;
import co.srsp.markup.handlers.RSSHandlerInterface;
import co.srsp.markup.handlers.SearchFilter;
import co.srsp.rss.model.FeedMessage;

@Controller
public class FeedsController {

	private final static Logger log = Logger.getLogger(FeedsController.class); 
	
	@RequestMapping(value = { "/getFeeds"}, method = RequestMethod.GET)
	public @ResponseBody FeedMessage[] getFeeds(HttpServletRequest request, HttpServletResponse response){
		log.info("in getFeeds...."+request.getParameter("rssFeedURL"));
		System.out.println("in getFeeds...."+request.getParameter("rssFeedURL"));
		RSSHandlerInterface rssHandler = new RSSHandlerImpl();
		String searchCriteria = request.getParameter("searchCriteria");
		log.info("search criteria : "+searchCriteria);
		if(searchCriteria != null && !"".equals(searchCriteria)){		
			log.info("in here for search criteria value: "+searchCriteria);
			SearchFilter searchFilter = new SearchFilter(searchCriteria, SearchFilter.SEARCH_TYPE_FEEDS);
			return rssHandler.readRSSFeedPaginatedSync(request.getParameter("rssFeedURL"), request.getSession(), true, true, searchFilter, -1);
		}else{
			return rssHandler.readRSSFeedPaginatedSync(request.getParameter("rssFeedURL"), request.getSession(), true, true, null, -1);
		}
		
		
	}
	
	@RequestMapping(value = { "/getPaginatedFeed"}, method = RequestMethod.GET)
	public @ResponseBody FeedMessage[] getPaginatedFeed(HttpServletRequest request, HttpServletResponse response){
		log.info("in getPaginatedFeed...."+request.getParameter("rssFeedURL"));
		System.out.println("in getPaginatedFeed...."+request.getParameter("rssFeedURL"));
		RSSHandlerInterface rssHandler = new RSSHandlerImpl();
		boolean paginateForward = Boolean.parseBoolean(request.getParameter("_paginateForward").toString());
		String searchCriteria = request.getParameter("searchCriteria");
		
		if(searchCriteria != null && !"".equals(searchCriteria)){
			log.info("in here for search criteria value: "+searchCriteria);
			SearchFilter searchFilter = new SearchFilter(searchCriteria, SearchFilter.SEARCH_TYPE_FEEDS);		
			return rssHandler.readRSSFeedPaginatedSync(request.getParameter("rssFeedURL"), request.getSession(), false, paginateForward, searchFilter, -1);
		}else{
			return rssHandler.readRSSFeedPaginatedSync(request.getParameter("rssFeedURL"), request.getSession(), false, paginateForward, null, -1);
		}
		
		
	}
}
