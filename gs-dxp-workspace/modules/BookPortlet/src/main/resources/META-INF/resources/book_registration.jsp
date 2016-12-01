<%@page import="com.xtivia.portlet.book.entity.Book"%>
<%@page import="com.xtivia.portlet.book.dao.impl.BookInMemoryDAOImpl"%>
<%@page import="com.xtivia.portlet.book.dao.BookDAOInterface"%>
<%@ include file="/init.jsp"%>
<%@page import="com.liferay.portal.kernel.util.UnicodeFormatter"%>
<%@page import="com.liferay.portal.kernel.util.PortalUtil"%> 
<%@page import="com.liferay.portal.kernel.util.ListUtil"%>
<%@page import="com.liferay.portal.kernel.util.ParamUtil"%>
<p>Add A Book</p>
 <%
	Integer bookId = ParamUtil.getInteger(request, "bookId");
 	Book book = new Book();
 	 if(bookId != 0){
		BookDAOInterface bookDAO = new BookInMemoryDAOImpl();
		book = bookDAO.getBook(bookId);
 	} 
	request.setAttribute("book", book);

%>
<portlet:actionURL name="addBookProcessAction" var="addBookProcessActionURL">
	<portlet:param name="mvcPath" value="/view.jsp" />
</portlet:actionURL>
<aui:form name="<portlet:namespace />addBookForm" action="${addBookProcessActionURL}" method="post">
	<aui:input type="text" name="title" size="100" />
	<aui:input type="text" name="author" size="100"/>
	<aui:input type="text" name="isbn" size="50"/>
   	 <aui:field-wrapper label="summary">
		<liferay-ui:input-editor name="summary"/>
		<input name="<portlet:namespace/>summary" type="hidden" size="2000"/>
		</aui:field-wrapper>
	<input type="submit" name="<portlet:namespace />addBookBtnSubmit" value="Submit Query"/>
	  
</aui:form>

<portlet:actionURL name="cancelProcessAction" var="cancelProcessActionURL">
	<portlet:param name="mvcPath" value="/view.jsp" />
</portlet:actionURL>
<p>
	<br/><a href="${cancelProcessActionURL}">Cancel</a>
</p>