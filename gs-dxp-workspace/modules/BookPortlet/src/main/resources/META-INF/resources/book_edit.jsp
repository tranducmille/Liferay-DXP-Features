<%@page import="com.xtivia.portlet.book.entity.Book"%>
<%@page import="com.xtivia.portlet.book.dao.impl.BookInMemoryDAOImpl"%>
<%@page import="com.xtivia.portlet.book.dao.BookDAOInterface"%>
<%@ include file="/init.jsp"%>
<%@page import="com.liferay.portal.kernel.util.UnicodeFormatter"%>
<%@page import="com.liferay.portal.kernel.util.PortalUtil"%> 
<%@page import="com.liferay.portal.kernel.util.ListUtil"%>
<%@page import="com.liferay.portal.kernel.util.ParamUtil"%>
<p>Edit Book Details</p>
 <%
	Integer bookId = ParamUtil.getInteger(request, "bookId");
 	Book book = new Book();
 	 if(bookId != 0){
		BookDAOInterface bookDAO = new BookInMemoryDAOImpl();
		book = bookDAO.getBook(bookId);
 	} 
	request.setAttribute("book", book);

%>
<portlet:actionURL name="updateBookProcessAction" var="updateBookProcessActionURL">
	<portlet:param name="mvcPath" value="/view.jsp" />
</portlet:actionURL>
<aui:form name="<portlet:namespace />updateBookForm" action="${updateBookProcessActionURL}" method="post">
	<input name="<portlet:namespace/>id" type="hidden" value="${book.id}"  size="2000"/> 
	<aui:input type="text" name="title" size="100" value="${book.title}"> </aui:input>
	  <aui:input type="text" name="author" size="100" value="${book.author}"/>
	 <aui:input type="text" name="isbn" size="50" value="${book.isbn}"/>
	  <aui:field-wrapper label="summary">
			      <liferay-ui:input-editor initMethod="initEditor" name="summary"/>
			      <script type="text/javascript">
			        function <portlet:namespace />initEditor() {
			         	return "<%=UnicodeFormatter.toString(book.getSummary())%>";
			        }
			      </script>
			 </aui:field-wrapper>
			<input name="<portlet:namespace/>summary" type="hidden"  size="2000"/> 
		<input type="submit" name="<portlet:namespace />updateBookBtnSubmit" value="Submit Query"/>
	
</aui:form>

<portlet:actionURL name="cancelProcessAction" var="cancelProcessActionURL">
	<portlet:param name="mvcPath" value="/view.jsp" />
</portlet:actionURL>
<p>
	<br/><a href="${cancelProcessActionURL}">Cancel</a>
</p>