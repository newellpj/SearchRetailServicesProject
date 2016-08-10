package co.srsp.rss.handlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import co.srsp.config.ConfigHandler;
import co.srsp.rss.model.FeedMessage;

public class SearchFilter {

	private final static Logger log = Logger.getLogger(SearchFilter.class); 
	
	public static final String SEARCH_TYPE_FEEDS = "SEARCH_TYPE_FEEDS";
	
	private String searchCriteria;
	private List<String> searchTermsMatchedArrayList = new ArrayList<String>(); //for granular multiple single term matches;
	private String searchTermsMatched = "";
	private String searchType;
	private HashMap<String, Object> searchTypeToReturnObjectMapping;
	private List<String> stopWordsList = new ArrayList<Srring>();
	
	public SearchFilter(String searchCriteria, String searchType){
		this.searchCriteria = searchCriteria;
		this.searchType = searchType;
		searchTypeToReturnObjectMapping = new HashMap<String, Object>();
		searchTypeToReturnObjectMapping.put(SEARCH_TYPE_FEEDS, FeedMessage.class);
		
		stopWordsList = (List<String>)ConfigHandler.getInstance().readCSVFile("./presentationResources/stopwords.txt", ConfigHandler.LIST_RETURN_TYPE);
	
		
	}

	public String getSearchCriteria() {
		return searchCriteria;
	}

	public void setSearchCriteria(String searchCriteria) {
		this.searchCriteria = searchCriteria;
	}

	public String getSearchTermsMatchedArray() {

		for(String searchTerm : searchTermsMatchedArrayList){
			searchTermsMatched = searchTermsMatched +" "+searchTerm;
		}

		return searchTermsMatched.trim();
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public boolean addFeedData(FeedMessage dataToTest){

		log.info("searchCriteria.toLowerCase().trim() : "+searchCriteria.toLowerCase().trim());
 
		
		if(dataToTest.getTitle().toLowerCase().contains(searchCriteria.toLowerCase().trim())){
			searchTermsMatched = searchCriteria;
			return true;
		}else if(dataToTest.getDescription().toLowerCase().contains(searchCriteria.toLowerCase().trim())){
			searchTermsMatched = searchCriteria;
			return true;
		}else if(dataToTest.getAuthor().toLowerCase().contains(searchCriteria.toLowerCase().trim())){
			searchTermsMatched = searchCriteria;
			return true;
		}else{
			//if phrase or terms doesn't match split and try and match any of the single words against the returned dataset
			String[] searchCriteriaSplit = searchCriteria.split(" ");
			
			log.info("log.info(searchCriteriaSplit); : "+searchCriteriaSplit);
			
			if(searchCriteriaSplit.length > 1){
				return addFeedDataGranular(dataToTest, searchCriteriaSplit);
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
			
			if(singleSearchTerm != null && !"".equals(singleSearchTerm) && !stopWordsList.contains(singleSearchTerm)){
				if(dataToTest.getTitle().toLowerCase().contains(singleSearchTerm.toLowerCase().trim())){
					somethingMatched = true;
					matchedCount++;
					
					if(!searchTermsMatchedArrayList.contains(singleSearchTerm)){
						searchTermsMatchedArrayList.add(singleSearchTerm);
					}

				}else if(dataToTest.getDescription().toLowerCase().contains(singleSearchTerm.toLowerCase().trim())){
					somethingMatched = true;
					if(!searchTermsMatchedArrayList.contains(singleSearchTerm)){
						searchTermsMatchedArrayList.add(singleSearchTerm);
					}
					matchedCount++;
				}else if(dataToTest.getAuthor().toLowerCase().contains(singleSearchTerm.toLowerCase().trim())){
					somethingMatched = true;
					if(!searchTermsMatchedArrayList.contains(singleSearchTerm)){
						searchTermsMatchedArrayList.add(singleSearchTerm);
					}
					matchedCount++;
				}
			}
		}
		
		log.info("matchedCount : "+matchedCount);

		return somethingMatched;
	}
}
