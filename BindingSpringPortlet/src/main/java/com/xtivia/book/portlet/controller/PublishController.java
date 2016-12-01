package com.xtivia.book.portlet.controller;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("publishController")
@RequestMapping("VIEW")
public class PublishController {

	@RequestMapping
	public String defaultLanding(Model model, RenderRequest rRequest, 
						RenderResponse rResponse) {
		return "view";
	}
}