package co.srsp.viewmodel;

public class HTMLModel {
	
	private String imageWidth;
	private String imageHeight;
	private String thumbnailLocation;
	private String thumbnailLocFullPath;
	private String title;
	private String author;
	private String publisher;
	private String excerpt;
	private String starRating;
	private String reviewerText;
	private String reviewersUserName;	
	private String bookDetails;
	private String docID;
	private String specifiedDocumentContentExtract;
	private String largerContent;
		

	public String getdocID() {
		return docID;
	}
	public void setdocID(String docID) {
		this.docID = docID;
	}
	public String getspecifiedDocumentContentExtract() {
		return specifiedDocumentContentExtract;
	}
	public void setspecifiedDocumentContentExtract(String specifiedDocumentContentExtract) {
		this.specifiedDocumentContentExtract = specifiedDocumentContentExtract;
	}
	public String getlargerContent() {
		return largerContent;
	}
	public void setlargerContent(String largerContent) {
		this.largerContent = largerContent;
	}
	public String getthumbnailLocFullPath() {
		return thumbnailLocFullPath;
	}
	public void setthumbnailLocFullPath(String thumbnailLocFullPath) {
		this.thumbnailLocFullPath = thumbnailLocFullPath;
	}
	public String getbookDetails() {
		return bookDetails;
	}
	public void setbookDetails(String bookDetails) {
		this.bookDetails = bookDetails;
	}
	public String getstarRating() {
		return starRating;
	}
	public void setstarRating(String starRating) {
		this.starRating = starRating;
	}
	public String getreviewerText() {
		return reviewerText;
	}
	public void setreviewerText(String reviewerText) {
		this.reviewerText = reviewerText;
	}
	public String getreviewersUserName() {
		return reviewersUserName;
	}
	public void setreviewersUserName(String reviewersUserName) {
		this.reviewersUserName = reviewersUserName;
	}
	public String getimageWidth() {
		return imageWidth;
	}
	public void setimageWidth(String imageWidth) {
		this.imageWidth = imageWidth;
	}
	public String getimageHeight() {
		return imageHeight;
	}
	public void setimageHeight(String imageHeight) {
		this.imageHeight = imageHeight;
	}
	public String getthumbnailLocation() {
		return thumbnailLocation;
	}
	public void setthumbnailLocation(String thumbnailLocation) {
		this.thumbnailLocation = thumbnailLocation;
	}
	public String gettitle() {
		return title;
	}
	public void settitle(String title) {
		this.title = title;
	}
	public String getauthor() {
		return author;
	}
	public void setauthor(String author) {
		this.author = author;
	}
	public String getpublisher() {
		return publisher;
	}
	public void setpublisher(String publisher) {
		this.publisher = publisher;
	}
	public String getexcerpt() {
		return excerpt;
	}
	public void setexcerpt(String excerpt) {
		this.excerpt = excerpt;
	}
	
	
}
