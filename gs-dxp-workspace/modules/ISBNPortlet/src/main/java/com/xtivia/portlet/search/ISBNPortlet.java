package com.xtivia.portlet.search;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletRequest;
import javax.portlet.PortletSession;
import javax.portlet.ProcessAction;
import javax.portlet.RenderRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.osgi.service.component.annotations.Component;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringPool;

@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.instanceable=true",		
/*		"com.liferay.portlet.private-request-attributes=false",
*/		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.requires-namespaced-parameters=false",
		"com.liferay.portlet.render-weight=3",
		"javax.portlet.display-name=ISBNPortlet Portlet",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class ISBNPortlet extends MVCPortlet {
	@ProcessAction(name = "isbnActionHanlder")
	public void isbnActionHanlder(ActionRequest request, ActionResponse response) {
		String isbn = ParamUtil.getString(request, "isbn", StringPool.BLANK);
		System.out.println("IPC ISBN "+ isbn);
		request.getPortletSession().setAttribute("isbnSession", isbn ,PortletSession.APPLICATION_SCOPE);
		
		HttpServletRequest req = PortalUtil.getHttpServletRequest(request);
		HttpSession session = req.getSession(false);
		session.setAttribute("LIFERAY_SHARED_my-session-attribute-name", isbn);
		
		request.getPortletSession(false).setAttribute("LIFERAY_SHARED_my-session-attribute-name1", isbn, PortletSession.APPLICATION_SCOPE);


	}
}