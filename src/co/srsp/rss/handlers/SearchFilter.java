package co.srsp.rss.handlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import co.srsp.rss.model.FeedMessage;

public class SearchFilter {

	private final static Logger log = Logger.getLogger(SearchFilter.class); 
	
	public static final String SEARCH_TYPE_FEEDS = "SEARCH_TYPE_FEEDS";
	
	private String searchCriteria;
	private String[] searchTermsMatchedArray; //for granular multiple single term matches;
	private String searchType;
	private HashMap<String, Object> searchTypeToReturnObjectMapping;
	
	public SearchFilter(String searchCriteria, String searchType){
		this.searchCriteria = searchCriteria;
		this.searchType = searchType;
		searchTypeToReturnObjectMapping = new HashMap<String, Object>();
		searchTypeToReturnObjectMapping.put(SEARCH_TYPE_FEEDS, FeedMessage.class);
	}

	public String getSearchCriteria() {
		return searchCriteria;
	}

	public void setSearchCriteria(String searchCriteria) {
		this.searchCriteria = searchCriteria;
	}

	public String[] getSearchTermsMatchedArray() {
		return searchTermsMatchedArray;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public boolean addFeedData(FeedMessage dataToTest){

		log.info("searchCriteria.toLowerCase().trim() : "+searchCriteria.toLowerCase().trim());
		
		searchTermsMatchedArray = new String[1];
		
		if(dataToTest.getTitle().toLowerCase().contains(searchCriteria.toLowerCase().trim())){
			searchTermsMatchedArray[0] = searchCriteria;
			return true;
		}else if(dataToTest.getDescription().toLowerCase().contains(searchCriteria.toLowerCase().trim())){
			searchTermsMatchedArray[0] = searchCriteria;
			return true;
		}else if(dataToTest.getAuthor().toLowerCase().contains(searchCriteria.toLowerCase().trim())){
			searchTermsMatchedArray[0] = searchCriteria;
			return true;
		}else{
			
			String[] searchCriteriaSplit = searchCriteria.split(" ");
			
			log.info("log.info(searchCriteriaSplit); : "+searchCriteriaSplit);
			
			if(searchCriteriaSplit.length > 1){
				addFeedDataGranular(dataToTest, searchCriteriaSplit);
			}
			
			return false;
		}
	}
	
	private boolean addFeedDataGranular(FeedMessage dataToTest, String[] splitSearchCriteria){
		
		boolean somethingMatched = false;

		List<String> searchTermsMatched = new ArrayList<String>();
		
		int matchedCount = 0;
		
		for(int i=0; splitSearchCriteria.length >  i; i++){
			String singleSearchTerm = splitSearchCriteria[i].trim();
			
			log.info("singleSearchTerm : "+singleSearchTerm);
			
			if(singleSearchTerm != null && !"".equals(singleSearchTerm)){
				if(dataToTest.getTitle().toLowerCase().contains(singleSearchTerm.toLowerCase().trim())){
					somethingMatched = true;
					matchedCount++;
					searchTermsMatched.add(singleSearchTerm);
				}else if(dataToTest.getDescription().toLowerCase().contains(singleSearchTerm.toLowerCase().trim())){
					somethingMatched = true;
					searchTermsMatched.add(singleSearchTerm);
					matchedCount++;
				}else if(dataToTest.getAuthor().toLowerCase().contains(singleSearchTerm.toLowerCase().trim())){
					somethingMatched = true;
					searchTermsMatched.add(singleSearchTerm);
					matchedCount++;
				}
			}
		}
		
		searchTermsMatchedArray = new String[matchedCount];
		
		int count = 0;
		
		for(String searchTerm : searchTermsMatched){
			searchTermsMatchedArray[count] = searchTerm;
			count++;
		}
		
		return somethingMatched;
	}
}
