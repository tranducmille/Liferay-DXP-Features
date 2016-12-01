package com.xtivia.book.portlet.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xtivia.book.portlet.base.BaseDAO;
import com.xtivia.book.portlet.base.BaseService;
import com.xtivia.book.portlet.entity.Book;
import com.xtivia.book.portlet.repository.BookInMemoryDAO;

@Service("bookService")
@Transactional
public class BookServiceImpl extends BaseService<Book, BaseDAO> implements BookService{

	@Autowired(required = true)
	BookInMemoryDAO bookDAO;
	
	public BookServiceImpl(){}
	
	public List<Book> getBookList() {
		return bookDAO.getBookList();
	}

	public List<Book> getBookList(String isbn) {
		return bookDAO.getBookList(isbn);
	}

	public Book getBook(int id) {
		return bookDAO.getBook(id);
	}

	public void deleteBook(int id) {
		bookDAO.deleteBook(id);
	}

	public void addBook(Book book) {
		bookDAO.addBook(book);
	}

	@Override
	public void update(Book book) {
		bookDAO.updateBook(book);		
	}

}
