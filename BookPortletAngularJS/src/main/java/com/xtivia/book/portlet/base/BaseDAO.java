package com.xtivia.book.portlet.base;

import java.util.List;

import com.xtivia.book.portlet.entity.Book;
import com.xtivia.book.portlet.repository.BookManager;

/**
 * @author dtran
 * A abstract class for DAO implementation classes
 * @param <T> Domain
 * @param <ID> Number
 */
public abstract class BaseDAO<T extends BaseDomain, ID extends Number> {

	/**
	 * save an instance
	 * @param instance
	 */
	public void save(T instance){
		BookManager.getInstance().addBook((Book) instance);
	}
	
	/**
	 * delete an instance by id
	 * @param id
	 * @return true/false
	 */
	public boolean delete(Integer id){
		BookManager.getInstance().deleteBookById(id);
		return true;
	}
	
	/**
	 * find all instance
	 * @return list of instance
	 */
	@SuppressWarnings("unchecked")
	public List<T> findAll(){
		return (List<T>) BookManager.getInstance().getBooks();
	}
	
	/**
	 * find by id
	 * @param id
	 * @return instance
	 */
	@SuppressWarnings("unchecked")
	public T findById(Integer id){
		return (T) BookManager.getInstance().getBookById(id);
	}
	
}
