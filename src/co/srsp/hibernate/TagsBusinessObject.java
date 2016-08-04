package co.srsp.hibernate;

import java.util.HashMap;
import java.util.List;

import co.srsp.hibernate.orm.BookTags;
import co.srsp.hibernate.orm.Books;

public interface TagsBusinessObject {
	public void save(BookTags books);
	public void update(BookTags books);
	public void delete(BookTags books);
	public List<Books> findBooksByTagsLazyLoad(HashMap<String, String> tagsKeyValues, int offset, int numberOfRecords);
}
