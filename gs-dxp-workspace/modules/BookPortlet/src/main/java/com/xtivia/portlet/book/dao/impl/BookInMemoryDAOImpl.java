package com.xtivia.portlet.book.dao.impl;

import java.util.List;

import com.xtivia.portlet.book.dao.BookDAOInterface;
import com.xtivia.portlet.book.entity.Book;

public class BookInMemoryDAOImpl implements BookDAOInterface {

	@Override
	public List<Book> getBookList() {
		return BookManager.getInstance().getBooks();
	}
	
	@Override
	public List<Book> getBookList(String isbn) {
		return BookManager.getInstance().findBooksByISBN(isbn);
	}

	@Override
	public Book getBook(int id) {
		return BookManager.getInstance().getBookById(id);
	}

	@Override
	public void deleteBook(int id) {
		BookManager.getInstance().deleteBookById(id);
		
	}

	@Override
	public void addBook(Book book) {
		BookManager.getInstance().addBook(book);		
	}
}
