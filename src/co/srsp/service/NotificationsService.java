package co.srsp.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import co.srsp.hibernate.NotificationsBusinessObject;
import co.srsp.hibernate.orm.NotificationSubscribers;

public class NotificationsService {
	
	private ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
	private NotificationsBusinessObject pushNotificationsBO = (NotificationsBusinessObject) ctx.getBean("pushNotificationsController");
	
	public void save(NotificationSubscribers notificationSubscibers){
		pushNotificationsBO.save(notificationSubscibers);
	}
	
	public void update(NotificationSubscribers notificationSubscibers){
		pushNotificationsBO.update(notificationSubscibers);
	}
	
	public void delete(NotificationSubscribers notificationSubscibers){
		pushNotificationsBO.delete(notificationSubscibers);
	}
	
	public List<NotificationSubscribers> findSubscribers(HashMap<String, String> keysAndValues){
		return pushNotificationsBO.findSubscribers(keysAndValues);
	}
}
