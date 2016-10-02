package co.srsp.markup.handlers;

import javax.servlet.http.HttpSession;

import co.srsp.rss.model.FeedMessage;

public interface RSSHandlerInterface {

	public FeedMessage[] readRSSFeedPaginated(String URL, HttpSession session, boolean newSearch, 
											boolean paginateForward, SearchFilter searchFilter);
	
	public FeedMessage[] readRSSFeed(String URL);
	
	public FeedMessage[] readRSSFeed();
}
