package co.srsp.hibernate;

import java.awt.print.Book;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import co.srsp.hibernate.orm.BookReviews;
import co.srsp.hibernate.orm.Books;
import co.srsp.hibernate.orm.NotificationSubscribers;
import co.srsp.service.BooksAndReviewsService;


@Configuration
@EnableAspectJAutoProxy
public class BooksBusinessObjectImpl extends HibernateDaoSupport implements BooksBusinessObject{

	private final static Logger log = Logger.getLogger(BooksBusinessObjectImpl.class); 
	
	@Override
	@Transactional
	public void save(Books books) {
		Session session = this.getSessionFactory().openSession();
		session.save(books);
		session.flush();
		session.close();
	}

	@Override
	@Transactional
	public void update(Books books) {
		Session session = this.getSessionFactory().openSession();
		session.update(books);
		session.flush();
		session.close();
	}

	@Override
	@Transactional
	public void delete(Books books, List<BookReviews> bookReviews) {
		Session session = this.getSessionFactory().openSession();
		
		for(BookReviews bookReview : bookReviews){
			session.delete(bookReview);
		}
		
		session.delete(books);
		session.flush();
		session.close();
	}

	@Override
	public Books findBooksByTitleOnly(String title) {
		Books book = new Books();
		book.setTitle(title);
		Session session = this.getSessionFactory().openSession();
		book = (Books)session.load(Books.class, 10);
		
		
		
		return book;
	}
	
	@Override
	public  List<String> findBookListByPartialMatch(HashMap<String, String> searchCriteria){
		
		StringBuffer sqlAppender = new StringBuffer();
		
		int count = 0;
		
		String sqlText = "select :key from books where ";
				
		for(String key : searchCriteria.keySet()){

			sqlAppender.append(sqlText.replaceAll(":key", key));
			
			if(count > 1){
				sqlAppender.append(" and ");
			}

			String value = (String)searchCriteria.get(key);
			sqlAppender.append(key+" like '%"+value+"%'");
			count++;
		}
		
		log.info("sql to exec : "+sqlAppender.toString());
		
		Session session = this.getSessionFactory().openSession();
		
		List<String> list = session.createSQLQuery(sqlAppender.toString()).list();
		
		return list;
	}
	
	@Override
	public List<Books> findBooksByTitleAndAuthor(String title, String author) {
		// TODO Auto-generated method stub
		System.out.println("book title to search : "+title);
		Session session = this.getSessionFactory().openSession();
		List<Books> list = null;
		
		
		if(author == null || "".equals(author)){
			list = session.createQuery(" from "+Books.class.getName()+" where UPPER(title) = UPPER(:title) ").setString("title", title).list();
		}else if(title == null || "".equals(title)){
			list = session.createQuery(" from "+Books.class.getName()+" where UPPER(author) = UPPER(:author) ").setString("author", author).list();
		}else{
			Map hashMap = new HashMap();
			hashMap.put("title", title);
			hashMap.put("author", author);
			//session.load(theClass, id) //evict and clear load performs new select only if object is evicted or cleared from session
			
			
			list = session.createQuery(" from "+Books.class.getName()+" where UPPER(title) = UPPER(:title) and UPPER(author) = UPPER(:author) ").setProperties(hashMap).list();
		}
		
		
		//session.clear();
		
		return list;


	}

}
