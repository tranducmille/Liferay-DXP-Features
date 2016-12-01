package com.xtivia.book.portlet.controller;

import java.util.Map;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceURL;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Modified;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import com.liferay.portal.kernel.model.User;
import com.liferay.portal.util.WebKeys;
import com.xtivia.book.portlet.configuration.BookConfiguration;
import com.xtivia.book.portlet.service.BookService;

import aQute.bnd.annotation.metatype.Configurable;

/**
 * @author created by dtran
 * A book controller to handle all the request from client
 */
@Controller("bookController")
@RequestMapping("VIEW")
public class BookController{

	@Autowired(required = true)
	BookService bookService;

	/**
	 * set default value for constant
	 * @param request
	 * @param response
	 * @return templatename
	 */
	@RenderMapping
	public String processRenderRequest(RenderRequest request, RenderResponse response) {
		
		User user = (User) request.getAttribute(WebKeys.USER);
		String userScreenName = user != null ? user.getScreenName() : "anonymous";
		ResourceURL baseResourceUrl = response.createResourceURL();

		request.setAttribute("ajaxURL", baseResourceUrl.toString());
		request.setAttribute("standalone", false);
		request.setAttribute("authenticatedUser", userScreenName);
		/*request.setAttribute("portletId", getPortletId(request));*/
		request.setAttribute("portletAppContextPath", request.getContextPath() + "/");
		request.setAttribute(BookConfiguration.class.getName(), _configuration);

		return "view";
	}
	/*
	private String getPortletId(PortletRequest request) {
		ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();
		return portletDisplay.getId();
	}*/

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		System.out.println("BookConfigPortlet Activated");
		_configuration = Configurable.createConfigurable(BookConfiguration.class, properties);
	}

	private volatile BookConfiguration _configuration;
}