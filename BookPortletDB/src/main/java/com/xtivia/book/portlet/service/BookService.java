package com.xtivia.book.portlet.service;

import java.util.List;

import com.xtivia.book.portlet.entity.Book;
/**
 * @author created by dtran
 * A Service Interface
 */
public interface BookService {

	public List<Book> getBookList();
	
	public List<Book> getBookList(String isbn);
	
	public Book getBook(int id);
	
	public void deleteBook(int id);
	
	public void addBook(Book book);
	
	public void update(Book book);
	
}
