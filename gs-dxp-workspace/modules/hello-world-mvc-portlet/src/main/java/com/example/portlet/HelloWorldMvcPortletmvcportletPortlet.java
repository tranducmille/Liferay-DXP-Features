package com.example.portlet;

import java.io.IOException;
import java.util.logging.Logger;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.PortletMode;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.ParamUtil;

@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.instanceable=false",
		"javax.portlet.display-name=hello-world-mvc-portlet Portlet",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.portlet-mode=text/html;view,edit",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.init-param.edit-template=/edit.jsp",
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class HelloWorldMvcPortletmvcportletPortlet extends MVCPortlet {
	
	private static Logger _log = Logger.getLogger(HelloWorldMvcPortletmvcportletPortlet.class.getName());
	/*protected String editJSP;
	protected String viewJSP;
		
	@Override
	public void init() throws PortletException {
		editJSP = getInitParameter("edit-template");
		viewJSP = getInitParameter("view-template");
		super.init();
	}*/
	
	@Override
	public void doEdit(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		renderResponse.setContentType("text/html");
		PortletURL addNameURL = renderResponse.createActionURL();
		addNameURL.setParameter("addName", "addName");
		renderRequest.setAttribute("addNameURL", addNameURL.toString());
		//include(editJSP, renderRequest, renderResponse);
		// TODO Auto-generated method stub
		System.out.println("doEdit");
		super.doEdit(renderRequest, renderResponse);
	}
	
	@Override
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		PortletPreferences prefs = renderRequest.getPreferences();
		String username = (String)prefs.getValue("name", "no");
		System.out.println("doview pre+ " + username);
		if("no".equalsIgnoreCase(username)){
			username = "";
		}
		renderRequest.setAttribute("userName", username);
		
		//include(viewJSP, renderRequest, renderResponse);
		// TODO Auto-generated method stub
		super.doView(renderRequest, renderResponse);
	}
	
	@Override
	public void processAction(ActionRequest actionRequest, ActionResponse actionResponse)
			throws IOException, PortletException {
		
		String addName = actionRequest.getParameter("addName");
		if(addName != null){
			PortletPreferences prefs = actionRequest.getPreferences();
			System.out.println("processAction " + actionRequest.getParameter("username"));
			String username = ParamUtil.getString(actionRequest, "username");
			prefs.setValue("name",actionRequest.getParameter("username"));
			//prefs.setValue("name", username);
			System.out.println("paramutil " + username);
			prefs.store();
			actionResponse.setPortletMode(PortletMode.VIEW);
		}
		
		// TODO Auto-generated method stub
		//super.processAction(actionRequest, actionResponse);
	}
	
	/*@Override
	protected void include(String path, RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		System.out.println("PATH " +  path);
		PortletRequestDispatcher dispatcher = getPortletContext().getRequestDispatcher(path);
		if(dispatcher == null){
			_log.log(Level.SEVERE, path + " is not valid include");
		}else{
			dispatcher.include(renderRequest, renderResponse);
		}
		
		// TODO Auto-generated method stub
		super.include(path, renderRequest, renderResponse);
	}*/
}