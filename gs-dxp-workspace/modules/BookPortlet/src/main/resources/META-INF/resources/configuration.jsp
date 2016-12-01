<%@page import="java.util.Map"%>
<%@ include file="/init.jsp"%>
<%@ page import="com.xtivia.portlet.book.configuration.BookConfiguration" %>
<%@ page import="com.liferay.portal.kernel.util.StringPool" %>
<%@ page import="com.liferay.portal.kernel.util.Validator" %>

<liferay-portlet:actionURL portletConfiguration="true" var="configurationURL">
</liferay-portlet:actionURL>
<liferay-portlet:renderURL portletConfiguration="true" var="configurationRenderURL" />

<%
	String title = portletPreferences.getValue("title", "false");
	String author = portletPreferences.getValue("author", "false");
	String isbn = portletPreferences.getValue("isbn", "false");
	String summary = portletPreferences.getValue("summary", "false");
%>
<div class="main-content-card" style="padding-left:100px">
	<div class="panel-group" role="tablist" aria-multiselectable="true"> 
		<h2>Display Options</h2>
		<aui:form action="${configurationURL}" method="post" name="fm">
			<aui:input name="cmd" type="hidden" value="update" />
			<aui:input name="redirect" type="hidden"  value="${configurationRenderURL}" />
		    <aui:input name="preferences--title--" checked="<%=Boolean.parseBoolean(title)%>" type="checkbox" />
			<aui:input name="preferences--author--" checked="<%=Boolean.parseBoolean(author)%>" type="checkbox" />
			<aui:input name="preferences--isbn--" checked="<%=Boolean.parseBoolean(isbn)%>" type="checkbox" />
			<aui:input name="preferences--summary--" checked="<%=Boolean.parseBoolean(summary)%>" type="checkbox" />
			<aui:button type="submit" value="Save"></aui:button>
		</aui:form>
	</div>
</div>

