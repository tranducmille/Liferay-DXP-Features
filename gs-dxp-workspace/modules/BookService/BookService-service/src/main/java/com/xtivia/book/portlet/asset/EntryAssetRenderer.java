package com.xtivia.book.portlet.asset;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.servlet.http.HttpServletRequest;

import com.liferay.asset.kernel.model.BaseJSPAssetRenderer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.trash.TrashRenderer;
import com.xtivia.book.portlet.model.Book;
import com.xtivia.book.portlet.model.Entry;
import com.xtivia.book.portlet.service.BookLocalServiceUtil;
import com.xtivia.book.portlet.service.EntryLocalServiceUtil;

public class EntryAssetRenderer extends BaseJSPAssetRenderer<Entry> 
								implements TrashRenderer {

	private Log _log;
	private Entry _entry;
	 
	public EntryAssetRenderer(Entry entry) {
		_entry = entry;
	}

	@Override
	public boolean hasEditPermission(PermissionChecker permissionChecker) {
		long entryId = _entry.getEntryId();

		boolean contains = false;

		try {
			contains = contains(permissionChecker, entryId, ActionKeys.UPDATE);
		} catch (PortalException pe) {
			_log.error(pe.getLocalizedMessage());
		} catch (SystemException se) {
			_log.error(se.getLocalizedMessage());
		}

		return contains;
	}

	 public static boolean contains(PermissionChecker permissionChecker,
	            long entryId, String actionId) throws PortalException,
	            SystemException {

        Entry entry = EntryLocalServiceUtil.getEntry(entryId);
        return permissionChecker
                .hasPermission(entry.getGroupId(), Entry.class.getName(), entry.getEntryId(),actionId);
    }
	 
	@Override
	public boolean hasViewPermission(PermissionChecker permissionChecker) {

		long entryId = _entry.getEntryId();
		boolean contains = false;

		try {
			contains = contains(permissionChecker, entryId, ActionKeys.VIEW);
		} catch (PortalException pe) {
			_log.error(pe.getLocalizedMessage());
		} catch (SystemException se) {
			_log.error(se.getLocalizedMessage());
		}

		return contains;
	}
	
	@Override
	public String getClassName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getClassPK() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getSummary(PortletRequest portletRequest, PortletResponse portletResponse) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTitle(Locale locale) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Entry getAssetObject() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getGroupId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getUserId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getUserName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUuid() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPortletId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getJspPath(HttpServletRequest request, String template) {
		// TODO Auto-generated method stub
		return null;
	}

}
