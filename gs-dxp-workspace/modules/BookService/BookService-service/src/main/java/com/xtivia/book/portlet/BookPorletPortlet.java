package com.xtivia.book.portlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
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

import com.liferay.portal.kernel.audit.AuditException;
import com.liferay.portal.kernel.audit.AuditMessage;
import com.liferay.portal.kernel.audit.AuditRouterUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.xtivia.book.portlet.audit.AttributesBuilder;
import com.xtivia.book.portlet.audit.AuditMessageBuilder;
import com.xtivia.book.portlet.configuration.BookConfiguration;
import com.xtivia.book.portlet.model.Book;
import com.xtivia.book.portlet.model.Entry;
import com.xtivia.book.portlet.model.impl.BookImpl;
import com.xtivia.book.portlet.service.BookLocalServiceUtil;
import com.xtivia.book.portlet.service.EntryLocalServiceUtil;

import aQute.bnd.annotation.metatype.Configurable;

/**
 * @author created by dtran
 * A book controller to handle all the request from client
 */
@Component(configurationPid = "com.xtivia.book.portlet.configuration.BookConfiguration", 
		immediate = true, 
		property = {
		"com.liferay.portlet.display-category=Book Category", 
		"com.liferay.portlet.instanceable=false",
		"com.liferay.portlet.configuration-action-class=com.xtivia.book.portlet.configuration.ConfigurationActionImpl",
		"com.liferay.portlet.asset-renderer-factory=com.xtivia.book.portlet.asset.BookAssetRendererFactory",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.requires-namespaced-parameters=false",
		"com.liferay.portlet.render-weight=2",
		"javax.portlet.display-name=Book Portlet MVC",
		"javax.portlet.name=BookPortletMVC",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.portlet-mode=text/html;view,edit", 
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.init-param.edit-template=/edit.jsp",
		"javax.portlet.init-param.config-template=/configuration.jsp",
		"com.liferay.portlet.header-portlet-css=/css/main.css", 
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user" }, service = Portlet.class)
public class BookPorletPortlet extends MVCPortlet {

	/* (non-Javadoc)
	 * @see com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet#doView(javax.portlet.RenderRequest, javax.portlet.RenderResponse)
	 */
	@Override
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {

		PortletSession portletSession = renderRequest.getPortletSession();
		String isbn = (String) portletSession.getAttribute("isbnSession", PortletSession.APPLICATION_SCOPE);
		ServiceContext serviceContext = null;
		try {
			serviceContext = ServiceContextFactory.getInstance(Book.class.getName(), renderRequest);
			long groupId = serviceContext.getScopeGroupId();
			List<Book> books = BookLocalServiceUtil.getBooks(groupId, WorkflowConstants.STATUS_APPROVED);
			if (isbn != null) {
				renderRequest.setAttribute("books", BookLocalServiceUtil.findBookByISBN(isbn));
			} else {
				renderRequest.setAttribute("books", books);
			}
		} catch (PortalException e) {
			e.printStackTrace();
		}
		renderRequest.setAttribute(BookConfiguration.class.getName(), _configuration);
		super.doView(renderRequest, renderResponse);
	}

	/**
	 * update message on the preference with EDIT mode
	 * @param actionRequest
	 * @param actionResponse
	 * @throws IOException
	 * @throws PortletException
	 */
	public void updateMessageAction(ActionRequest actionRequest, ActionResponse actionResponse)
			throws IOException, PortletException {

		String title = actionRequest.getParameter("message");
		System.out.println(title);
		displayTitle(title, actionRequest);
		System.out.println("updateMessageAction");
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
		PortletPreferences portletSetup = PortletPreferencesFactoryUtil.getLayoutPortletSetup(layout,
				PortalUtil.getPortletId(request));
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
	 * Add book
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws PortletException
	 * @throws PortalException
	 * @throws SystemException
	 */
	public void addBookProcessAction(ActionRequest request, ActionResponse response)
			throws IOException, PortletException, PortalException, SystemException {
		String title = ParamUtil.get(request, "title", "");
		String author = ParamUtil.get(request, "author", "");
		String isbn = ParamUtil.get(request, "isbn", "");
		String summary = ParamUtil.get(request, "summary", "");
		ServiceContext serviceContext = ServiceContextFactory.getInstance(Book.class.getName(), request);
		if (title != null && author != null && isbn != null && summary != null) {
			Book book = new BookImpl();
			book.setAuthor(author);
			book.setTitle(title);
			book.setIsbn(isbn);
			book.setSummary(summary);
			try {
				BookLocalServiceUtil.createBook(book, serviceContext.getUserId(), serviceContext);
				SessionMessages.add(request, "book-added-msg");
				long groupId = serviceContext.getScopeGroupId();
				request.setAttribute("books", BookLocalServiceUtil.getBooks(groupId, WorkflowConstants.STATUS_APPROVED));
			} catch (Exception e) {
				SessionErrors.add(request, e.getClass().getName());
				response.setRenderParameter("mvcPath", "/book_registration.jsp");
			}
		}
		response.setPortletMode(PortletMode.VIEW);
	}

	/**
	 * @param request
	 * @param response
	 */
	public void deleteEntry(ActionRequest request, ActionResponse response) {

		long entryId = ParamUtil.getLong(request, "entryId");
		long bookId = ParamUtil.getLong(request, "bookId");

		try {
			ServiceContext serviceContext = ServiceContextFactory.getInstance(Entry.class.getName(), request);
			response.setRenderParameter("bookId", Long.toString(bookId));
			EntryLocalServiceUtil.deleteEntry(entryId, serviceContext);

		} catch (Exception e) {
			SessionErrors.add(request, e.getClass().getName());
		}
	}

	/**
	 * @param request
	 * @param response
	 * @throws PortalException
	 * @throws SystemException
	 */
	public void addEntry(ActionRequest request, ActionResponse response) throws PortalException, SystemException {

		ServiceContext serviceContext = ServiceContextFactory.getInstance(Entry.class.getName(), request);
		String email = ParamUtil.getString(request, "email");
		String userName = ParamUtil.getString(request, "name");
		String message = ParamUtil.getString(request, "message");
		long bookId = ParamUtil.getLong(request, "bookId");

		try {
			EntryLocalServiceUtil.addEntry(serviceContext.getUserId(), bookId, userName, email, message,
					serviceContext);
			SessionMessages.add(request, "entry-added-msg");
			response.setRenderParameter("bookId", Long.toString(bookId));

		} catch (Exception e) {
			SessionErrors.add(request, e.getClass().getName());
			PortalUtil.copyRequestParameters(request, response);
			response.setRenderParameter("mvcPath", "/book_registration.jsp");
		}

	}

	/**
	 * update a book
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws PortletException
	 * @throws PortalException
	 * @throws SystemException
	 */
	public void updateBookProcessAction(ActionRequest request, ActionResponse response)
			throws IOException, PortletException, PortalException, SystemException {
		String id = ParamUtil.get(request, "bookId", "");
		String title = ParamUtil.get(request, "title", "");
		String author = ParamUtil.get(request, "author", "");
		String isbn = ParamUtil.get(request, "isbn", "");
		String summary = ParamUtil.get(request, "summary", "");

		if (title != null && author != null && isbn != null && summary != null) {
			ServiceContext serviceContext = ServiceContextFactory.getInstance(Book.class.getName(), request);
			long bookId = ParamUtil.getLong(request, "bookId");

			Book book = BookLocalServiceUtil.getBook(Long.parseLong(id));
			Book oldBook = (Book) book.clone();
			if (book != null) {
				book.setTitle(title);
				book.setAuthor(author);
				book.setIsbn(isbn);
				book.setSummary(summary);
				BookLocalServiceUtil.updateBook(serviceContext.getUserId(),bookId, book, serviceContext);
			}
			AttributesBuilder attributesBuilder = new AttributesBuilder(book, oldBook);
			attributesBuilder.add("title");
			attributesBuilder.add("author");
			attributesBuilder.add("isbn");
			attributesBuilder.add("summary");
			AuditMessage auditMessage = AuditMessageBuilder.buildAuditMessage("com.xtivia.book.portlet.domain.updated",
					book.getClass().getName(), book.getBookId(), attributesBuilder.getAttributes());
			try {
				AuditRouterUtil.route(auditMessage);
			} catch (AuditException e) {
				e.printStackTrace();
			}
			long groupId = serviceContext.getScopeGroupId();
			request.setAttribute("books", BookLocalServiceUtil.getBooks(groupId, WorkflowConstants.STATUS_APPROVED));
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
			throws IOException, PortletException, PortalException, SystemException {
		ServiceContext serviceContext = ServiceContextFactory.getInstance(Book.class.getName(), request);
		long groupId = serviceContext.getScopeGroupId();
		request.setAttribute("books", BookLocalServiceUtil.getBooks(groupId, WorkflowConstants.STATUS_APPROVED));
		response.setPortletMode(PortletMode.VIEW);
		System.out.println("cancelProcessAction");
	}

	/* (non-Javadoc)
	 * @see com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet#serveResource(javax.portlet.ResourceRequest, javax.portlet.ResourceResponse)
	 */
	public void serveResource(ResourceRequest request, ResourceResponse response) throws IOException, PortletException {
		long bookId = ParamUtil.getLong(request, "bookId");
		ServiceContext serviceContext = null;
		try {
			serviceContext = ServiceContextFactory.getInstance(Book.class.getName(), request);
			BookLocalServiceUtil.deleteBook(bookId, serviceContext);
			SessionMessages.add(request, "guestbookDeleted");
		} catch (PortalException e1) {
			SessionErrors.add(request, e1.getClass().getName());
		}

		System.out.println("Successfully Deleted bookId of Id =>" + bookId);
		long groupId = serviceContext.getScopeGroupId();
		request.setAttribute("books", BookLocalServiceUtil.getBooks(groupId, WorkflowConstants.STATUS_APPROVED));
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("success");
		out.flush();
		super.serveResource(request, response);
	}
}