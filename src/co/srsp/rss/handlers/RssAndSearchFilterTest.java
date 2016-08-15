package co.srsp.rss.handlers;

import org.junit.Test;
import org.springframework.mock.web.MockHttpSession;
import static org.junit.Assert.*;
import co.srsp.rss.model.FeedMessage;

public class RssAndSearchFilterTest {

	@Test
	public void testReadRSSFeedNoTextSearchWithPagination(){
		RSSHandlerInterface rssHandler = new RSSHandlerImpl();
		MockHttpSession mockSession = new MockHttpSession();
		FeedMessage[] feedMessages = rssHandler.readRSSFeedPaginated("https://www.nasa.gov/rss/dyn/lg_image_of_the_day.rss", mockSession, true, true, null);
		System.out.println("feed messages length : "+feedMessages.length);
		assertNotNull(feedMessages);
		assertTrue(feedMessages.length > 0);
		assertTrue(feedMessages.length == 10);
		assertEquals(10, Integer.parseInt(mockSession.getAttribute("rssPaginationOffset").toString()));	
		feedMessages = rssHandler.readRSSFeedPaginated("https://www.nasa.gov/rss/dyn/lg_image_of_the_day.rss", mockSession, true, true, null);
		System.out.println("second feed messages length : "+feedMessages.length);
		assertNotNull(feedMessages);
		assertTrue(feedMessages.length > 0);
		assertTrue(feedMessages.length == 10);
	}
	
	@Test
	public void testReadRSSFeedTextSearchWithPagination(){
		RSSHandlerInterface rssHandler = new RSSHandlerImpl();
		MockHttpSession mockSession = new MockHttpSession();	
		SearchFilter searchFilter = new SearchFilter("NASA space", SearchFilter.SEARCH_TYPE_FEEDS);
		FeedMessage[] feedMessages = rssHandler.readRSSFeedPaginated("https://www.nasa.gov/rss/dyn/lg_image_of_the_day.rss", mockSession, true, true, searchFilter);
		System.out.println("Feed messages length : "+feedMessages.length);
		assertNotNull(feedMessages);
		assertTrue(feedMessages.length > 0);
		assertTrue(10 < Integer.parseInt(mockSession.getAttribute("rssPaginationOffset").toString()));
		assertTrue(20 > Integer.parseInt(mockSession.getAttribute("rssPaginationOffset").toString()));
		feedMessages = rssHandler.readRSSFeedPaginated("https://www.nasa.gov/rss/dyn/lg_image_of_the_day.rss", mockSession, true, true, null);
		System.out.println("Second feed messages length : "+feedMessages.length);
		assertNotNull(feedMessages);
		assertTrue(feedMessages.length > 0);
	
	}
	

	
}
