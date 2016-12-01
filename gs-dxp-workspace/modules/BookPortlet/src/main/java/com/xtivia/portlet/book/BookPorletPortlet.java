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

/**
 * @author created by dtran
 * A book portlet using annotation
 */
@Component(
	configurationPid = "com.xtivia.portlet.book.configuration.BookConfiguration",
    immediate = true,
    property = {
		"com.liferay.portlet.display-category=Book Category",
		"com.liferay.portlet.instanceable=true",
		"com.liferay.portlet.configuration-action-class=com.xtivia.portlet.book.configuration.ConfigurationActionImpl",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.requires-namespaced-parameters=false",
		"com.liferay.portlet.render-weight=2",
		"javax.portlet.display-name=Book Portlet MVC",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.portlet-mode=text/html;view,edit",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.init-param.edit-template=/edit.jsp",
        "javax.portlet.init-param.config-template=/configuration.jsp",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class BookPorletPortlet extends MVCPortlet {
	
	/* (non-Javadoc)
	 * @see com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet#doView(javax.portlet.RenderRequest, javax.portlet.RenderResponse)
	 */
	@Override
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		
		BookDAOInterface bookDAO = new BookInMemoryDAOImpl();		
		PortletSession portletSession = renderRequest.getPortletSession();
		String isbn = (String) portletSession.getAttribute("isbnSession",PortletSession.APPLICATION_SCOPE);
		if(isbn != null){
			renderRequest.setAttribute("books", bookDAO.getBookList(isbn));
		}else{
			renderRequest.setAttribute("books", bookDAO.getBookList());
		}
		renderRequest.setAttribute(BookConfiguration.class.getName(), _configuration);
		
		super.doView(renderRequest, renderResponse);
	}
		
	/**
	 * @param actionRequest
	 * @param actionResponse
	 * @throws IOException
	 * @throws PortletException
	 */
	public void updateMessageAction(ActionRequest actionRequest, ActionResponse actionResponse)
			throws IOException, PortletException {

		String title = actionRequest.getParameter("message");
		displayTitle(title, actionRequest);
		actionResponse.setPortletMode(PortletMode.VIEW);
	}
	
	/**
	 * @param title
	 * @param request
	 * @throws IOException
	 * @throws PortletException
	 */
	private void displayTitle(String title, PortletRequest request) throws IOException, PortletException {
		ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
		Layout layout = themeDisplay.getLayout();
		PortletPreferences portletSetup = PortletPreferencesFactoryUtil.getLayoutPortletSetup(layout,  PortalUtil.getPortletId(request));
		Locale locale = new Locale("en", "US"); 
		portletSetup.setValue("portlet-setup-title-" + LocaleUtil.toLanguageId(locale), title);
		portletSetup.setValue("portlet-setup-use-custom-title", "true");
		portletSetup.store();
	}
	
	/**
	 * @param properties
	 */
	@Activate
    @Modified
    protected void activate(Map<String, Object> properties) {
    	System.out.println("BookConfigPortlet Activated");
        _configuration = Configurable.createConfigurable(BookConfiguration.class, properties);
    }

    private volatile BookConfiguration _configuration;
	
	/**
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws PortletException
	 * @throws PortalException
	 * @throws SystemException
	 */
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
	/**
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws PortletException
	 * @throws PortalException
	 * @throws SystemException
	 */
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

	/**
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws PortletException
	 * @throws PortalException
	 * @throws SystemException
	 */
	public void cancelProcessAction(ActionRequest request, ActionResponse response)
        throws IOException, PortletException, PortalException, SystemException{
    	BookDAOInterface bookDAO = new BookInMemoryDAOImpl();
        request.setAttribute("books", bookDAO.getBookList());
        response.setPortletMode(PortletMode.VIEW);
    }
	
	/* (non-Javadoc)
	 * @see com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet#serveResource(javax.portlet.ResourceRequest, javax.portlet.ResourceResponse)
	 */
	public void serveResource(ResourceRequest request, ResourceResponse response)
		    throws IOException, PortletException {
    	int bookId = ParamUtil.getInteger(request, "bookId");
		BookDAOInterface bookDAO = new BookInMemoryDAOImpl();
		bookDAO.deleteBook(bookId);
		request.setAttribute("books", bookDAO.getBookList());
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.flush();
		super.serveResource(request, response);
	}
}