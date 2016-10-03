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

import co.srsp.constants.SessionConstants;
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
		log.info("findBooksByAnyCriteriaLazyLoad");
		Session session = this.getSessionFactory().openSession();
		StringBuffer sqlAppender = new StringBuffer();
		int count = 0;
		
		HashMap<String, String> tagsMap  = searchCriteria.get(SessionConstants.TAGS_SEARCH_CRITERIA);
		HashMap<String, String> booksMap  = searchCriteria.get(SessionConstants.BOOKS_SEARCH_CRITERIA);
		
		if(tagsMap != null && tagsMap.size() > 0 && booksMap != null && booksMap.size() > 0){
			log.info("tags and books...................................");
			HashMap<String, String> tagsKeyValues = searchCriteria.get(SessionConstants.TAGS_SEARCH_CRITERIA);

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
			
			
			HashMap<String, String> booksSearchCriteria = searchCriteria.get(SessionConstants.BOOKS_SEARCH_CRITERIA);
			
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
				
				booksWhereClause += "UPPER("+booksKey.toLowerCase()+") = UPPER('"+booksSearchCriteria.get(booksKey)+"')";
				booksValuesMap.put(booksKey.toLowerCase(), searchCriteria.get(booksKey));
				count++;
			}
			
			booksWhereClause += ")";
			
			log.info("booksWhereClause :::: "+booksWhereClause);
			
			sqlAppender.append(booksWhereClause);
			
			//TODO the pagination part of this query set
			
			log.info("sql appender ::: "+sqlAppender.toString());
			log.info("sql offset ::: "+offset);
			log.info("sql numberOfRecords ::: "+numberOfRecords);
			
			//setProperties(booksValuesMap).setFirstResult(offset).setMaxResults(numberOfRecords).list();
			
			List list = session.createSQLQuery(sqlAppender.toString()).setFirstResult(offset).setMaxResults(numberOfRecords).list();
			
			log.info("list size returned : "+list);
			log.info("list size returned : "+list.size());
			
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
			
		}else if((tagsMap != null && tagsMap.size() > 0) && (booksMap == null || booksMap.size() <= 0)){
			//TODO the pagination part of this query set
			log.info("tags only search criteria");
			return findBooksByTagsLazyLoad(searchCriteria.get(SessionConstants.TAGS_SEARCH_CRITERIA), offset, numberOfRecords);
			
		}else if(booksMap != null && booksMap.size() > 0){
			log.info("books only search criteria");
			HashMap<String, String> booksSearchCriteria = searchCriteria.get(SessionConstants.BOOKS_SEARCH_CRITERIA);
			
			String booksWhereClause = "";
			
			if(sqlAppender.toString().length() > 0){
				booksWhereClause += " and idbooks in (select idbooks from books ";
			}
			
			booksWhereClause = " where ";
			
			count = 0;
			Map booksValuesMap = new HashMap();
			log.info("before key message size : "+booksSearchCriteria.size());
			for(String booksKey : booksSearchCriteria.keySet()){
			
				log.info("count in books search criteria : "+count);
				log.info("key in books search criteria : "+booksKey);
				
				if(count > 0){
					booksWhereClause += " and ";
				}
				log.info("books key : "+booksKey.toLowerCase());
				log.info("books criteria values : "+booksSearchCriteria.get(booksKey));
				
				booksWhereClause += " UPPER("+booksKey.toLowerCase()+") = UPPER(:"+booksKey+") ";
				booksValuesMap.put(booksKey.toLowerCase(), booksSearchCriteria.get(booksKey));
				count++;
				
			}
			
			log.info("booksWhereClause :::: "+booksWhereClause);
			log.info("sql appender ::: "+sqlAppender.toString());
			log.info("sql offset ::: "+offset);
			log.info("sql numberOfRecords ::: "+numberOfRecords);
			
			
			//TODO the pagination part of this query set
			try{
				List list = session.createQuery(" from "+Books.class.getName()+booksWhereClause).setProperties(booksValuesMap).setFirstResult(offset).setMaxResults(numberOfRecords).list();
				log.info("list size returned : "+list);
				log.info("list size returned : "+list.size());
				
				return list;
				
			}catch(Exception e){
				e.printStackTrace();
				log.error(e.getMessage());
			}
			
		}
		
		return null;
	}
	
	
	public List<Books> findBooksByTagsLazyLoad(HashMap<String, String> tagsKeyValues, int offset, int numberOfRecords){
		
		log.info("findBooksByTagsLazyLoad");
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
