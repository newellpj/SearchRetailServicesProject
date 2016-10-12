package co.srsp.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import co.srsp.constants.SessionConstants;
import co.srsp.hibernate.orm.Books;
import co.srsp.hibernate.orm.NotificationSubscribers;

public class NotificationsBusinessObjectImpl extends HibernateDaoSupport implements NotificationsBusinessObject{

	@Transactional
	@Override
	public void save(NotificationSubscribers notificationSubscibers) {
		
		Session session = this.getSessionFactory().openSession();
		session.save(notificationSubscibers);
		session.flush();
		session.close();
	}

	@Transactional
	@Override
	public void update(NotificationSubscribers notificationSubscibers) {
		
		Session session = this.getSessionFactory().openSession();
		session.update(notificationSubscibers);
		session.flush();
		session.close();
	}

	@Transactional
	@Override
	public void delete(NotificationSubscribers notificationSubscibers) {
		
		Session session = this.getSessionFactory().openSession();
		session.update(notificationSubscibers);
		session.flush();
		session.close();
	}

	@Override
	public List<NotificationSubscribers> findSubscribers(HashMap<String, String> keysAndValues) {
		
		Session session = this.getSessionFactory().openSession();

		int count = 0;

		StringBuffer sqlAppenderBuffer = new StringBuffer();	
		sqlAppenderBuffer.append(" from "+NotificationSubscribers.class.getName()+" where ");
		
		for(String key : keysAndValues.keySet()){

			if(count > 1){
				sqlAppenderBuffer.append(" and ");
			}

			String value = (String)keysAndValues.get(key);
			sqlAppenderBuffer.append(key+" = "+value);
			count++;
		}
		
		List<NotificationSubscribers> list = session.createQuery(sqlAppenderBuffer.toString()).list();
		
		return list;
	}

}
