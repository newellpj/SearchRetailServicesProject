package co.srsp.hibernate;


import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import co.srsp.constants.SessionConstants;
import co.srsp.hibernate.orm.Authorities;
import co.srsp.hibernate.orm.BookReviews;
import co.srsp.hibernate.orm.Books;
import co.srsp.hibernate.orm.Users;
import co.srsp.service.UsersRolesAuthoritiesService;
import junit.framework.Assert;

public class HibernateTestClass {

	private final static Logger log = Logger.getLogger(HibernateTestClass.class); 
	
	public static void main(String[] args) {
		
		//testBooksAndReviews();
		//testUsersAuthorities();
		//testSelectSubsets();
	//	testTagsSearch();
	}
	
	@Test
	public void testBookLoad(){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		BooksBusinessObject booksBO = (BooksBusinessObject) ctx.getBean("booksBusinessObject");
		Books book = booksBO.findBooksByTitleOnly("The Plague");
		
		System.out.println("book title : "+book.getTitle());
		System.out.println("book author : "+book.getAuthor());
		
		//book.setTitle("The plague goes on");
		
		Books book2 = new Books();
		book2.setIdbooks(9);
		book2.setTitle("The Trial");
		book2.setAuthor("Franz Kafka");
		book2.setPublisher("Vintage");
		book2.setThumbnailLocation("theTrial.png");
		book2.setExcerpt("Someone must have been telling lies about Josef K., he knew he had done nothing wrong but, one morning, he was arrested."
				+ " From its gripping first sentence onward, this novel exemplifies the term Kafkaesque."+
				" Its darkly humorous narrative recounts a bank clerk's entrapment based on an undisclosed charge in a maze of nonsensical rules and"
				+ " bureaucratic roadblocks. Written in 1914 and published posthumously in 1925, Kafka's engrossing parable about the human condition plunges"
				+ " an isolated individual into an impersonal, illogical system. Josef K.'s ordeals raise provocative, ever-relevant issues related to the role "
				+ " of government and the nature of justice. This inexpensive edition of one of the 20th century's most important novels features an acclaimed translation "
				+ "by David Wyllie.");
		
		booksBO.update(book2);
		book = booksBO.findBooksByTitleOnly("The Trial");
		//booksBO.delete(book2, null);
		System.out.println("2 book title : "+book.getTitle());
		System.out.println("2 book author : "+book.getAuthor());
		
		
	}
	

	@Test
	public void testTagsSearchWithNullCriteria(){
	ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		
		//BooksBusinessObject booksBO = (BooksBusinessObject) ctx.getBean("booksBusinessObject");
		TagsBusinessObject tagsBO = (TagsBusinessObject) ctx.getBean("tagsBusinessObject");
		
		HashMap<String, HashMap<String, String>> searchCriteriaMap = new HashMap<String, HashMap<String, String>>();
		
		HashMap<String, String> tagsMap = null;
		
	
		
		HashMap<String, String> booksMap = new HashMap<String, String>();
		
		booksMap.put(SessionConstants.TITLE_TEXT, "The Trial");
		booksMap.put(SessionConstants.AUTHOR_TEXT, "Franz Kafka");
		booksMap.put(SessionConstants.PUBLISHER_TEXT, "Vintage");
		
		searchCriteriaMap.put(SessionConstants.TAGS_SEARCH_CRITERIA, tagsMap);
		searchCriteriaMap.put(SessionConstants.BOOKS_SEARCH_CRITERIA, booksMap);
		
		List<Books> booksFound = tagsBO.findBooksByAnyCriteriaLazyLoad(searchCriteriaMap, 0, 20);
		
		assertNotNull(booksFound);
		
		if(booksFound != null){
			assertTrue(booksFound.size() > 0);
		}
		
		tagsMap = new <String, String>HashMap();
		
		tagsMap.put(SessionConstants.GENRE_SELECT, "Drama");
		tagsMap.put(SessionConstants.LANGUAGE_SELECT, "English");
		tagsMap.put(SessionConstants.CATEGORY_SELECT, "Fiction");
		
		booksMap = null;
		searchCriteriaMap = new HashMap<String, HashMap<String, String>>();
		searchCriteriaMap.put(SessionConstants.TAGS_SEARCH_CRITERIA, tagsMap);
		searchCriteriaMap.put(SessionConstants.BOOKS_SEARCH_CRITERIA, booksMap);
		
		booksFound = tagsBO.findBooksByAnyCriteriaLazyLoad(searchCriteriaMap, 0, 20);
		
		assertNotNull(booksFound);
		
		if(booksFound != null){
			assertTrue(booksFound.size() > 0);
		}
		
	}
	
	@Test
	public void testTagsSearch(){
		//findBooksByTagsLazyLoad
		
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		
		//BooksBusinessObject booksBO = (BooksBusinessObject) ctx.getBean("booksBusinessObject");
		TagsBusinessObject tagsBO = (TagsBusinessObject) ctx.getBean("tagsBusinessObject");
		
		HashMap<String, HashMap<String, String>> searchCriteriaMap = new HashMap<String, HashMap<String, String>>();
		
		HashMap<String, String> tagsMap = new HashMap<String, String>();
		
		tagsMap.put(SessionConstants.GENRE_SELECT, "Drama");
		tagsMap.put(SessionConstants.LANGUAGE_SELECT, "English");
		tagsMap.put(SessionConstants.CATEGORY_SELECT, "Fiction");
		
		HashMap<String, String> booksMap = new HashMap<String, String>();
		
		booksMap.put(SessionConstants.TITLE_TEXT, "The Trial");
		booksMap.put(SessionConstants.AUTHOR_TEXT, "Franz Kafka");
		booksMap.put(SessionConstants.PUBLISHER_TEXT, "Vintage");
		
		searchCriteriaMap.put(SessionConstants.TAGS_SEARCH_CRITERIA, tagsMap);
		searchCriteriaMap.put(SessionConstants.BOOKS_SEARCH_CRITERIA, booksMap);
		
		List<Books> booksFound = tagsBO.findBooksByAnyCriteriaLazyLoad(searchCriteriaMap, 0, 20);
		
		assertNotNull(booksFound);
		
		if(booksFound != null){
			assertTrue(booksFound.size() > 0);
		}
		
		Books books = new Books();
		books.setTitle("Fear and Loathing in Las Vegas");
		books.setAuthor("Hunter S Thompson");
	
	//	booksBO.save(books);
		
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("langText", "English");	
		map.put("catText", "Non-fiction");	
		map.put("genreText", "Philosophy");	
		
		List<Books> booksFound2 = tagsBO.findBooksByTagsLazyLoad(map, 0, 20);
		
		for(Books book : booksFound2){
			System.out.println("title :::: "+book.getTitle());
			
			assertNotNull(book.getTitle());
			//assertEquals("Fear and Loathing in Las Vegas", book.getTitle());
		}
		
		
	
		
	}
	
	@Test
	public void testBooksSearchPublisher(){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		
		BooksBusinessObject booksBO = (BooksBusinessObject) ctx.getBean("booksBusinessObject");
		BookReviewsBusinessObject booksReviewsBO = (BookReviewsBusinessObject) ctx.getBean("booksReviewsBusinessObject");
		
		Books books = new Books();
		books.setTitle("Fear and Loathing in Las Vegas");
		books.setAuthor("Hunter S Thompson");
		books.setPublisher("Harper");
	//	booksBO.save(books);
		
		List<Books> booksFound = booksReviewsBO.findBooksByPublishersLazyLoad(books.getPublisher(), 0, 20);

		for(Books book : booksFound){
			assertNotNull(book.getTitle());
		
			System.out.println("title :::: "+book.getTitle());
		}

	}
	
	@Test
	public void testBooksAndReviews(){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		
		BooksBusinessObject booksBO = (BooksBusinessObject) ctx.getBean("booksBusinessObject");
		BookReviewsBusinessObject booksReviewsBO = (BookReviewsBusinessObject) ctx.getBean("booksReviewsBusinessObject");
		
		Books books = new Books();
		books.setTitle("Fear and Loathing in Las Vegas");
		books.setAuthor("Hunter S Thompson");
	
	//	booksBO.save(books);
		
		Books bookFound = booksBO.findBooksByTitleAndAuthor(books.getTitle(), books.getAuthor()).get(0);
		
		System.out.println("book found : "+bookFound.getTitle()+" author : "+bookFound.getAuthor());
		assertEquals("Fear and Loathing in Las Vegas", bookFound.getTitle());
		assertEquals("Hunter S Thompson", bookFound.getAuthor());
		
		int count = 0;
		
		boolean exceptionCaught = false;
		
		try{
		
			while(count < 200){	
				BookReviews bookReviews = new BookReviews();
				bookReviews.setIdbooks(15);
				bookReviews.setReviewersUsername("edureka");
				bookReviews.setReviewText("Absolutely loved it. RECOMMENDED! : "+count);
				booksReviewsBO.save(bookReviews);
				
				count++;
			}
		}catch(Exception e){
			e.printStackTrace();
			exceptionCaught = true;
		}

		assertFalse(exceptionCaught);
	}
	
	public void testSelectSubsets(){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		
		BooksBusinessObject booksBO = (BooksBusinessObject) ctx.getBean("booksBusinessObject");
		BookReviewsBusinessObject booksReviewsBO = (BookReviewsBusinessObject) ctx.getBean("booksReviewsBusinessObject");
		
		List<BookReviews> reviews = booksReviewsBO.findBooksReviewByReviewerLazyLoad("edureka", 10, 37);
		
		for(BookReviews br : reviews){
			System.out.println("reviews book id : "+br.getIdbooks());
			System.out.println("reviews text : "+br.getReviewText());
		}
		
		HashMap<Books, List<BookReviews>> booksAndReviews = booksReviewsBO.findBooksReviewByTitleAndAuthorLazyLoad("Fear and Loathing in Las Vegas","Hunter S Thompson", 20, 59);
		
		Set<Books> books = booksAndReviews.keySet();
		
		for(Books book :  books){
		
			System.out.println("book with reviews is : "+book.getTitle() +" by "+book.getAuthor());
			
			
			for(BookReviews br : booksAndReviews.get(book)){
				System.out.println("reviews book id 2 : "+br.getIdbooks());
				System.out.println("reviews text 2 : "+br.getReviewText());
			}
		}
		
	}
	
	@Test
	public void testPasswordsEncryptDecrypt(){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		UsersBusinessObject userBO = (UsersBusinessObject) ctx.getBean("usersBusinessObject");
		AuthoritiesBusinessObject authBO = (AuthoritiesBusinessObject) ctx.getBean("authoritiesBusinessObject");
		Users users = new Users();
		String passPlainText = "pAssword1";
		users.setUsername("taylorpt1332");
		System.out.println("userBO.encryptPassword(pAssword1) : "+userBO.encryptPassword("pAssword1"));
		users.setPassword(userBO.encryptPassword("pAssword1"));
		users.setEnabled("Y");
		
		Authorities auth = new Authorities();
		auth.setAuthority("ROLE_USER");
		auth.setUsername(users.getUsername());
		
		userBO.save(users, auth);
		
		Users usersRet = userBO.findUsersByUsername(users.getUsername());
		userBO.delete(users, auth);
		assertEquals("taylorpt1332", usersRet.getUsername());
		assertEquals(true, userBO.checkPassword(usersRet.getPassword(), passPlainText));
		
		
	}
	
	public void testUsersAuthorities(){

		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		UsersBusinessObject userBO = (UsersBusinessObject) ctx.getBean("usersBusinessObject");
		AuthoritiesBusinessObject authBO = (AuthoritiesBusinessObject) ctx.getBean("authoritiesBusinessObject");
		
		Users users = new Users();
		users.setUsername("taylorpt");
		users.setPassword("password1");
		users.setEnabled("Y");
		
		Authorities auth = new Authorities();
		auth.setAuthority("ROLE_USER");
		auth.setUsername(users.getUsername());
		
		//userBO.save(users, auth);
		
		Users usersRet = userBO.findUsersByUsername(users.getUsername());
		List<Authorities> authRetList = authBO.findAuthoritiesByUserName(users.getUsername());
		
		assertEquals("password1", usersRet.getPassword());
		
		System.out.println("user password returned is : "+usersRet.getPassword());
		
		for(Authorities authority : authRetList){
			System.out.println("auth authoritiy : "+authority.getAuthority());
			System.out.println("auth username : "+authority.getUsername());
			assertNotNull(authority.getAuthority());
			assertNotNull(authority.getUsername());
		}
		
		userBO.delete(users, auth);
	}

}
