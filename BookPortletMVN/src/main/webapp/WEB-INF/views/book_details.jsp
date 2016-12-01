<%@ include file="init.jsp"%>
<%@page import="com.xtivia.book.portlet.entity.Book"%>
<%@page import="com.liferay.portal.kernel.util.PortalUtil"%>
<%@page import="com.liferay.portal.kernel.util.ListUtil"%>
<%@page import="com.liferay.portal.kernel.util.ParamUtil"%>
<p>Book Details</p>
<table class="bookdetail">
	<tr>
		<td class="left">Title</td>
		<td class="right">${book.title}</td>
	</tr>
	<tr>
		<td class="left">Author</td>
		<td class="right">${book.author}</td>
	</tr>
	<tr>
		<td class="left">ISBN</td>
		<td class="right">${book.isbn}</td>
	</tr>
	<tr>
		<td class="left">Summary</td>
		<td class="right">${book.summary}</td>
	</tr>
</table>
<portlet:renderURL var="cancelProcessActionURL">
	<portlet:param name="action" value="returnMainPage" />
	<portlet:param name="jspPage" value="view" />
</portlet:renderURL>
<portlet:actionURL var="editBookProcessActionRUL">
	<portlet:param name="action" value="redirectToEditBook" />
	<portlet:param name="bookId" value="${book.id}" />
	<portlet:param name="jspPage" value="book_edit" />
</portlet:actionURL>
<div style="width: 100px; margin-top: 10px">
	<span style="width: 100px"><a href="${cancelProcessActionURL}">Back</a></span>
	<span style="width: 100px"><a href="${editBookProcessActionRUL}">Edit</a></span>
</div>