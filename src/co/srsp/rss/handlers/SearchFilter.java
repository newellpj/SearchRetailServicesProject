package co.srsp.rss.handlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import org.apache.log4j.Logger;

import co.srsp.rss.model.FeedMessage;
import co.srsp.rss.model.ReturnMessageInterface;

public class SearchFilter {

	private final static Logger log = Logger.getLogger(SearchFilter.class); 
	
	public static final String SEARCH_TYPE_FEEDS = "SEARCH_TYPE_FEEDS";
	
	private String searchCriteria;
	private String searchType;
	private HashMap<String, Object> searchTypeToReturnObjectMapping;
	
	public SearchFilter(String searchCriteria, String searchType){
		this.searchCriteria = searchCriteria;
		this.searchType = searchType;
		searchTypeToReturnObjectMapping = new HashMap<String, Object>();
		searchTypeToReturnObjectMapping.put(SEARCH_TYPE_FEEDS, FeedMessage.class);
	}

	
	public boolean addFeedData(FeedMessage dataToTest){
		//searchCriteria;
		
		//log.info("filter array size to FILTER : "+dataToFilter.length);
		log.info("searchCriteria.toLowerCase().trim() : "+searchCriteria.toLowerCase().trim());
		//FeedMessage[] filteredFeedMessageArray = new FeedMessage[dataToFilter.length];
		
		int count = 0;
		
		//for(FeedMessage feedMessage : dataToFilter){
			
			boolean addItem = false;
			
			if(dataToTest.getTitle().toLowerCase().contains(searchCriteria.toLowerCase().trim())){
				//filteredFeedMessageArray[count] = feedMessage;
				//count++;
				return true;
			}else if(dataToTest.getDescription().toLowerCase().contains(searchCriteria.toLowerCase().trim())){
				//filteredFeedMessageArray[count] = feedMessage;
				//count++;
				return true;
			}else if(dataToTest.getAuthor().toLowerCase().contains(searchCriteria.toLowerCase().trim())){
				//filteredFeedMessageArray[count] = feedMessage;
				//count++;
				return true;
			}else{
				return false;
			}
			
	}
}
