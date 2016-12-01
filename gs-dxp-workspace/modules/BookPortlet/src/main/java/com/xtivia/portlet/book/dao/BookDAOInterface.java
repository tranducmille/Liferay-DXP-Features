package com.xtivia.portlet.book.dao;

import java.util.List;

import com.xtivia.portlet.book.entity.Book;

public interface BookDAOInterface {

	public List<Book> getBookList();
	
	public List<Book> getBookList(String isbn);
	
	public Book getBook(int id);
	
	public void deleteBook(int id);
	
	public void addBook(Book book);
}
