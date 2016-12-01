package com.xtivia.portlet.book;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.PortletMode;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletSession;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.xtivia.portlet.book.configuration.BookConfiguration;
import com.xtivia.portlet.book.dao.BookDAOInterface;
import com.xtivia.portlet.book.dao.impl.BookInMemoryDAOImpl;
import com.xtivia.portlet.book.entity.Book;
import com.xtivia.portlet.book.util.SequenceGenerator;

import aQute.bnd.annotation.metatype.Configurable;

@Component(
	configurationPid = "com.xtivia.portlet.book.configuration.BookConfiguration",
    immediate = true,
    property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.instanceable=true",
		"com.liferay.portlet.configuration-action-class=com.xtivia.portlet.book.configuration.ConfigurationActionImpl",
/*		"com.liferay.portlet.private-request-attributes=false",
*/		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.requires-namespaced-parameters=false",
		"com.liferay.portlet.render-weight=2",
		"javax.portlet.display-name=Book Portlet MVC",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.portlet-mode=text/html;view,edit",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.init-param.edit-template=/edit.jsp",
        "javax.portlet.init-param.config-template=/configuration.jsp",
		"com.liferay.portlet.header-portlet-css=/css/main.css",/*
		"com.liferay.portlet.header-portlet-javascript=true",
		"com.liferay.portlet.footer-portlet-javascript=true",*/
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class BookPorletPortlet extends MVCPortlet {
	@Override
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		
		BookDAOInterface bookDAO = new BookInMemoryDAOImpl();		
		PortletSession portletSession = renderRequest.getPortletSession();
		String isbn = (String) portletSession.getAttribute("isbnSession",PortletSession.APPLICATION_SCOPE);
		System.out.println("Search by isbn : " + isbn);
		
		System.out.println(portletSession.getAttribute("isbnSession"));
		System.out.println(portletSession.getAttribute("LIFERAY_SHARED_my-session-attribute-name1"));
		System.out.println(portletSession.getAttribute("LIFERAY_SHARED_my-session-attribute-name",PortletSession.APPLICATION_SCOPE));
		System.out.println(portletSession.getAttribute("LIFERAY_SHARED_my-session-attribute-name1",PortletSession.APPLICATION_SCOPE));
		HttpServletRequest req = PortalUtil.getHttpServletRequest(renderRequest);
		System.out.println(req.getSession(false).getAttribute("isbnSession"));
		System.out.println(req.getSession(true).getAttribute("isbnSession"));
		System.out.println(req.getSession(true).getAttribute("LIFERAY_SHARED_my-session-attribute-name1"));
		System.out.println(req.getSession(false).getAttribute("LIFERAY_SHARED_my-session-attribute-name1"));
		System.out.println(renderRequest.getPortletSession(false).getAttribute("isbn_session",PortletSession.APPLICATION_SCOPE));

		if(isbn != null){
			renderRequest.setAttribute("books", bookDAO.getBookList(isbn));
		}else{
			renderRequest.setAttribute("books", bookDAO.getBookList());
		}
		renderRequest.setAttribute(BookConfiguration.class.getName(), _configuration);
		
		super.doView(renderRequest, renderResponse);
	}
		
	public void updateMessageAction(ActionRequest actionRequest, ActionResponse actionResponse)
			throws IOException, PortletException {

		String title = actionRequest.getParameter("message");
		System.out.println(title);
		displayTitle(title, actionRequest);
		System.out.println("updateMessageAction");
		/*String portletName = (String) actionRequest.getAttribute(WebKeys.PORTLET_ID); 
		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		PortletURL redirectURL = PortletURLFactoryUtil.create(PortalUtil.getHttpServletRequest(actionRequest),
				portletName,themeDisplay.getLayout().getPlid(), PortletRequest.RENDER_PHASE); */
		actionResponse.setPortletMode(PortletMode.VIEW);
	}
	
	private void displayTitle(String title, PortletRequest request) throws IOException, PortletException {
		ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
		Layout layout = themeDisplay.getLayout();
		PortletPreferences portletSetup = PortletPreferencesFactoryUtil.getLayoutPortletSetup(layout,  PortalUtil.getPortletId(request));
		Locale locale = new Locale("en", "US"); 
		portletSetup.setValue("portlet-setup-title-" + LocaleUtil.toLanguageId(locale), title);
		portletSetup.setValue("portlet-setup-use-custom-title", "true");
		portletSetup.store();
	}
	
	@Activate
    @Modified
    protected void activate(Map<String, Object> properties) {
    	System.out.println("BookConfigPortlet Activated");
        _configuration = Configurable.createConfigurable(BookConfiguration.class, properties);
    }

    private volatile BookConfiguration _configuration;
	
	public void addBookProcessAction(ActionRequest request, ActionResponse response)
            throws IOException, PortletException, PortalException, SystemException{
        String title=ParamUtil.get(request, "title", "");
        String author=ParamUtil.get(request, "author", "");
        String isbn=ParamUtil.get(request, "isbn", "");
        String summary=ParamUtil.get(request, "summary", "");
        
        if(title != null && author != null 
        		&& isbn != null && summary != null){
        	BookDAOInterface bookDAO = new BookInMemoryDAOImpl();
        	bookDAO.addBook(new Book(SequenceGenerator.getInstance().getSequence(), title, author, isbn, summary));
            request.setAttribute("books", bookDAO.getBookList());
        }
        response.setPortletMode(PortletMode.VIEW);
    }
	public void updateBookProcessAction(ActionRequest request, ActionResponse response)
            throws IOException, PortletException, PortalException, SystemException{
		String id = ParamUtil.get(request, "id", "");
        String title=ParamUtil.get(request, "title", "");
        String author=ParamUtil.get(request, "author", "");
        String isbn=ParamUtil.get(request, "isbn", "");
        String summary=ParamUtil.get(request, "summary", "");
        
        if(title != null && author != null 
        		&& isbn != null && summary != null){
        	BookDAOInterface bookDAO = new BookInMemoryDAOImpl();
        	Book book = bookDAO.getBook(Integer.parseInt(id));
        	if(book != null){
        		book.setTitle(title);
        		book.setAuthor(author);
        		book.setIsbn(isbn);
        		book.setSummary(summary);
        	}
            request.setAttribute("books", bookDAO.getBookList());
        }
        response.setPortletMode(PortletMode.VIEW);
    }

	public void cancelProcessAction(ActionRequest request, ActionResponse response)
        throws IOException, PortletException, PortalException, SystemException{
    	BookDAOInterface bookDAO = new BookInMemoryDAOImpl();
        request.setAttribute("books", bookDAO.getBookList());
        response.setPortletMode(PortletMode.VIEW);
        System.out.println("cancelProcessAction");
    }
	
	public void serveResource(ResourceRequest request, ResourceResponse response)
		    throws IOException, PortletException {
    	int bookId = ParamUtil.getInteger(request, "bookId");
		BookDAOInterface bookDAO = new BookInMemoryDAOImpl();
		bookDAO.deleteBook(bookId);
		System.out.println("Successfully Deleted bookId of Id =>" + bookId);
		request.setAttribute("books", bookDAO.getBookList());
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("success");
		out.flush();
		super.serveResource(request, response);
	}
}