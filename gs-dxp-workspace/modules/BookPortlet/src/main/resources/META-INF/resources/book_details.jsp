<%@page import="com.xtivia.portlet.book.entity.Book"%>
<%@page import="com.xtivia.portlet.book.dao.impl.BookInMemoryDAOImpl"%>
<%@page import="com.xtivia.portlet.book.dao.BookDAOInterface"%>
<%@ include file="/init.jsp"%>
<%@page import="com.liferay.portal.kernel.util.PortalUtil"%>
<%@page import="com.liferay.portal.kernel.util.ListUtil"%>
<%@page import="com.liferay.portal.kernel.util.ParamUtil"%>
<p>Book Details</p>
<%
	int bookdId = ParamUtil.getInteger(request, "bookId");
	BookDAOInterface bookDAO = new BookInMemoryDAOImpl();
	Book book = bookDAO.getBook(bookdId);
	request.setAttribute("book", book);
%>
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
<portlet:actionURL name="cancelProcessAction"
	var="cancelProcessActionURL">
	<portlet:param name="mvcPath" value="/view.jsp" />
</portlet:actionURL>
<portlet:renderURL var="editBookProcessActionRUL">
	<portlet:param name="bookId" value="${book.id}" />
	<portlet:param name="mvcPath" value="/book_edit.jsp" />
</portlet:renderURL>
<div style="width: 100px; margin-top: 10px">
	<span style="width: 100px"><a href="${cancelProcessActionURL}">Back</a></span>
	<span style="width: 100px"><a href="${editBookProcessActionRUL}">Edit</a></span>
</div>