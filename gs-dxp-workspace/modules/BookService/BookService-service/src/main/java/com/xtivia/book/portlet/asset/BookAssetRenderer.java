package com.xtivia.book.portlet.asset;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.BaseJSPAssetRenderer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.trash.TrashRenderer;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.xtivia.book.portlet.model.Book;
import com.xtivia.book.portlet.service.permission.BookPermission;
/**
 * @author created by dtran
 * A BookAssetRenderer implentation for rendering asset
 */
public class BookAssetRenderer extends BaseJSPAssetRenderer<Book> 
				implements TrashRenderer {

	private Book _book;
	ResourceBundleLoader _resourceBundleLoader;

	/**
	 * @param book
	 * @param resourceBundleLoader
	 */
	public BookAssetRenderer(Book book, ResourceBundleLoader resourceBundleLoader) {

		_book = book;
		_resourceBundleLoader = resourceBundleLoader;
	}

	/* (non-Javadoc)
	 * @see com.liferay.asset.kernel.model.BaseAssetRenderer#hasEditPermission(com.liferay.portal.kernel.security.permission.PermissionChecker)
	 */
	@Override
	public boolean hasEditPermission(PermissionChecker permissionChecker) {

		long bookId = _book.getBookId();
		boolean contains = false;

		try {
			contains = BookPermission.contains(permissionChecker, bookId, ActionKeys.UPDATE);
		} catch (PortalException pe) {
			_log.error(pe.getLocalizedMessage());
		} catch (SystemException se) {
			_log.error(se.getLocalizedMessage());
		}

		return contains;
	}

	/* (non-Javadoc)
	 * @see com.liferay.asset.kernel.model.BaseAssetRenderer#hasViewPermission(com.liferay.portal.kernel.security.permission.PermissionChecker)
	 */
	@Override
	public boolean hasViewPermission(PermissionChecker permissionChecker) {

		long bookId = _book.getBookId();
		boolean contains = false;
		try {
			contains = BookPermission.contains(permissionChecker, bookId, ActionKeys.VIEW);
		} catch (PortalException pe) {
			_log.error(pe.getLocalizedMessage());
		} catch (SystemException se) {
			_log.error(se.getLocalizedMessage());
		}

		return contains;
	}
	
	private Log _log;

	@Override
	public Book getAssetObject() {
	    return _book;
	}

	@Override
	public String getClassName() {
	    return Book.class.getName();
	}

	@Override
	public long getClassPK() {
	    return _book.getBookId();
	}

	@Override
	public long getGroupId() {
	    return _book.getGroupId();
	}

	@Override
	public String getType() {
	    return BookAssetRendererFactory.TYPE;
	}

	@Override
	public String getUuid() {
	    return _book.getUuid();
	}

	@Override
	public String getSummary(PortletRequest portletRequest, PortletResponse portletResponse) {
		return _book.getSummary();
	}

	@Override
	public String getTitle(Locale locale) {
		// TODO Auto-generated method stub
		return _book.getTitle();
	}

	@Override
	public long getUserId() {
		// TODO Auto-generated method stub
		return _book.getUserId();
	}
	
	@Override
	public int getStatus() {
	    return _book.getStatus();
	}

	@Override
	public String getUserName() {
		// TODO Auto-generated method stub
		return _book.getTitle();
	}

	@Override
	public String getPortletId() {
		 AssetRendererFactory<Book> assetRendererFactory =  getAssetRendererFactory();
		 return assetRendererFactory.getPortletId();
	}

	@Override
	public String getJspPath(HttpServletRequest request, String template) {
		/*if (template.equals(TEMPLATE_ABSTRACT) || template.equals(TEMPLATE_FULL_CONTENT)) {
			return "/blogs/asset/" + template + ".jsp";
		} else {
			return null;
		}*/
		return null;
	}
	/* (non-Javadoc)
	 * @see com.liferay.asset.kernel.model.BaseJSPAssetRenderer#include(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.String)
	 */
	@Override
	public boolean include(
	        HttpServletRequest request, HttpServletResponse response,
	        String template)
	    throws Exception {

	    request.setAttribute(com.xtivia.book.portlet.util.WebKeys.BOOK, _book);

	    return super.include(request, response, template);
	}
	
	/* (non-Javadoc)
	 * @see com.liferay.asset.kernel.model.BaseAssetRenderer#getURLEdit(com.liferay.portal.kernel.portlet.LiferayPortletRequest, com.liferay.portal.kernel.portlet.LiferayPortletResponse)
	 */
	@Override
	public PortletURL getURLEdit(LiferayPortletRequest liferayPortletRequest,
	        								LiferayPortletResponse liferayPortletResponse)throws Exception {

	    Group group = GroupLocalServiceUtil.fetchGroup(_book.getGroupId());

	    PortletURL portletURL = PortalUtil.getControlPanelPortletURL(
	        liferayPortletRequest, group, "BookPortletMVC", 0, 0,
	        PortletRequest.RENDER_PHASE);

	    portletURL.setParameter("mvcRenderCommandName", "/blogs/edit_entry");
	    portletURL.setParameter("entryId", String.valueOf(_book.getEntryId()));

	    return portletURL;
	}

}