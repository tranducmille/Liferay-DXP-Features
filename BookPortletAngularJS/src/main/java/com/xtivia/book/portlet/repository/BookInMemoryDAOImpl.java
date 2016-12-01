package com.xtivia.book.portlet.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.xtivia.book.portlet.base.BaseDAO;
import com.xtivia.book.portlet.base.BaseDomain;
import com.xtivia.book.portlet.entity.Book;

/**
 * @author created by dtran
 * A DAO implementation
 */
@Repository("bookDAO")
public class BookInMemoryDAOImpl extends BaseDAO<BaseDomain, Number> implements BookInMemoryDAO {

	public BookInMemoryDAOImpl(){};
	
	/* (non-Javadoc)
	 * @see com.xtivia.book.portlet.repository.BookInMemoryDAO#getBookList()
	 */
	public List<Book> getBookList() {
		return BookManager.getInstance().getBooks();
	}
	
	/* (non-Javadoc)
	 * @see com.xtivia.book.portlet.repository.BookInMemoryDAO#getBookList(java.lang.String)
	 */
	public List<Book> getBookList(String isbn) {
		return BookManager.getInstance().findBooksByISBN(isbn);
	}

	/* (non-Javadoc)
	 * @see com.xtivia.book.portlet.repository.BookInMemoryDAO#getBook(int)
	 */
	public Book getBook(int id) {
		return BookManager.getInstance().getBookById(id);
	}

	/* (non-Javadoc)
	 * @see com.xtivia.book.portlet.repository.BookInMemoryDAO#deleteBook(int)
	 */
	public void deleteBook(int id) {
		BookManager.getInstance().deleteBookById(id);
		
	}

	/* (non-Javadoc)
	 * @see com.xtivia.book.portlet.repository.BookInMemoryDAO#addBook(com.xtivia.book.portlet.entity.Book)
	 */
	public void addBook(Book book) {
		BookManager.getInstance().addBook(book);		
	}
}
