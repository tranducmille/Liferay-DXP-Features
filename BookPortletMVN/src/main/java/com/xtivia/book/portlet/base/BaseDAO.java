package com.xtivia.book.portlet.base;

import java.util.List;

import com.xtivia.book.portlet.entity.Book;
import com.xtivia.book.portlet.repository.BookManager;

public abstract class BaseDAO<T extends BaseDomain, ID extends Number> {

	public void save(T instance){
		BookManager.getInstance().addBook((Book) instance);
	}
	
	public boolean delete(Integer id){
		BookManager.getInstance().deleteBookById(id);
		return true;
	}
	
	@SuppressWarnings("unchecked")
	public List<T> findAll(){
		return (List<T>) BookManager.getInstance().getBooks();
	}
	
	@SuppressWarnings("unchecked")
	public T findById(Integer id){
		return (T) BookManager.getInstance().getBookById(id);
	}
	
}
