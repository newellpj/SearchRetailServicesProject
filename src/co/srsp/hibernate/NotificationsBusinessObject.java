package co.srsp.hibernate;

import java.util.HashMap;
import java.util.List;

import co.srsp.hibernate.orm.Books;
import co.srsp.hibernate.orm.NotificationSubscribers;

public interface NotificationsBusinessObject {
	public void save(NotificationSubscribers notificatioSubscibers);
	public void update(NotificationSubscribers notificatioSubscibers);
	public void delete(NotificationSubscribers notificatioSubscibers);
	public List<Books> findSubscribers(HashMap<String, String> tagsKeyValues);
}
