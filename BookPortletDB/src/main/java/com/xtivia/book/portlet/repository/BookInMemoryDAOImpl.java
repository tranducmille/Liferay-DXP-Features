package com.xtivia.book.portlet.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.liferay.portal.kernel.util.Validator;
import com.xtivia.book.portlet.base.BaseDAO;
import com.xtivia.book.portlet.entity.Book;

@Repository("bookDAO")
@Transactional
public class BookInMemoryDAOImpl extends BaseDAO<Book, Number> implements BookInMemoryDAO {
	
	@Transactional
	public List<Book> getBookList() {
		return this.findAll();
	}
	
	@Transactional
	public List<Book> getBookList(String isbn) {
		return this.findByCriteria("isbn", isbn);
	}

	@Transactional
	public Book getBook(int id) {
		return this.findById(id);
	}

	@Transactional
	public void deleteBook(int id) {
		Book book = this.findById(id);
		if(!Validator.isNull(book)){
			this.delete(book);
		}
	}
	@Transactional
	public void addBook(Book book) {
		this.save(book);		
	}

	@Transactional
	public void updateBook(Book book) {
		this.saveOrUpdate(book);
		
	}
}
