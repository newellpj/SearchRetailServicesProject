package co.srsp.service;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import co.srsp.hibernate.BookReviewsBusinessObject;
import co.srsp.hibernate.BooksBusinessObject;
import co.srsp.hibernate.TagsBusinessObject;

public class NotificationsService {
	
	private ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
	private NotificationsBO pushNotificationsController = (TagsBusinessObject) ctx.getBean("pushNotificationsController");
}
