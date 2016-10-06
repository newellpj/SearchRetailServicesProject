
package co.srsp.controller;

import java.awt.Image;
import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.ImageIcon;

import org.apache.log4j.Logger;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.tika.config.TikaConfig;
import org.apache.tika.detect.DefaultDetector;
import org.apache.tika.detect.Detector;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import co.srsp.config.ConfigHandler;
import co.srsp.constants.SessionConstants;
import co.srsp.hibernate.orm.BookReviews;
import co.srsp.hibernate.orm.Books;
import co.srsp.markup.handlers.HTMLHelper;
import co.srsp.service.BooksAndReviewsService;
import co.srsp.service.SolrSearchService;
import co.srsp.solr.SolrSearchData;
import co.srsp.viewmodel.BookReviewsModel;
import co.srsp.viewmodel.HTMLModel;

@Controller
public class SolrAndDbSearchingPageController {

	private final static Logger log = Logger.getLogger(SolrAndDbSearchingPageController.class); 
	
	@RequestMapping(value = { "/reviews"}, method = RequestMethod.GET)
	public ModelAndView welcomePage() {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		log.info("we getting in here user logged in here - "+auth.getName());
		ModelAndView model = new ModelAndView();		 
		model.setViewName("reviews");
		return model;
	}
	
	@RequestMapping(value = { "/reviewsAddBook"}, method = RequestMethod.GET)
	public ModelAndView addBookPage() {
		log.info("we getting in here reviewsAddBook?");
		ModelAndView model = new ModelAndView();		
		model.setViewName("reviewsAddBook");
		return model;
	}
	
	@RequestMapping(value = { "/searchNASA"}, method = RequestMethod.GET)
	public ModelAndView addSearchAllPage() {
		log.info("we getting in here searchNASA page?");
		ModelAndView model = new ModelAndView();		
		model.setViewName("searchNASA");
		return model;
	}

	@RequestMapping(value = { "/reviewsSearchBook"}, method = RequestMethod.GET)
	public ModelAndView addSearchPage() {
		log.info("we getting in here reviewsSearchBook?");
		ModelAndView model = new ModelAndView();		
		model.setViewName("reviewsSearchBook");
		return model;
	}
	
	@RequestMapping(value = { "/reviewsSearchDocs"}, method = RequestMethod.GET)
	public ModelAndView addDocsSearchPage() {
		log.info("we getting in here addDocsSearchPage?");
		ModelAndView model = new ModelAndView();		
		model.setViewName("reviewsSearchDocs");
		return model;
	}
	
	@RequestMapping(value = { "/reviewsReviewBookNoneAdded"}, method = RequestMethod.GET)
	public ModelAndView reviewsReviewBookNoneAdded(HttpServletRequest request, HttpServletResponse response) {
		log.info("we getting in here reviewsReviewBookNoneAdded?");
		ModelAndView model = new ModelAndView();
		
		request.getSession().setAttribute("bookAuthorFound", "");
		request.getSession().setAttribute("bookTitleFound", "");

		model.setViewName("reviewsReviewBook");
		return model;
	}
	
	
	
	@RequestMapping(value = { "/reviewsReviewBook"}, method = RequestMethod.GET)
	public ModelAndView addReviewsPage(HttpServletRequest request, HttpServletResponse response) {

		
		if(request.getSession() == null){
			return null;
		}
		
		log.info("we getting in here reviewsReviewBook?");
		
		log.info("bookTitleFound : "+request.getSession().getAttribute("bookTitleFound")); 
		log.info("bookAuthorFound : "+request.getSession().getAttribute("bookAuthorFound")); 
		
		BookReviewsModel bookReviewsModel = new BookReviewsModel();
		
	
		
		BooksAndReviewsService booksService = new BooksAndReviewsService();
		ModelAndView model = new ModelAndView();	
		
		String searchSelectedBook = request.getParameter("titleAuthorText");
		String publisher = "";
		String excerpt = "";
		String imageWidth = request.getParameter("imageWidth");
		String imageHeight = request.getParameter("imageHeight");
		String thumbnailLocation = request.getParameter("thumbnailLocation");

		request.getParameter("titleAuthorText");
		log.info("thumbnailLocation from request : "+thumbnailLocation);
		log.info("searchSelectedBook : "+searchSelectedBook);
		
		String title ="";
		String author  = "";
		
		if(searchSelectedBook != null && !"".equals(searchSelectedBook)){
			//the existence of the request parameter searchSelectedBook - means we are coming to the reviews page from the search book page AND NOT the add book page.
			title = searchSelectedBook.substring(0, searchSelectedBook.lastIndexOf("-")).trim();
			author = searchSelectedBook.substring(searchSelectedBook.lastIndexOf("-")+1).trim();
			
			title = title.replaceAll("-", " ");
			
			log.info("title : "+title);
			
			request.getSession().setAttribute("bookTitleFound", title);
			request.getSession().setAttribute("bookAuthorFound", author);
			request.getSession().setAttribute("imageWidth", imageWidth);
			request.getSession().setAttribute("imageHeight", imageHeight);
			request.getSession().setAttribute("thumbnailLocation", thumbnailLocation);
		}else{
			//get values from session
			
			title = (request.getSession().getAttribute("bookTitleFound") != null) ? request.getSession().getAttribute("bookTitleFound").toString() : ""; 
			author = (request.getSession().getAttribute("bookAuthorFound") != null) ? request.getSession().getAttribute("bookAuthorFound").toString() : ""; 
			imageWidth = (request.getSession().getAttribute("imageWidth") != null) ? request.getSession().getAttribute("imageWidth").toString() : ""; 
			imageHeight = (request.getSession().getAttribute("imageHeight") != null) ? request.getSession().getAttribute("imageHeight").toString() : ""; 
			thumbnailLocation = (request.getSession().getAttribute("thumbnailLocation") != null) ? request.getSession().getAttribute("thumbnailLocation").toString() : ""; 
		}

		bookReviewsModel.setBookTitleReview(title);
		bookReviewsModel.setBookAuthorReview(author);
			
		if(!"".equals(title)){
		
			HashMap<Books, List<BookReviews>> bookMap = booksService.searchBookReviewsByTitleAndAuthor(request.getSession().getAttribute("bookTitleFound").toString(), 
					request.getSession().getAttribute("bookAuthorFound").toString(),0,20);
			request.getSession().setAttribute(SessionConstants.CURRENT_PAGINATION_OFFSET, 0);
			ArrayList<String> list = new ArrayList<String>();
			
			for(Books book : bookMap.keySet()){	
				bookMap.get(book);
				
				publisher = book.getPublisher();
				excerpt = book.getExcerpt();			

				if(excerpt == null || "".equals(excerpt)){
					excerpt = "No description at this time";
				}
				
				for(BookReviews bookRev : bookMap.get(book)){
						
					// color: #f70;
					//	content: "\2605";
					
				
					
					//starRatingsHTMLBuffer.append("<div>");
					
					log.info("bookRev.getStarRating() : "+bookRev.getStarRating());
					
					String starRating = "0";
					
					if(bookRev.getStarRating() != null){
						starRating = String.valueOf(bookRev.getStarRating());
					}
				
					HTMLModel htmlModel = new HTMLModel();
					htmlModel.setstarRating(starRating);
					htmlModel.setreviewersUserName(bookRev.getReviewersUsername());
					htmlModel.setreviewerText(bookRev.getReviewText());
					
					HTMLHelper htmlHelper = new HTMLHelper();
					String reviewsFormattedHTML = htmlHelper.formatReviewersHTML(htmlModel);
			
					list.add(reviewsFormattedHTML);
				}
			}
			
			if(list.size() == 0){
				list.add("No Reviews found for title.");
			}
			
			model.addObject("reviewLists", list);
			
			log.info("thumnbnailLocation :::: "+thumbnailLocation);
			

			
			HTMLModel htmlModel = new HTMLModel();
			htmlModel.setauthor(author);
			htmlModel.settitle(title);
			htmlModel.setimageHeight(imageHeight);
			htmlModel.setimageWidth(imageWidth);
			htmlModel.setpublisher(publisher);
			htmlModel.setthumbnailLocation(thumbnailLocation);
			htmlModel.setexcerpt(excerpt);
			
			HTMLHelper htmlHelper = new HTMLHelper();
			String reviewsBooksOnlyFormattedHTML = htmlHelper.formatReviewBookOnlyHTML(htmlModel);
	
			
			log.info("reviewsListHTML : "+reviewsBooksOnlyFormattedHTML);
					
			log.info("formattedHTML :::: "+reviewsBooksOnlyFormattedHTML);
			
			model.addObject("formattedHTML", reviewsBooksOnlyFormattedHTML);
		}else{
			request.getSession().setAttribute("bookAuthorFound", "");
			request.getSession().setAttribute("bookTitleFound", "");
			request.getSession().setAttribute(SessionConstants.CURRENT_PAGINATION_OFFSET, 0);
		}
		
		
		log.info("request.getSession().getAttribute(bookTitleFound) !!!!"+request.getSession().getAttribute("bookTitleFound"));
		
		model.addObject("bookReviewsModel", bookReviewsModel);	
		model.addObject("title", title);
		model.addObject("author", author);
		model.setViewName("reviewsReviewBook");
		return model;
	}
	
	
	
	@RequestMapping(value = { "/addBookReview"}, method = RequestMethod.GET)
	public @ResponseBody BookReviewsModel addBookReview(HttpServletRequest request, HttpServletResponse response){
		
		if(request.getSession() == null){
			return null;
		}
		
		log.info(" addBookReview request "+request.toString());
		log.info("request contain titleText ? : "+request.getParameter("titleText"));
		log.info("request contain authorText ? : "+request.getParameter("authorText"));
	
		
		log.info("request contain titleText ? : "+request.getParameter("titleText"));
		log.info("request contain authorText ? : "+request.getParameter("authorText"));
		log.info("request contain reviewText ? : "+request.getParameter("reviewText"));
		//log.info("author from map : "+bookReviewsModel.getAuthorText());
		
		BooksAndReviewsService booksService = new BooksAndReviewsService();
		int bookID = (request.getSession().getAttribute("bookID") != null) ? Integer.parseInt(request.getSession().getAttribute("bookID").toString()) : -1;
		
		log.info("bookID : "+bookID);
		
		if(bookID == -1){
			log.info("BOOK != -1");
			Books book = booksService.searchBooksByTitleAndOrAuthor(request.getParameter("titleText"), request.getParameter("authorText")).get(0);
			bookID = book.getIdbooks();
		}
		
		Integer starRating = Integer.parseInt(request.getParameter("starRating"));
		
		booksService.addReview(bookID, SecurityContextHolder.getContext().getAuthentication().getName(), request.getParameter("reviewText").toString(), starRating);
		//addReview(request.getParameter("titleText"), request.getParameter("authorText"));
	
		ModelAndView modelAndView = new ModelAndView();
		
		BookReviewsModel bookReviewsModel = new BookReviewsModel();
		bookReviewsModel.setTitleText(request.getParameter("titleText"));
		bookReviewsModel.setAuthorText(request.getParameter("authorText"));
		bookReviewsModel.setReviewText(request.getParameter("reviewText"));	
		bookReviewsModel.setStarRating(starRating);
		//store returned values in session
		
		request.getSession().setAttribute("bookTitleFound", request.getParameter("titleText"));
		request.getSession().setAttribute("bookAuthorFound", request.getParameter("authorText"));
		//request.getSession().setAttribute("bookReviewText", request.getParameter("reviewText"));
		
	
		//modelAndView.setViewName("reviews");
	//	modelAndView.addObject("bookReviewsModel", bookReviewsModel);
		return bookReviewsModel;
	}
	

	
	@RequestMapping(value = { "/searchForBook2"}, method = RequestMethod.GET)
	public @ResponseBody BookReviewsModel searchBook2(HttpServletRequest request, HttpServletResponse response){
		
		request.getSession().setAttribute("langText", request.getParameter("langText"));
		request.getSession().setAttribute("publisherText", request.getParameter("publisherText"));
		return new BookReviewsModel();
	}
	
	
	@RequestMapping(value = { "/resetSearch"}, method = RequestMethod.GET)
	public @ResponseBody BookReviewsModel resetSearch(HttpServletRequest request, HttpServletResponse response){
		
		if(request.getSession() == null){
			return null;
		}
		
		request.getSession().removeAttribute("bookAuthorFound");
		request.getSession().removeAttribute("bookTitleFound");
		request.getSession().removeAttribute(SessionConstants.CURRENT_PAGINATION_OFFSET);
		request.getSession().removeAttribute(SessionConstants.SEARCH_TYPE_TAG);
		request.getSession().removeAttribute(SessionConstants.TAGS_SEARCH_CRITERIA);
		request.getSession().removeAttribute(SessionConstants.BOOKS_SEARCH_CRITERIA);
		request.getSession().removeAttribute(SessionConstants.PUBLISHER_TEXT);
		
		return new BookReviewsModel();
	}
	
	private void resetSearchSessionAttributes(HttpServletRequest request){
		request.getSession().setAttribute(SessionConstants.PUBLISHER_TEXT, "");
		request.getSession().setAttribute(SessionConstants.TITLE_TEXT, "");
		request.getSession().setAttribute(SessionConstants.AUTHOR_TEXT, "");
		request.getSession().setAttribute(SessionConstants.GENRE_SELECT, "");
		request.getSession().setAttribute(SessionConstants.CATEGORY_SELECT, "");
		request.getSession().setAttribute(SessionConstants.LANGUAGE_SELECT, "");
		request.getSession().setAttribute(SessionConstants.SEARCH_TYPE_TAG, "");
		request.getSession().setAttribute(SessionConstants.TAGS_SEARCH_CRITERIA, null);
		request.getSession().setAttribute(SessionConstants.BOOKS_SEARCH_CRITERIA, null);
		
		
		request.getSession().setAttribute(SessionConstants.CURRENT_PAGINATION_OFFSET, 0);
		request.getSession().setAttribute("solrSearchListReturned", null);
		request.getSession().setAttribute("solrPaginationOffset", 0);
		request.getSession().setAttribute("solrTitleQuery", "");
		request.getSession().setAttribute("solrAuthorQuery", "");
		request.getSession().setAttribute("solrKeywordsQuery", "");
	}
	
	
	
	@RequestMapping(value = { "/searchForDocs"}, method = RequestMethod.GET)
	public @ResponseBody SolrSearchData[] searchForDocs(HttpServletRequest request, HttpServletResponse response){
		log.info("searchForDocs keyword text : : "+request.getParameter("keywordText"));
		
		resetSearchSessionAttributes(request);
		SolrSearchData ssd = new SolrSearchData();
		SolrSearchService solrService = new SolrSearchService();	
		String keywords = request.getParameter("keywordText");
		keywords = keywords.replaceAll(",", " ");
		
		log.info("keywords : "+keywords);
		
		String titleText = request.getParameter("titleText");
		String authorText = request.getParameter("authorText");
		
		
		SolrDocumentList solrDocListAuthorsSearch = null;
		
		if(!"".equals(authorText)){
			solrDocListAuthorsSearch =  solrService.performQueryPaginated("author:"+authorText, 5, 0);
			request.getSession().setAttribute("solrAuthorQuery", "author:"+authorText);
			
			log.info("list solrDocListAuthorsSearch is : "+solrDocListAuthorsSearch.size());
		}
		
		request.getSession().setAttribute("solrPaginationOffset", 0);
		
		
		
		SolrDocumentList solrDocListTitleSearch = null;
		
		if(!"".equals(titleText)){
			solrDocListTitleSearch = solrService.performQueryPaginated("title:"+titleText, 5, 0);
			
			request.getSession().setAttribute("solrTitleQuery", "title:"+titleText);
	
			
			log.info("list solrDocListTitleSearch is : "+solrDocListTitleSearch.size());
		}
		
		
		
		SolrDocumentList filteredList = new SolrDocumentList();
		
		if(solrDocListTitleSearch != null && solrDocListTitleSearch.size() > 0 && 
				solrDocListAuthorsSearch != null && solrDocListAuthorsSearch.size() > 0){
			
			for(SolrDocument solrDoc : solrDocListTitleSearch){
				
				for(SolrDocument solrDocAuthors : solrDocListAuthorsSearch){
					if(solrDocAuthors.getFieldValue("id").toString().equals(solrDoc.getFieldValue("id").toString())){
						filteredList.add(solrDoc);
					}
				}
			}
			
		}else{
			
			if(solrDocListAuthorsSearch != null){
				filteredList.addAll(solrDocListAuthorsSearch);
			}
			
			if(solrDocListTitleSearch != null){
				filteredList.addAll(solrDocListTitleSearch);
			}
		}
		
		
		log.info("filteredList size "+filteredList.size());
		
		SolrDocumentList solrDocListKeywordsSearch = null;
		
		if(!"".equals(keywords)){
			solrDocListKeywordsSearch = solrService.performQueryPaginated(keywords,5, 0);
			request.getSession().setAttribute("solrKeywordsQuery", keywords);
			log.info("list solrDocListKeywordsSearch is : "+solrDocListKeywordsSearch.size());
		}

		for(SolrDocument solrDocument : filteredList){
			log.info("solrDocument filtered list ID : "+solrDocument.getFieldValue("id"));
		}
		
		SolrDocumentList finalisedFilteredList = new SolrDocumentList();
		
		if(solrDocListKeywordsSearch != null && solrDocListKeywordsSearch.size() > 0 && filteredList.size() > 0){
			for(SolrDocument solrDocument : solrDocListKeywordsSearch){
				
				log.info("keywords list ID : "+solrDocument.getFieldValue("id"));
				
				for(SolrDocument solrDocFiltered : filteredList){
					if(solrDocFiltered.getFieldValue("id").toString().equals(solrDocument.getFieldValue("id").toString())){
						finalisedFilteredList.add(solrDocument);
					}
				}
			}
		}else{
			finalisedFilteredList.addAll(filteredList);
			
			if(solrDocListKeywordsSearch != null){
				finalisedFilteredList.addAll(solrDocListKeywordsSearch);
			}
			
		}
		
		log.info("finalisedFilteredList size "+finalisedFilteredList.size());

		List<SolrSearchData> returnList = new ArrayList<SolrSearchData>();
	//	List<String> formattedList = new ArrayList<String>();

		SolrSearchData[] returnArray = new SolrSearchData[finalisedFilteredList.size()];
		
		int count = 0;
		
		for(SolrDocument solrD : finalisedFilteredList){

			ssd = new SolrSearchData();
			
			for(String field : solrService.getFieldsArray()){
				
				String fieldToSet = (solrD.getFieldValue(field) != null) ? solrD.getFieldValue(field).toString() : "";
				
				try{
					Method method = ssd.getClass().getDeclaredMethod("set"+field, String.class);
					method.invoke(ssd, fieldToSet);
				}catch(Exception e){
					e.printStackTrace();
					log.error(e.getMessage());
				}
			}
			
			log.info("author set : "+ssd.getauthor());
			log.info("title set : "+ssd.gettitle());
			log.info("id set : "+ssd.getid());

			String title = "";
			
			if(ssd.gettitle() == null || "".equals(ssd.gettitle().trim()) || "Unknown".equalsIgnoreCase(ssd.gettitle()) || "en".equalsIgnoreCase(ssd.gettitle())){

				if(ssd.getid().lastIndexOf(File.separator) > -1){
					title = ssd.getid().substring(ssd.getid().lastIndexOf(File.separator)+1);
				}else{
					title = ssd.getid();
				}
			}else{
				title = ssd.gettitle();
			}
			
			log.info("title "+title);

			String largerContent = solrService.extractSpecifiedDocumentContent(ssd.getid(), 2000);
			
			if(largerContent.length() >= 1999){
				largerContent = largerContent + "<i> ...open document to see more</i>";
			}
			
			
			String author = ssd.getauthor().replaceAll("\\[", "").replaceAll("\\]","");
			
			
			log.info("larger content :::: "+largerContent);
			
			ssd.setauthor(author);
			ssd.settitle(title);
			ssd.setlargercontent(largerContent);
			
			
			//TODO detect content
			TikaConfig config = TikaConfig.getDefaultConfig();
			Detector detector = new DefaultDetector(config.getMimeRepository());
			
	
			try{
				TikaInputStream stream = TikaInputStream.get(new File(ssd.getid()));
	
				Metadata metadata = new Metadata();
				metadata.add(Metadata.RESOURCE_NAME_KEY, ssd.getid());
			    MediaType mediaType = detector.detect(stream, metadata);
			    
			    log.info("media type : "+mediaType.getType());
			    log.info("media base type : "+mediaType.getBaseType());
			    log.info("media sub type : "+mediaType.getSubtype());
			    log.info(detector.detect(stream, metadata).toString());
			    
			    ssd.setThumbnailLocation(solrService.getMimeTypeToThumbLocationMap().get(mediaType.getSubtype().toLowerCase().trim()));
			    
			}catch(Exception e){
				e.printStackTrace();
				log.error(e.getMessage());
			}
			
			log.info("author 2 : "+author);
			
			ssd.setextract(solrService.extractSpecifiedDocumentContent(ssd.getid(), 600));
			
			returnArray[count] = ssd;
			count++;
		}

	
		request.getSession().setAttribute("solrSearchListReturned", returnList);		
		log.info("list to return is : "+returnList.size());
		return returnArray;
	}
	
	@RequestMapping(value = { "/searchForBook"}, method = RequestMethod.GET)
	public @ResponseBody BookReviewsModel[] searchBook(HttpServletRequest request, HttpServletResponse response){
		
		
		log.info("request.getSession() : "+request.getSession());
		
		if(request.getSession() == null){
			return null;
		}
		
		resetSearchSessionAttributes(request);
		log.info("request contain titleText ? : "+request.getParameter(SessionConstants.TITLE_TEXT));
		log.info("request contain authorText ? : "+request.getParameter(SessionConstants.AUTHOR_TEXT));
		log.info("request contain publisherText ? : "+request.getParameter(SessionConstants.PUBLISHER_TEXT));
		
		log.info("request contain lang text ? : "+request.getParameter("langText"));
		
		//log.info("author from map : "+bookReviewsModel.getAuthorText());
		
		String titleText = request.getParameter(SessionConstants.TITLE_TEXT);
		String authorText = request.getParameter(SessionConstants.AUTHOR_TEXT);
		String publisherText = request.getParameter(SessionConstants.PUBLISHER_TEXT);
		
		//contains any search criteria the users has input from the search books page 
		//they currently being - title, author, publisher, category, genre, language
		
		HashMap<String, HashMap<String, String>> searchCriteria = new HashMap<String, HashMap<String, String>>();
		
		HashMap<String, String> tagsAndValueMap = new HashMap<String, String>();
		
		if(request.getParameter(SessionConstants.GENRE_SELECT) != null && !"".equals(request.getParameter(SessionConstants.GENRE_SELECT))){
			tagsAndValueMap.put(SessionConstants.GENRE_SELECT, request.getParameter(SessionConstants.GENRE_SELECT));
			log.info("genreText to search on : "+request.getParameter(SessionConstants.GENRE_SELECT));
		}
		
		if(request.getParameter(SessionConstants.CATEGORY_SELECT) != null && !"".equals(request.getParameter(SessionConstants.CATEGORY_SELECT))){
			log.info("catText to search on : "+request.getParameter(SessionConstants.CATEGORY_SELECT));
			
			tagsAndValueMap.put(SessionConstants.CATEGORY_SELECT, request.getParameter(SessionConstants.CATEGORY_SELECT));
		}
		
		if(request.getParameter(SessionConstants.LANGUAGE_SELECT) != null && !"".equals(request.getParameter(SessionConstants.LANGUAGE_SELECT))){
			log.info("lang text to search on : "+request.getParameter("langText"));
			tagsAndValueMap.put(SessionConstants.LANGUAGE_SELECT, request.getParameter(SessionConstants.LANGUAGE_SELECT));
		}
		
		log.info("tags and value map size : "+tagsAndValueMap.size());
		
		if(tagsAndValueMap.size() > 0){
			searchCriteria.put(SessionConstants.TAGS_SEARCH_CRITERIA, tagsAndValueMap);
			request.getSession().setAttribute(SessionConstants.TAGS_SEARCH_CRITERIA, tagsAndValueMap);
		}else{
			searchCriteria.put(SessionConstants.TAGS_SEARCH_CRITERIA, new <String, String>HashMap());
			request.getSession().setAttribute(SessionConstants.TAGS_SEARCH_CRITERIA,  new <String, String>HashMap());
		}

		log.info("just before service instantiation !");
		
		BooksAndReviewsService booksService = new BooksAndReviewsService();
		
		List<Books> booksList = new ArrayList<Books>();
		log.info("just before test !");

		request.getSession().setAttribute(SessionConstants.PUBLISHER_TEXT, publisherText);
		request.getSession().setAttribute(SessionConstants.SEARCH_TYPE_TAG, SessionConstants.FIND_BY_BOOKS_BY_PUBLISHER_LAZY_LOAD);
		
		
		HashMap<String, String> booksSearchCriteria = null;
		
		if(titleText != null && !"".equals(titleText)){
			
			if(booksSearchCriteria == null){
				booksSearchCriteria = new HashMap<String, String>(); 
			}
			
			booksSearchCriteria.put(SessionConstants.TITLE_TEXT, titleText);
			log.info("in here111");
			//booksList.addAll(booksService.searchBooksByTitleAndOrAuthor(request.getParameter("titleText"), request.getParameter("authorText")));
		}
		
		if(!"".equals(authorText) && authorText != null){
			
			if(booksSearchCriteria == null){
				booksSearchCriteria = new HashMap<String, String>(); 
			}
			
			booksSearchCriteria.put(SessionConstants.AUTHOR_TEXT, authorText);
		}
		
		if(publisherText != null && !"".equals(publisherText)){
			
			if(booksSearchCriteria == null){
				booksSearchCriteria = new HashMap<String, String>(); 
			}
			booksSearchCriteria.put(SessionConstants.PUBLISHER_TEXT, publisherText);
			log.info("in here222");
		}
		
		if(booksSearchCriteria != null && booksSearchCriteria.size() > 0){
			searchCriteria.put(SessionConstants.BOOKS_SEARCH_CRITERIA, booksSearchCriteria); 
			request.getSession().setAttribute(SessionConstants.BOOKS_SEARCH_CRITERIA, booksSearchCriteria);
		}else{
			searchCriteria.put(SessionConstants.BOOKS_SEARCH_CRITERIA, new HashMap<String, String>());
			request.getSession().setAttribute(SessionConstants.BOOKS_SEARCH_CRITERIA, new HashMap<String, String>());
		}
		
		
		booksList.addAll(booksService.findBooksByAnyCriteriaLazyLoad(searchCriteria, 0, 20));

		ModelAndView modelView = new ModelAndView();
		List<String> booksStringViewList = new ArrayList<String>();
		
		log.info("book list : "+booksList.size());
		request.getSession().setAttribute("currentPaginationOffset", 0);
		
			
		if(booksList == null || booksList.size() == 0){
			request.getSession().setAttribute("bookAuthorFound", "");
			request.getSession().setAttribute("bookTitleFound", "");
			request.getSession().setAttribute(SessionConstants.CURRENT_PAGINATION_OFFSET, 0);
			log.info("no books found ");
			booksStringViewList.add("No books found");
		}
		
		BookReviewsModel[] bookReviewsModelArray = new BookReviewsModel[booksList.size()];
		int count = 0;
		
		BookReviewsModel model = null;
		
		for(Books book : booksList){
			log.info("1 book.getThumbnailLocation() : "+book.getThumbnailLocation());	
			model = new BookReviewsModel();
			model.setAuthorText(book.getAuthor());
			model.setTitleText(book.getTitle()); 
			model.setExcerpt(book.getExcerpt());
			
			log.info("EXCERPT : "+model.getExcerpt());
			
			String loc = (book.getThumbnailLocation() != null && book.getThumbnailLocation().contains("http")) ? 
					book.getThumbnailLocation() : "./presentationResources/images/"+book.getThumbnailLocation();
			
			model.setThumbnnalLocation(loc);
			model.setBooksID(book.getIdbooks());
			model.setPublisherText(book.getPublisher());
			model.setBooksList(book.getTitle()+" - "+book.getAuthor());			
			
			try{
				//file system relative references are different from web application relative references 
				String fileURLPath = (loc.toLowerCase().contains("http")) ? loc : ConfigHandler.getInstance().readApplicationProperty("applicationImagesLocation")+book.getThumbnailLocation();
				log.info( System.getProperty("user.dir"));
				 
				File file = new File(fileURLPath);
				log.info("location for file is :::: "+fileURLPath);
				log.info("does file exist : "+file.exists());
				
				Image image = new ImageIcon(fileURLPath).getImage();
				
				int imgWidth = image.getWidth(null);
				int imgHeight = image.getHeight(null);
				
				log.info("imgWidth : "+imgWidth);
				log.info("imgHeight : "+imgHeight);
				
				if(imgWidth > imgHeight){
					double result = new Double(imgHeight)/ new Double(imgWidth);
					log.info("result : "+result);
					imgHeight = (int)(result * new Double(120));
					imgWidth = 120;
				}else if(imgWidth < imgHeight){
					double result = new Double(imgWidth)/ new Double(imgHeight);
					imgWidth = (int)(result * new Double(120));
					imgHeight = 120;
				}else{
					imgHeight = 120;
					imgWidth  = 120;
				}
				
				model.setImageHeight(String.valueOf(imgHeight));
				model.setImageWidth(String.valueOf(imgWidth));
				
			}catch(Exception e){
				e.printStackTrace();
				log.error(e.getMessage());
			}
			
			bookReviewsModelArray[count] = model;
			count++;
		}
		
		log.info("before testing array length");
		
		if(bookReviewsModelArray.length == 0){
			log.info("after testing array length");
			bookReviewsModelArray = new BookReviewsModel[1];
			log.info("after testing array length 222");
			BookReviewsModel model_ = new BookReviewsModel();
			log.info("after testing array length 333");
			model_.setBooksList("No Books Found!!");
			log.info("after testing array length 444");
			bookReviewsModelArray[0] = model_;
		}
		
		log.info("after testing array length 555");
		
		modelView.setViewName("reviewsSearchBook");
		return bookReviewsModelArray;

	}
	
	public static void main(String args[]){
		
		try{
			Image image = new ImageIcon("C:/Tomcat_8/webapps/iFindit4U/presentationResources/images/plague.jpg").getImage();
			
			int imgWidth = image.getWidth(null);
			int imgHeight = image.getHeight(null);
			
			System.out.println("imgWidth BEFORE : "+imgWidth);
			System.out.println("imgHeight BEFORE : "+imgHeight);
			
			if(imgWidth > imgHeight){
				double result = new Double(imgHeight)/ new Double(imgWidth);
				log.info("result : "+result);
				imgHeight = (int)(result * new Double(120));
				imgWidth = 120;
			}else if(imgWidth < imgHeight){
				double result = new Double(imgWidth)/ new Double(imgHeight);
				imgWidth = (int)(result * new Double(120));
				imgHeight = 120;
			}else{
				imgHeight = 120;
				imgWidth  = 120;
			}
			
			System.out.println("imgWidth AFTER : "+imgWidth);
			System.out.println("imgHeight AFTER : "+imgHeight);
			
		}catch(Exception e){
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}


}
