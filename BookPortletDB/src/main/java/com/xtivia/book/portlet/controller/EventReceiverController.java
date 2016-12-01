package com.xtivia.book.portlet.controller;

import java.io.IOException;
import java.util.List;

import javax.portlet.EventPortlet;
import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.EventMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import com.liferay.portal.kernel.util.ParamUtil;
import com.xtivia.book.portlet.entity.Book;
import com.xtivia.book.portlet.service.BookService;
/**
 * @author created by dtran
 * A event controller to handle all the request from client
 */
@Controller
@RequestMapping("VIEW")
public class EventReceiverController implements EventPortlet{
	
	@Autowired(required = true)
	BookService bookService;
	
	/* (non-Javadoc)
	 * @see javax.portlet.EventPortlet#processEvent(javax.portlet.EventRequest, javax.portlet.EventResponse)
	 */
	@EventMapping
	public void processEvent(EventRequest request, EventResponse response) throws PortletException, IOException {
		javax.portlet.Event event = request.getEvent();
        String isbn = (String) event.getValue();
        System.out.println("Getting para " + isbn);
        request.setAttribute("isbn", isbn);
        response.setRenderParameter("isbn", isbn);
		response.setRenderParameter("action", "showViewPage");
	}
	
	/**
	 * @param request
	 * @param model
	 * @return template name
	 */
	@RenderMapping(params = "action=showViewPage")
	public String showEditPage(RenderRequest request,Model model) {
		String isbn = ParamUtil.getString(request, "isbn");
		List<Book> books = bookService.getBookList(isbn);
		model.addAttribute("books", books);
		return "view";		
	} 
}