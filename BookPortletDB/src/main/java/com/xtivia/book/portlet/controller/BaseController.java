package com.xtivia.book.portlet.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Modified;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ParamUtil;
import com.xtivia.book.portlet.configuration.BookConfiguration;
import com.xtivia.book.portlet.entity.Book;
import com.xtivia.book.portlet.service.BookService;
import com.xtivia.book.portlet.util.BookValidator;

import aQute.bnd.annotation.metatype.Configurable;

@Controller("baseController")
@RequestMapping("VIEW")
public class BaseController {

	@Autowired(required = true)
	BookService bookService;
	
	@RenderMapping
	public String processRenderRequest(RenderRequest request, RenderResponse response) {
		System.out.println("processRenderRequest");
		List<Book> books = bookService.getBookList();
		request.setAttribute("books", books);
		System.out.println(books.toString());
		request.setAttribute(BookConfiguration.class.getName(), _configuration);
		return "view";
	}
	
	@RenderMapping(params = "action=displayBookDetails")
	public String displayBookDetails(@RequestParam("bookId")Integer bookId, 
											@RequestParam("jspPage")String page,  Model model) {
		Book book = bookService.getBook(bookId);
		model.addAttribute("book", book);
		return page;		
	}
	
	@RenderMapping(params = "action=redirectToBookReg")
	public String redirectBookRegistration(@RequestParam("jspPage")String page, Model model) {
		model.addAttribute("book", new Book());
		return page;		
	}
	
	@RequestMapping("VIEW")
	@ActionMapping(params = "action=redirectToEditBook")
	public void doAction(ActionRequest request,ActionResponse response, 
			@RequestParam("bookId")Integer bookId, 
			@RequestParam("jspPage")String page, Model model) {
		Book book = bookService.getBook(bookId);
		request.setAttribute("book", book);
		request.setAttribute("jspPage", page);
		response.setRenderParameter("action", "showEditPage");
	}
	
	@RequestMapping("VIEW")
	@RenderMapping(params = "action=showEditPage")
	public String showEditPage(RenderRequest request,Model model) {
		String jspPage = (String) request.getAttribute("jspPage");
		Book book = (Book) request.getAttribute("book");
		System.out.println(book.toString());	
		System.out.println(jspPage);	
		model.addAttribute("book", book);
		return jspPage;		
	}
	
	@RequestMapping("VIEW")
	@ActionMapping(params = "action=updateBook")
	public void updateBook(@ModelAttribute("book") Book book, ActionRequest request, ActionResponse response) {
		List<String> errors = new ArrayList<String>();
		System.out.println(book.toString());
		if(book != null){
			book.setSummary(book.getSummary().substring(0, book.getSummary().length() -1 ));
		}
		boolean bookValid = BookValidator.validateBook(book, errors);
		if (bookValid) {
			try {
				Book bookDB = bookService.getBook(book.getBookId());
				if(book != null){
					bookDB.setTitle(book.getTitle());
					bookDB.setAuthor(book.getAuthor());
					bookDB.setIsbn(book.getIsbn());
					bookDB.setSummary(book.getSummary());
					bookService.update(bookDB);
	        	}
				response.setRenderParameter("action", "redirectToView");
			} catch (SystemException e) {
				SessionErrors.add(request, "error-while-adding");
				response.setRenderParameter("jspPage", "errorPage");
				return;
			}
			SessionMessages.add(request, "book-updated");
			//return;
		} else {
			for (String error : errors) {
				SessionErrors.add(request, error);
			}
			response.setRenderParameter("action", "redirectUpdatePage");
			request.setAttribute("book", book);
			//return;
		}
	}
	
	@RequestMapping("VIEW")
	@ActionMapping(params = "action=addANewBook")
	public void addBookProcessAction(@ModelAttribute("book") Book book, ActionRequest request, ActionResponse response)
			throws IOException, PortletException, PortalException, SystemException {
		System.out.println(book.toString());
		if(book != null){
			book.setSummary(book.getSummary().substring(0, book.getSummary().length() -1 ));
		}
		List<String> errors = new ArrayList<String>();
		boolean bookValid = BookValidator.validateBook(book, errors);
		if (bookValid) {
			try {
				bookService.addBook(new Book(book.getTitle(),book.getAuthor(), book.getIsbn(), book.getSummary()));
				response.setRenderParameter("action", "redirectToView");
			} catch (SystemException e) {
				response.setRenderParameter("jspPage", "errorPage");
				SessionErrors.add(request, "error-while-adding");
				//return;
			}
			SessionMessages.add(request, "book-added");
			//return;
		} else {
			for (String error : errors) {
				SessionErrors.add(request, error);
			}
			response.setRenderParameter("action", "redirectRegPage");
			request.setAttribute("book", book);
			//return;
		}

    }	
	
	@RequestMapping("VIEW")
	@RenderMapping(params = "action=redirectRegPage")
	public String redirectRegPage(RenderRequest request,Model model) {
		return "book_registration";		
	}
	
	@RequestMapping("VIEW")
	@RenderMapping(params = "action=redirectUpdatePage")
	public String redirectUpdatePage(RenderRequest request,Model model) {
		return "book_edit";		
	}
	
	@RequestMapping("VIEW")
	@RenderMapping(params = "action=redirectToView")
	public String renderJsp(RenderRequest request, RenderResponse response){
		return processRenderRequest(request, response);
	}
	
	@ResourceMapping(value="sendDataURL")
	public void serveResource(ResourceRequest request, ResourceResponse response)
		    throws IOException, PortletException {
    	int bookId = ParamUtil.getInteger(request, "bookId");
		bookService.deleteBook(bookId);
		System.out.println("Successfully Deleted bookId of Id =>" + bookId);
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("success");
		out.flush();
	}
		
	@Activate
    @Modified
    protected void activate(Map<String, Object> properties) {
    	System.out.println("BookConfigPortlet Activated");
        _configuration = Configurable.createConfigurable(BookConfiguration.class, properties);
    }

    private volatile BookConfiguration _configuration;
}