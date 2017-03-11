package co.srsp.viewmodel;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class BookReviewsModel implements Serializable{
	
	private String booksList;
	
	private int booksID;
	
	private int booksReviewsID;
	
	@Size(min=5,max=40)
	private String username;
	
	@NotNull
	@Size(min=5,max=40)
	private String titleText;

	@NotNull
	@Size(min=1,max=40)
	private String authorText;
	
	@Size(min=1,max=40)
	private String titleTextAdd;
	
	@Size(min=1,max=40)
	private String authorTextAdd;
	
	@Size(min=1,max=300)
	private String reviewText;
	
	@Size(min=1,max=40)
	private String bookTitleReview;
	
	@Size(min=1,max=40)
	private String bookAuthorReview;
	
	@Size(min=1,max=40)
	private String publisherText;

	private String excerpt;
	
	private String thumbnailLocation;
		
	private String imageHeight;
	
	private String imageWidth;
	
	private int starRating;
	

	public int getStarRating() {
		return starRating;
	}

	public void setStarRating(int starRating) {
		this.starRating = starRating;
	}

	public String getImageHeight() {
		return imageHeight;
	}

	public void setImageHeight(String imageHeight) {
		this.imageHeight = imageHeight;
	}

	public String getImageWidth() {
		return imageWidth;
	}

	public void setImageWidth(String imageWidth) {
		this.imageWidth = imageWidth;
	}

	public String getExcerpt() {
		return excerpt;
	}

	public void setExcerpt(String excerpt) {
		this.excerpt = excerpt;
	}

	public String getThumbnailLocation() {
		return thumbnailLocation;
	}

	public void setThumbnnalLocation(String thumbnailLocation) {
		this.thumbnailLocation = thumbnailLocation;
	}

	public String getBooksList(){
		return booksList;
	}

	public void setBooksList(String booksList) {
		this.booksList = booksList;
	}

	public String getPublisherText() {
		return publisherText;
	}

	public void setPublisherText(String publisherText) {
		this.publisherText = publisherText;
	}

	public String getBookTitleReview() {
		return bookTitleReview;
	}

	public void setBookTitleReview(String bookTitleReview) {
		this.bookTitleReview = bookTitleReview;
	}

	public String getBookAuthorReview() {
		return bookAuthorReview;
	}

	public void setBookAuthorReview(String bookAuthorReview) {
		this.bookAuthorReview = bookAuthorReview;
	}

	public int getBooksID() {
		return booksID;
	}

	public void setBooksID(int booksID) {
		this.booksID = booksID;
	}

	public int getBooksReviewsID() {
		return booksReviewsID;
	}

	public void setBooksReviewsID(int booksReviewsID) {
		this.booksReviewsID = booksReviewsID;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	

	public String getTitleText() {
		return titleText;
	}

	public void setTitleText(String titleText) {
		this.titleText = titleText;
	}

	public String getAuthorText() {
		return authorText;
	}

	public void setAuthorText(String authorText) {
		this.authorText = authorText;
	}

	public String getTitleTextAdd() {
		return titleTextAdd;
	}

	public void setTitleTextAdd(String titleTextAdd) {
		this.titleTextAdd = titleTextAdd;
	}

	public String getAuthorTextAdd() {
		return authorTextAdd;
	}

	public void setAuthorTextAdd(String authorTextAdd) {
		this.authorTextAdd = authorTextAdd;
	}

	public String getReviewText() {
		return reviewText;
	}

	public void setReviewText(String reviewText) {
		this.reviewText = reviewText;
	}

	public BookReviewsModel(){}

	
}
