package co.srsp.hibernate;

import java.util.HashMap;
import java.util.List;

import co.srsp.hibernate.orm.Books;
import co.srsp.hibernate.orm.NotificationSubscribers;

public interface NotificationsBusinessObject {
	public void save(NotificationSubscribers notificationSubscibers);
	public void update(NotificationSubscribers notificationSubscibers);
	public void delete(NotificationSubscribers notificationSubscibers);
	public List<NotificationSubscribers> findSubscribers(HashMap<String, String> keysAndValues);
}
