package com.xtivia.book.portlet.controller;

import java.io.IOException;
import java.util.Locale;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletMode;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

@Controller("myAppEditController")
@RequestMapping("EDIT")
public class MyAppEditController {

    @RenderMapping
    public String processRenderRequest(RenderRequest request,
            RenderResponse response) {
        return "edit";
    }

    @ActionMapping(params="action=updateMsgPrefAction")
    public void updateMsgPrefAction(ActionRequest request, ActionResponse response) throws IOException, PortletException{

    	String title = request.getParameter("message");
		displayTitle(title, request);
		System.out.println("updateMessageAction");
		response.setPortletMode(PortletMode.VIEW);
		SessionMessages.add(request, "columns-update-preference");
    }
    
	
	private void displayTitle(String title, PortletRequest request) throws IOException, PortletException {
		ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
		Layout layout = themeDisplay.getLayout();
		PortletPreferences portletSetup = PortletPreferencesFactoryUtil.getLayoutPortletSetup(layout,  PortalUtil.getPortletId(request));
		Locale locale = new Locale("en", "US"); 
		System.out.println("LOCALE " + LocaleUtil.toLanguageId(locale));
		portletSetup.setValue("portlet-setup-title", title);
		portletSetup.store();		
	}
}