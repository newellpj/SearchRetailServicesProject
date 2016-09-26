package co.srsp.hibernate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import co.srsp.controller.PaginationController;
import co.srsp.hibernate.orm.BookTags;
import co.srsp.hibernate.orm.Books;



public class TagsBusinessObjectImpl extends HibernateDaoSupport implements TagsBusinessObject{

	private final static Logger log = Logger.getLogger(TagsBusinessObjectImpl.class); 
	
	@Override
	@Transactional
	public void save(BookTags bookTags) {
		Session session = this.getSessionFactory().openSession();
		session.save(bookTags);
		session.flush();
		session.close();
	}

	@Override
	@Transactional
	public void update(BookTags bookTags) {
		Session session = this.getSessionFactory().openSession();
		session.update(bookTags);
		session.flush();
		session.close();
	}

	@Override
	@Transactional
	public void delete(BookTags bookTags) {
		Session session = this.getSessionFactory().openSession();
		session.delete(bookTags);
		session.flush();
		session.close();
	}
	
	public List<Books> findBooksByAnyCriteriaLazyLoad(HashMap<String, HashMap<String, String>> searchCriteria, int offset, int numberOfRecords){
		
		Session session = this.getSessionFactory().openSession();
		StringBuffer sqlAppender = new StringBuffer();
		int count = 0;
		
		if(searchCriteria.containsKey("tags") && searchCriteria.containsKey("books")){
			
			HashMap<String, String> tagsKeyValues = searchCriteria.get("tags");
			
			for(String key : tagsKeyValues.keySet()){
				
				count++;
				
				if(count > 1){
					sqlAppender.append(" and idbooks in (");
				}
				
				sqlAppender.append(" select idbooks from book_tags where ");
				sqlAppender.append("  UPPER(tag_type) = "+"UPPER('"+key+"')");
				sqlAppender.append(" and UPPER(tag_value) = "+"UPPER('"+tagsKeyValues.get(key)+"')");
				
				if(count > 1){
					sqlAppender.append(")");
				}
			}

			log.info("sql appender value ::: "+sqlAppender.toString());
			//List list = session.createSQLQuery(sqlAppender.toString()).list(); //allows you to create native sql query
			
			
			HashMap<String, String> booksSearchCriteria = searchCriteria.get("books");
			
			String booksWhereClause = "";
			
			if(sqlAppender.toString().length() > 0){
				booksWhereClause += " and idbooks in (select idbooks from books ";
			}
			
			booksWhereClause += " where ";
			
			count = 0;
			Map booksValuesMap = new HashMap();
			
			for(String booksKey : booksSearchCriteria.keySet()){
			
				if(count > 0){
					booksWhereClause += " and ";
				}
				
				booksWhereClause += "UPPER("+booksKey.toLowerCase()+") = UPPER(:"+booksKey+")";
				booksValuesMap.put(booksKey.toLowerCase(), searchCriteria.get(booksKey));
				count++;
			}
			
			log.info("booksWhereClause :::: "+booksWhereClause);
			
			sqlAppender.append(booksWhereClause);
			
			//TODO the pagination part of this query set
			List list = session.createSQLQuery(sqlAppender.toString()).setFirstResult(offset).setMaxResults(numberOfRecords).list();
			
			List<Books> books = new ArrayList<Books>();
			
			for(Object obj : list){
				
				int idbooks = (Integer)obj;
				log.info("id books to search on : "+idbooks);
				String sql = " from "+Books.class.getName()+" where idbooks = :booksid ";
				books.addAll(session.createQuery(sql).setParameter("booksid", idbooks).list());
				log.info("id books returned : "+idbooks);
				
			}
			
			session.close();
			
			return books;
			
		}else if(searchCriteria.containsKey("tags") && !searchCriteria.containsKey("books")){
			//TODO the pagination part of this query set
			return findBooksByTagsLazyLoad(searchCriteria.get("tags"), offset, numberOfRecords);
			
		}else if(searchCriteria.containsKey("books")){
			
			HashMap<String, String> booksSearchCriteria = searchCriteria.get("books");
			
			String booksWhereClause = "";
			
			if(sqlAppender.toString().length() > 0){
				booksWhereClause += " and idbooks in (select idbooks from books ";
			}
			
			booksWhereClause = " where ";
			
			count = 0;
			Map booksValuesMap = new HashMap();
			
			for(String booksKey : booksSearchCriteria.keySet()){
			
				if(count > 0){
					booksWhereClause += " and ";
				}
				
				booksWhereClause += "UPPER("+booksKey.toLowerCase()+") = UPPER(:"+booksKey+")";
				booksValuesMap.put(booksKey.toLowerCase(), searchCriteria.get(booksKey));
				count++;
			}
			
			log.info("booksWhereClause :::: "+booksWhereClause);
			
			//TODO the pagination part of this query set
			List list = session.createQuery(" from "+Books.class.getName()+booksWhereClause).setProperties(booksValuesMap).setFirstResult(offset).setMaxResults(numberOfRecords).list();
			
			return list;
		}
		
		return null;
	}
	
	
	public List<Books> findBooksByTagsLazyLoad(HashMap<String, String> tagsKeyValues, int offset, int numberOfRecords){
		Session session = this.getSessionFactory().openSession();
		
		StringBuffer sqlAppender = new StringBuffer();
		
		int count = 0;
		
		for(String key : tagsKeyValues.keySet()){
			
			count++;
			
			if(count > 1){
				sqlAppender.append(" and idbooks in (");
			}
			
			sqlAppender.append(" select idbooks from book_tags where ");
			sqlAppender.append("  UPPER(tag_type) = "+"UPPER('"+key+"')");
			sqlAppender.append(" and UPPER(tag_value) = "+"UPPER('"+tagsKeyValues.get(key)+"')");
			
			if(count > 1){
				sqlAppender.append(")");
			}
			
		}

		//entityManager.getEntityManagerFactory();
		
		log.info("sql appender value ::: "+sqlAppender.toString());
		
		//Query query = entityManager.getEntityManagerFactory().createEntityManager().createNativeQuery(sqlAppender.toString());
		//List list = query.getResultList();
		
		List list = session.createSQLQuery(sqlAppender.toString()).list(); //allows you to create native sql query
		
		List<Books> books = new ArrayList<Books>();
		
		for(Object obj : list){
			
			int idbooks = (Integer)obj;
			log.info("id books to search on : "+idbooks);
			String sql = " from "+Books.class.getName()+" where idbooks = :booksid ";
			books.addAll(session.createQuery(sql).setParameter("booksid", idbooks).setFirstResult(offset).setMaxResults(numberOfRecords).list());
			log.info("id books returned : "+idbooks);
			
		}
		
		session.close();
		return books;
	}
}
