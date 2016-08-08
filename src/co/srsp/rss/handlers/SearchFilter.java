package co.srsp.rss.handlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import co.srsp.rss.model.FeedMessage;
import co.srsp.rss.model.ReturnMessageInterface;

public class SearchFilter {

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
	
	public ReturnMessageInterface[] filterData(ReturnMessageInterface[] dataToFilter){
		
		switch(searchType){
			case SEARCH_TYPE_FEEDS:
				return filterFeedData((FeedMessage[])dataToFilter);
			default:
				break;
		}
		
		return dataToFilter;
	}
	
	private FeedMessage[] filterFeedData(FeedMessage[] dataToFilter){
		//searchCriteria;
		
		List<FeedMessage> filteredFeedMessageArr = new ArrayList<FeedMessage>();
		
		for(FeedMessage feedMessage : dataToFilter){
			
			boolean addItem = false;
			
			if(feedMessage.getTitle().toLowerCase().contains(searchCriteria.toLowerCase())){
				filteredFeedMessageArr.add(feedMessage);
			}else if(feedMessage.getDescription().toLowerCase().contains(searchCriteria.toLowerCase())){
				filteredFeedMessageArr.add(feedMessage);
			}else if(feedMessage.getAuthor().toLowerCase().contains(searchCriteria.toLowerCase())){
				filteredFeedMessageArr.add(feedMessage);
			}
		}
		
		
		return (FeedMessage[])filteredFeedMessageArr.toArray();
	}
}
