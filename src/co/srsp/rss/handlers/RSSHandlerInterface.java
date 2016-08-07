package co.srsp.rss.handlers;

import javax.servlet.http.HttpSession;

import co.srsp.rss.model.FeedMessage;

public interface RSSHandlerInterface {

	public FeedMessage[] readRSSFeedPaginated(String URL, HttpSession session, boolean newSearch);
	
	public FeedMessage[] readRSSFeed(String URL);
	
	public FeedMessage[] readRSSFeed();
}
