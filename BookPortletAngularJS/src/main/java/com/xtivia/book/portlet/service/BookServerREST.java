package com.xtivia.book.portlet.service;

import java.util.List;

import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;

import com.xtivia.book.portlet.entity.Book;
import com.xtivia.book.portlet.util.SequenceGenerator;
import com.xtivia.xsf.core.annotation.Route;
import com.xtivia.xsf.core.commands.CommandResult;
import com.xtivia.xsf.core.commands.ICommand;
import com.xtivia.xsf.core.commands.IContext;
import com.xtivia.xsf.core.web.Xsf;

@Route(uri = "/books")
public class BookServerREST implements ICommand {
	
	@Autowired(required = true)
	BookService bookService;
	
	@Route(uri="", method="GET", authenticated=false)
	public CommandResult getAllPeople(IContext context) {
		List<Book> books = bookService.getBookList();
		return new CommandResult().setSucceeded(true).setData(books).setMessage("OK");
	}

	public CommandResult execute(IContext context) throws Exception {
		return Xsf.dispatch(this, context);
	}

	@Route(uri = "/{id}", method = "GET", authenticated = false)
	public CommandResult getBook(IContext context) {
		CommandResult cr = new CommandResult().setSucceeded(false).setMessage("General error");
		try {
			Book book = bookService.getBook(getInputId(context));
			if (book != null) {
				cr.setSucceeded(true).setData(book).setMessage("");
			} else {
				cr.setMessage("Requested book not found");
			}
		} catch (Exception e) {
			cr.setMessage(e.getMessage());
		}
		return cr;

	}
	
	@Route(uri = "", method = "POST", authenticated = false,
			inputClass = "com.xtivia.book.portlet.service.BookServerREST$Book", inputKey = "book")
	public CommandResult addBook(IContext context) {
		CommandResult cr = new CommandResult().setSucceeded(false).setMessage("General error");
		try {

			Book newBook = getInputBook(context);
			validateBook(newBook);
			bookService.addBook(new Book(SequenceGenerator.getInstance().getSequence(), newBook.getTitle(),
					newBook.getAuthor(), newBook.getIsbn(), newBook.getSummary()));
			
			cr.setSucceeded(true).setData(newBook).setMessage("OK");
		} catch (Exception e) {
			cr.setMessage(e.getMessage());
		}

		return cr;
	}
	
	@Route(uri = "/{id}", method = "PUT", authenticated = false,
			inputClass = "com.xtivia.book.portlet.service.BookServerREST$Book", inputKey = "book")
	public CommandResult updateBook(IContext context) {
		CommandResult cr = new CommandResult().setSucceeded(false).setMessage("General error");
		try {

			Book oldBook = bookService.getBook(getInputId(context));
			if (oldBook != null) {
				Book newBook = getInputBook(context);
				validateBook(newBook);
				oldBook.setTitle(newBook.getTitle());
				oldBook.setAuthor(newBook.getAuthor());
				oldBook.setIsbn(newBook.getIsbn());
				oldBook.setSummary(newBook.getSummary());
				cr.setSucceeded(true).setMessage("OK");
			} else {
				cr.setMessage("Requested to update non-existing book");
			}

		} catch (Exception e) {
			cr.setMessage(e.getMessage());
		}
		return cr;
	}
	
	@Route(uri = "/{id}", method = "DELETE", authenticated = false)
	public CommandResult deleteBook(IContext context) {
		CommandResult cr = new CommandResult().setSucceeded(false).setMessage("General error");

		try {
			int id = getInputId(context);
			bookService.deleteBook(id);
			cr.setSucceeded(true).setMessage("OK");
		} catch (Exception e) {
			cr.setMessage(e.getMessage());
		}
		return cr;

	}

	private static void validateBook(Book book) throws Exception {

		Validate.notNull(book.getTitle(), "Title is required.");
		Validate.notNull(book.getAuthor(), "Author is required.");
		Validate.notNull(book.getIsbn(), "ISBN is required.");

	}
	private static int getInputId(IContext context) throws Exception {

		  String idstr = context.find("id");
		  Validate.notNull(idstr,"ID is null on get request");
		  return new Integer(idstr);

	}
	private static Book getInputBook(IContext context) throws Exception {

		Book book = context.find("book");
		Validate.notNull(book,"Required book object not found in input");
		return book;

	}
}
