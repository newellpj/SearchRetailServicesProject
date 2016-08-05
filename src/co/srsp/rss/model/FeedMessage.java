package co.srsp.rss.model;

public class FeedMessage {
	private String title;
	private String description;
	private String link;
	private String url;
	private String author;
	private String guid;
	private String imageWidth;
	private String imageHeight;
	

	
	
    public String getImageWidth() {
		return imageWidth;
	}

	public void setImageWidth(String imageWidth) {
		this.imageWidth = imageWidth;
	}

	public String getImageHeight() {
		return imageHeight;
	}

	public void setImageHeight(String imageHeight) {
		this.imageHeight = imageHeight;
	}

	public String getTitle() {
            return title;
    }

    public void setTitle(String title) {
            this.title = title;
    }

    public String getDescription() {
            return description;
    }

    public void setDescription(String description) {
            this.description = description;
    }

    public String getLink() {
            return link;
    }

    public void setLink(String link) {
            this.link = link;
    }
    
    public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getAuthor() {
            return author;
    }

    public void setAuthor(String author) {
            this.author = author;
    }

    public String getGuid() {
            return guid;
    }

    public void setGuid(String guid) {
            this.guid = guid;
    }

    @Override
    public String toString() {
            return "FeedMessage [title=" + title + ", description=" + description
                            + ", link=" + link + ", author=" + author + ", guid=" + guid
                            + "]";
    }
}
