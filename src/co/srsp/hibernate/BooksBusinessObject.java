package co.srsp.hibernate;

import java.util.HashMap;
import java.util.List;

import co.srsp.hibernate.orm.BookReviews;
import co.srsp.hibernate.orm.Books;

public interface BooksBusinessObject {
	public void save(Books books);
	public void update(Books books);
	public void delete(Books books, List<BookReviews> bookReviews);
	
	public Books findBooksByTitleOnly(String title);
	
	/**
	 * gets book by title and/or author. If author is null it will just search on title.
	 * @param title
	 * @param author
	 * @return Books ORM object
	 */
	public  List<Books> findBooksByTitleAndAuthor(String title, String author);
	
	public  List<Books> findBookListByPartialMatch(HashMap<String, String> searchCriteria);
}
