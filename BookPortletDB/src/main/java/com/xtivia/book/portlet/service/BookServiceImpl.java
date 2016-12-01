package com.xtivia.book.portlet.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xtivia.book.portlet.base.BaseDAO;
import com.xtivia.book.portlet.base.BaseService;
import com.xtivia.book.portlet.entity.Book;
import com.xtivia.book.portlet.repository.BookInMemoryDAO;

/**
 * @author created by dtran
 * A Service implementation
 */
@Service("bookService")
@Transactional
public class BookServiceImpl extends BaseService<Book, BaseDAO> implements BookService{

	@Autowired(required = true)
	BookInMemoryDAO bookDAO;
	
	public BookServiceImpl(){}
	
	/* (non-Javadoc)
	 * @see com.xtivia.book.portlet.service.BookService#getBookList()
	 */
	public List<Book> getBookList() {
		return bookDAO.getBookList();
	}

	/* (non-Javadoc)
	 * @see com.xtivia.book.portlet.service.BookService#getBookList(java.lang.String)
	 */
	public List<Book> getBookList(String isbn) {
		return bookDAO.getBookList(isbn);
	}

	/* (non-Javadoc)
	 * @see com.xtivia.book.portlet.service.BookService#getBook(int)
	 */
	public Book getBook(int id) {
		return bookDAO.getBook(id);
	}

	/* (non-Javadoc)
	 * @see com.xtivia.book.portlet.service.BookService#deleteBook(int)
	 */
	public void deleteBook(int id) {
		bookDAO.deleteBook(id);
	}

	/* (non-Javadoc)
	 * @see com.xtivia.book.portlet.service.BookService#addBook(com.xtivia.book.portlet.entity.Book)
	 */
	public void addBook(Book book) {
		bookDAO.addBook(book);
	}

	/* (non-Javadoc)
	 * @see com.xtivia.book.portlet.service.BookService#update(com.xtivia.book.portlet.entity.Book)
	 */
	@Override
	public void update(Book book) {
		bookDAO.updateBook(book);		
	}

}
