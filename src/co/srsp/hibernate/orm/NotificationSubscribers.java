package co.srsp.hibernate.orm;

public class NotificationSubscribers {

	private long idnotification_subscribers = -1;
	private String subscriptionID;
	private String username;
	private String event_name;
	
	public long getIdnotification_subscribers() {
		return idnotification_subscribers;
	}
	public void setIdnotification_subscribers(long idnotification_subscribers) {
		this.idnotification_subscribers = idnotification_subscribers;
	}
	public String getSubscriptionID() {
		return subscriptionID;
	}
	public void setSubscriptionID(String subscriptionID) {
		this.subscriptionID = subscriptionID;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEvent_name() {
		return event_name;
	}
	public void setEvent_name(String event_name) {
		this.event_name = event_name;
	}
	
	
}