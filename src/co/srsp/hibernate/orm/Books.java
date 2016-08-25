package co.srsp.hibernate.orm;

public class Books implements java.io.Serializable {

	private static final long serialVersionUID = 4744798857627076674L;
	private Integer idbooks;
	private String title;
	private String author;
	private String publisher;
	private String thumbnailLocation;
	private String excerpt;
	
	public Books() {
	}

	public Books(String title, String author) {
		this.title = title;
		this.author = author;
	}

	public Integer getIdbooks() {
		return this.idbooks;
	}

	public void setIdbooks(Integer idbooks) {
		this.idbooks = idbooks;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return this.author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getThumbnailLocation() {
		return thumbnailLocation;
	}

	public void setThumbnailLocation(String thumbnailLocation) {
		this.thumbnailLocation = thumbnailLocation;
	}

	public String getExcerpt() {
		return excerpt;
	}

	public void setExcerpt(String excerpt) {
		this.excerpt = excerpt;
	}
	
	

}
