package com.xtivia.book.portlet.asset;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;
import javax.portlet.WindowStateException;
import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.BaseAssetRendererFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.xtivia.book.portlet.model.Book;
import com.xtivia.book.portlet.service.BookLocalService;
import com.xtivia.book.portlet.service.BookLocalServiceUtil;
import com.xtivia.book.portlet.service.permission.BookPermission;

@Component(immediate = true, property = { "javax.portlet.name=BookPortletMVC" }, service = AssetRendererFactory.class)
public class BookAssetRendererFactory extends BaseAssetRendererFactory<Book> {

	public static final String CLASS_NAME = Book.class.getName();

	public static final String TYPE = "book";

	public BookAssetRendererFactory() {
		setClassName(Book.class.getName());
		setCategorizable(true);
		setLinkable(true);
		setPortletId("BookPortletMVC");
		setSearchable(true);
		setSelectable(true);
	}

	@Reference(target = "(bundle.symbolic.name=bookservice-service)", unbind = "-")
	public void setResourceBundleLoader(ResourceBundleLoader resourceBundleLoader) {

		_resourceBundleLoader = resourceBundleLoader;
	}

	private ResourceBundleLoader _resourceBundleLoader;

	@Reference(target = "(osgi.web.symbolicname=bookservice-service)", unbind = "-")
	public void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	private ServletContext _servletContext;

	@Reference(unbind = "-")
	protected void setBlogsEntryLocalService(BookLocalService _bookLocalService) {

		this._bookLocalService = _bookLocalService;
	}

	private BookLocalService _bookLocalService;

	@Override
	public AssetRenderer<Book> getAssetRenderer(long classPK, int type) throws PortalException {

		Book book = _bookLocalService.getBook(classPK);

		BookAssetRenderer bookAssetRenderer = new BookAssetRenderer(book, _resourceBundleLoader);

		bookAssetRenderer.setAssetRendererType(type);
		bookAssetRenderer.setServletContext(_servletContext);
		return bookAssetRenderer;
	}

	@Override
	public String getClassName() {
		return CLASS_NAME;
	}

	@Override
	public String getType() {
		return TYPE;
	}

	@Override
	public boolean hasPermission(PermissionChecker permissionChecker, long classPK, String actionId) throws Exception {

		return BookPermission.contains(permissionChecker, classPK, actionId);
	}

	@Override
	public boolean isLinkable() {
		return _LINKABLE;
	}

	private static final boolean _LINKABLE = true;

	@Override
	public PortletURL getURLView(LiferayPortletResponse liferayPortletResponse, WindowState windowState) {

		LiferayPortletURL liferayPortletURL = liferayPortletResponse.createLiferayPortletURL("BookPortletMVC",
				PortletRequest.RENDER_PHASE);

		try {
			liferayPortletURL.setWindowState(windowState);
		} catch (WindowStateException wse) {
		}

		return liferayPortletURL;
	}
}
