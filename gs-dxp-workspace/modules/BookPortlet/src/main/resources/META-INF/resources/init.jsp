<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ page import="com.xtivia.portlet.book.configuration.BookConfiguration" %>
<%@ page import="com.liferay.portal.kernel.util.GetterUtil" %>
<%@page import="com.liferay.portal.kernel.util.LocaleUtil"%>
<%@page import="java.util.Locale"%>
<%@page import="javax.portlet.PortletPreferences"%>
<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
	PortletPreferences prefs = renderRequest.getPreferences();
	String id = "portlet-setup-title-" + LocaleUtil.toLanguageId(locale);
	renderResponse.setTitle(prefs.getValue(id, "Welcome Message"));
%>