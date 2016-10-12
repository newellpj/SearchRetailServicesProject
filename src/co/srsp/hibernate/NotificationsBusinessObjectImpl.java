package co.srsp.hibernate;

import java.util.HashMap;
import java.util.List;

import org.hibernate.Session;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import co.srsp.hibernate.orm.Books;
import co.srsp.hibernate.orm.NotificationSubscribers;

public class NotificationsBusinessObjectImpl extends HibernateDaoSupport implements NotificationsBusinessObject{

	@Transactional
	@Override
	public void save(NotificationSubscribers notificatioSubscibers) {
		// TODO Auto-generated method stub
		Session session = this.getSessionFactory().openSession();
		session.save(notificatioSubscibers);
		session.flush();
		session.close();
	}

	@Transactional
	@Override
	public void update(NotificationSubscribers notificatioSubscibers) {
		// TODO Auto-generated method stub
		Session session = this.getSessionFactory().openSession();
		session.update(notificatioSubscibers);
		session.flush();
		session.close();
	}

	@Transactional
	@Override
	public void delete(NotificationSubscribers notificatioSubscibers) {
		// TODO Auto-generated method stub
		Session session = this.getSessionFactory().openSession();
		session.update(notificatioSubscibers);
		session.flush();
		session.close();
	}

	@Override
	public List<Books> findSubscribers(HashMap<String, String> tagsKeyValues) {
		// TODO Auto-generated method stub
		return null;
	}

}
