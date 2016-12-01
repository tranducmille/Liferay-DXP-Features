package com.xtivia.portlet.book.configuration;

import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Modified;

import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.portlet.DefaultConfigurationAction;
import com.liferay.portal.kernel.util.ParamUtil;

import aQute.bnd.annotation.metatype.Configurable;

@Component(
	    configurationPid = "com.xtivia.portlet.book.configuration.BookConfiguration",
	    configurationPolicy = ConfigurationPolicy.OPTIONAL,
	    immediate = true,
	    property = {
	        "javax.portlet.name=com_xtivia_portlet_book_BookPorletPortlet"
	    },
	    service = ConfigurationAction.class
	)
public class ConfigurationActionImpl extends DefaultConfigurationAction  {

    private volatile BookConfiguration bookConfiguration;
	
	@Override
	public void include(PortletConfig portletConfig, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
			System.out.println("include");
		
			request.setAttribute(BookConfiguration.class.getName(),bookConfiguration);
	        super.include(portletConfig, request, response);
	}

	@Override
	public void processAction(PortletConfig portletConfig, ActionRequest actionRequest, ActionResponse actionResponse)
			throws Exception {
		System.out.println("processAction");
		
		
		String title = ParamUtil.getString(actionRequest, "preferences--title--");
		String author = ParamUtil.getString(actionRequest, "preferences--author--");
		String isbn = ParamUtil.getString(actionRequest, "preferences--isbn--");
		String summary = ParamUtil.getString(actionRequest, "preferences--summary--");
        setPreference(actionRequest, "title", title);
        setPreference(actionRequest, "author", author);
        setPreference(actionRequest, "isbn", isbn);
        setPreference(actionRequest, "summary", summary);

        super.processAction(portletConfig, actionRequest, actionResponse);
		
	}

	@Activate
    @Modified
    protected void activate(Map<Object, Object> properties) {
		
		bookConfiguration = Configurable.createConfigurable(BookConfiguration.class, properties);
    }
}
