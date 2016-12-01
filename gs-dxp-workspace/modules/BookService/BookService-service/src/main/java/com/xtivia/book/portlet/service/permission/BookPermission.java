package com.xtivia.book.portlet.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.xtivia.book.portlet.model.Book;
import com.xtivia.book.portlet.service.BookLocalServiceUtil;

public class BookPermission {
    public static void check(PermissionChecker permissionChecker,
            long bookId, String actionId) throws PortalException,
            SystemException {

        if (!contains(permissionChecker, bookId, actionId)) {
            throw new PrincipalException();
        }
    }

    public static boolean contains(PermissionChecker permissionChecker,
            long bookId, String actionId) throws PortalException,
            SystemException {

        Book book = BookLocalServiceUtil.getBook(bookId);

        return permissionChecker
                .hasPermission(book.getGroupId(),Book.class.getName(), book.getBookId(),
                        actionId);

    }
}