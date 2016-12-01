package com.xtivia.book.portlet.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.liferay.portal.kernel.util.Validator;
import com.xtivia.book.portlet.base.BaseDAO;
import com.xtivia.book.portlet.entity.Book;

/**
 * @author created by dtran
 * A DAO implementation
 */
@Repository("bookDAO")
@Transactional
public class BookInMemoryDAOImpl extends BaseDAO<Book, Number> implements BookInMemoryDAO {
	
	/* (non-Javadoc)
	 * @see com.xtivia.book.portlet.repository.BookInMemoryDAO#getBookList()
	 */
	@Transactional
	public List<Book> getBookList() {
		return this.findAll();
	}
	
	/* (non-Javadoc)
	 * @see com.xtivia.book.portlet.repository.BookInMemoryDAO#getBookList(java.lang.String)
	 */
	@Transactional
	public List<Book> getBookList(String isbn) {
		return this.findByCriteria("isbn", isbn);
	}

	/* (non-Javadoc)
	 * @see com.xtivia.book.portlet.repository.BookInMemoryDAO#getBook(int)
	 */
	@Transactional
	public Book getBook(int id) {
		return this.findById(id);
	}

	/* (non-Javadoc)
	 * @see com.xtivia.book.portlet.repository.BookInMemoryDAO#deleteBook(int)
	 */
	@Transactional
	public void deleteBook(int id) {
		Book book = this.findById(id);
		if(!Validator.isNull(book)){
			this.delete(book);
		}
	}
	/* (non-Javadoc)
	 * @see com.xtivia.book.portlet.repository.BookInMemoryDAO#addBook(com.xtivia.book.portlet.entity.Book)
	 */
	@Transactional
	public void addBook(Book book) {
		this.save(book);		
	}

	/* (non-Javadoc)
	 * @see com.xtivia.book.portlet.repository.BookInMemoryDAO#updateBook(com.xtivia.book.portlet.entity.Book)
	 */
	@Transactional
	public void updateBook(Book book) {
		this.saveOrUpdate(book);
		
	}
}
