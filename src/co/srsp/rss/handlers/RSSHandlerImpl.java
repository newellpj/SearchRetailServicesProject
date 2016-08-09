package co.srsp.rss.handlers;

import java.awt.Image;
import java.net.URL;
import java.util.HashMap;

import javax.servlet.http.HttpSession;
import javax.swing.ImageIcon;

import org.apache.log4j.Logger;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import co.srsp.rss.model.FeedMessage;

public class RSSHandlerImpl implements RSSHandlerInterface {

	private final static Logger log = Logger.getLogger(RSSHandlerImpl.class); 
	
	
	
	
	@Override
	public FeedMessage[] readRSSFeedPaginated(String feedUrl, HttpSession session, boolean newSearch, boolean paginateForward, SearchFilter searchFilter){
		SyndFeedInput input = new SyndFeedInput();
		FeedMessage[] feedArr = null;
		FeedMessage feedMsg = null;		
				
		try{
	         
	         int sizeOfArrayToReturn = 0;
	         
			 int rssPaginationOffset = -1;
			
			 SyndFeed feed = null;
			
			if(!newSearch){ //this if for paginating large feeds
				log.info("feed found in session!!!!!@@ NOT a NEW SEARCH");
				rssPaginationOffset = (session.getAttribute("rssPaginationOffset") != null) ? Integer.parseInt(session.getAttribute("rssPaginationOffset").toString()) : 0;
				feed = (SyndFeed)session.getAttribute("feed");
				
				if(!paginateForward && rssPaginationOffset >= 20){
					rssPaginationOffset = rssPaginationOffset - 20;
				}

				int feedSize = feed.getEntries().size(); 
				sizeOfArrayToReturn = ((feedSize - rssPaginationOffset) < 10 ) ? (feedSize - rssPaginationOffset) : 10;
				feedArr = new FeedMessage[sizeOfArrayToReturn];
				
			}else{
				log.info(" THIS IS A new search");
				rssPaginationOffset = 0;
				
				if(feedUrl.contains(",")){ //is an ALL search
					String[] feedURLArr = feedUrl.split(",");
					
					log.info("feed url in array "+feedURLArr[0]);
					
					for(int i=0; feedURLArr.length > i; i++){
						SyndFeed tempFeed = input.build(new XmlReader(new URL(feedURLArr[i])));
						
						if(i == 0){
							feed = tempFeed;
						}else{
							feed.getEntries().addAll(tempFeed.getEntries());
						}
					}
				}else{
					feed = input.build(new XmlReader(new URL(feedUrl)));
				}
				
				if(feed.getEntries().size() > 10){
					sizeOfArrayToReturn = 10;
		         }else{
		        	 sizeOfArrayToReturn = feed.getEntries().size();
		         }
				feedArr = new FeedMessage[sizeOfArrayToReturn];
			}
			
			log.info("rss pagination offset : "+rssPaginationOffset);
			log.info("size of array : "+sizeOfArrayToReturn);
			log.info(feed.getEntries().subList(rssPaginationOffset, rssPaginationOffset+sizeOfArrayToReturn).size());
			
			
			//TODO overall count and found count
			//TODO match items then add overall count to offset
			int overallCount = 0;
			 int matchedCount = 0;
			for(SyndEntry syndEntry : feed.getEntries().subList(rssPaginationOffset, feed.getEntries().size())){
				
				feedMsg = new FeedMessage();
				feedMsg.setAuthor(syndEntry.getAuthor());
				feedMsg.setTitle(syndEntry.getTitle());
				
				log.info("description value is : "+syndEntry.getDescription().getValue());
				
				feedMsg.setDescription(syndEntry.getDescription().getValue());
				feedMsg.setLink(syndEntry.getLink());

				if(syndEntry.getEnclosures() != null && syndEntry.getEnclosures().size() > 0){
					
					feedMsg.setUrl(syndEntry.getEnclosures().get(0).getUrl());
					
					Image image = new ImageIcon(new URL(feedMsg.getUrl())).getImage();
					int imgWidth = image.getWidth(null);
					int imgHeight = image.getHeight(null);
					
					log.info("imgWidth : "+imgWidth);
					log.info("imgHeight : "+imgHeight);
					
					if(imgWidth > imgHeight){
						double result = new Double(imgHeight)/ new Double(imgWidth);
						log.info("result : "+result);
						imgHeight = (int)(result * new Double(320));
						imgWidth = 320;
					}else if(imgWidth < imgHeight){
						double result = new Double(imgWidth)/ new Double(imgHeight);
						imgWidth = (int)(result * new Double(320));
						imgHeight = 320;
					}else{
						imgHeight = 320;
						imgWidth  = 320;
					}

					log.info("imgWidth 2 : "+imgWidth);
					log.info("imgHeight 2: "+imgHeight);
					
					log.info("feed entries size ::: "+feed.getEntries().size());
					
					feedMsg.setImageWidth(String.valueOf(imgWidth));
					feedMsg.setImageHeight(String.valueOf(imgHeight));
					feedMsg.setTotalFeedCount(feed.getEntries().size());
					feedMsg.setCurrentPaginationOffset(rssPaginationOffset+10);
				}
				
				//apply for search filter
				
				if(searchFilter != null && searchFilter.addFeedData(feedMsg)){ //user has enetered text so need to match records
					feedArr[matchedCount] = feedMsg;		
					matchedCount++;
				}else if(searchFilter == null){ //no search criteria entered return all that exist either 10 or total size of feed list returned.
					feedArr[matchedCount] = feedMsg;		
					matchedCount++;
				}
				
				overallCount++;
				
				if(matchedCount >= 10){ //found 10 that match search criteria
					break;
				}
			}
			
			//now remove any null entries in the array
			
			int removalCountIndex = -1;
			
			for(int i=0; feedArr.length > i; i++){
				if(feedArr[i] == null){
					removalCountIndex = i;
					break;
				}
			}
			
			FeedMessage[] feedMessageArrayToReturn = null;
			
			if(removalCountIndex > -1){
				feedMessageArrayToReturn = new FeedMessage[removalCountIndex];
				System.arraycopy(feedArr, 0, feedMessageArrayToReturn, 0, removalCountIndex);
			}else{
				feedMessageArrayToReturn = feedArr;
			}
			
			session.setAttribute("rssPaginationOffset", rssPaginationOffset+overallCount);
			session.setAttribute("feed", feed);
			log.info("returning feed array");
			return feedMessageArrayToReturn;
			
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage());
		}
		
		return feedArr = new FeedMessage[0];
	}
	
	
	public FeedMessage[] readRSSFeed(String feedUrl) {
		return readRSSFeedPaginated(feedUrl, null, true, true, null);
	}

	@Override
	public FeedMessage[] readRSSFeed() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public static void main(String[] args) {
        boolean ok = false;
     
        try {
            URL feedUrl = new URL("https://www.nasa.gov/rss/dyn/lg_image_of_the_day.rss");

            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(feedUrl));

            System.out.println("The feed is "+feed);
            System.out.println("The feed is "+feed.getEntries().size());
            ok = true;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("ERROR: "+ex.getMessage());
        }
        

        if (!ok) {
            System.out.println();
            System.out.println("FeedReader reads and prints any RSS/Atom feed type.");
            System.out.println("The first parameter must be the URL of the feed to read.");
            System.out.println();
        }
    }
}
