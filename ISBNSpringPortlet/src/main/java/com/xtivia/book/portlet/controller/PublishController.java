package com.xtivia.book.portlet.controller;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.xml.namespace.QName;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.ActionMapping;

import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;

@Controller("publishController")
@RequestMapping("VIEW")
public class PublishController {

	@RequestMapping
	public String defaultLanding(Model model, RenderRequest rRequest, 
						RenderResponse rResponse) {
		return "view";
	}
	
	@RequestMapping("VIEW")
	@ActionMapping(params = "action=searchByISBN")
	public void doAction(ActionRequest request,ActionResponse response) {
		String isbn = ParamUtil.getString(request, "isbn", StringPool.BLANK);
		javax.xml.namespace.QName qName = new QName("http://liferay.com/events", "isbn", "x");
		response.setRenderParameter("isbn", isbn);
		response.setEvent(qName, isbn);
		System.out.println("Search String" + isbn);
	}
}