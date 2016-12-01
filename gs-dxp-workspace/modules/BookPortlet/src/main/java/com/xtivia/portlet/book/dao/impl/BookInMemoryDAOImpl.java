package com.xtivia.portlet.book.dao.impl;

import java.util.List;

import com.xtivia.portlet.book.dao.BookDAOInterface;
import com.xtivia.portlet.book.entity.Book;

/**
 * @author created by dtran
 * A DAO implementation
 */
public class BookInMemoryDAOImpl implements BookDAOInterface {

	/* (non-Javadoc)
	 * @see com.xtivia.portlet.book.dao.BookDAOInterface#getBookList()
	 */
	@Override
	public List<Book> getBookList() {
		return BookManager.getInstance().getBooks();
	}
	
	/* (non-Javadoc)
	 * @see com.xtivia.portlet.book.dao.BookDAOInterface#getBookList(java.lang.String)
	 */
	@Override
	public List<Book> getBookList(String isbn) {
		return BookManager.getInstance().findBooksByISBN(isbn);
	}

	/* (non-Javadoc)
	 * @see com.xtivia.portlet.book.dao.BookDAOInterface#getBook(int)
	 */
	@Override
	public Book getBook(int id) {
		return BookManager.getInstance().getBookById(id);
	}

	/* (non-Javadoc)
	 * @see com.xtivia.portlet.book.dao.BookDAOInterface#deleteBook(int)
	 */
	@Override
	public void deleteBook(int id) {
		BookManager.getInstance().deleteBookById(id);
		
	}

	/* (non-Javadoc)
	 * @see com.xtivia.portlet.book.dao.BookDAOInterface#addBook(com.xtivia.portlet.book.entity.Book)
	 */
	@Override
	public void addBook(Book book) {
		BookManager.getInstance().addBook(book);		
	}
}
