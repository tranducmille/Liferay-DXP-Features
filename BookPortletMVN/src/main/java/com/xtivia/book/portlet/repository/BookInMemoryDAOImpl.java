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
	
	public List<Book> getBookList() {
		return BookManager.getInstance().getBooks();
	}
	
	public List<Book> getBookList(String isbn) {
		return BookManager.getInstance().findBooksByISBN(isbn);
	}

	public Book getBook(int id) {
		return BookManager.getInstance().getBookById(id);
	}

	public void deleteBook(int id) {
		BookManager.getInstance().deleteBookById(id);
		
	}

	public void addBook(Book book) {
		BookManager.getInstance().addBook(book);		
	}
}
