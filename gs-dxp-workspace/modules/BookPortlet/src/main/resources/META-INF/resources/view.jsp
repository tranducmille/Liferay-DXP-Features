<%@page import="com.liferay.portal.kernel.util.WebKeys"%>
<%@page import="javax.portlet.PortletPreferences"%>
<%@page import="com.xtivia.portlet.book.entity.Book"%>
<%@ include file="/init.jsp"%>
<%@page import="java.util.List"%>
<%@page import="com.liferay.portal.kernel.util.PortalUtil"%> 
<%@page import="com.liferay.portal.kernel.util.ListUtil"%>
<p>
	<%
		List<Book> books = (List<Book>) renderRequest.getAttribute("books");
		String title = (String) prefs.getValue("title", "false");
		String author = (String) prefs.getValue("author", "false");
		String isbn = (String) prefs.getValue("isbn", "false");
		String summary = (String) prefs.getValue("summary", "false");
	%>
	<liferay-ui:search-container var="searchContainer"
		emptyResultsMessage="There are no books!" delta="10">
		<liferay-ui:search-container-results results="<%=ListUtil.subList(books, searchContainer.getStart(), searchContainer.getEnd())%>" />
		<liferay-ui:search-container-row
			className="com.xtivia.portlet.book.entity.Book" keyProperty="id" modelVar="book">
			
			<portlet:renderURL var="rowURL">
				<portlet:param name="bookId" value="${book.id}" />
				<portlet:param name="mvcPath" value="/book_details.jsp" />
			</portlet:renderURL>
			<c:if test='<%=title.equalsIgnoreCase("true") %>'>
				<liferay-ui:search-container-column-text name="Title" property="title" href="${rowURL}"/>
			</c:if>
			<c:if test='<%=author.equalsIgnoreCase("true") %>'>
				<liferay-ui:search-container-column-text name="Author" property="author" />
			</c:if>
			<c:if test='<%=isbn.equalsIgnoreCase("true") %>'>
				<liferay-ui:search-container-column-text name="ISBN" property="isbn" />
			</c:if>
			<c:if test='<%=summary.equalsIgnoreCase("true") %>'>
				<liferay-ui:search-container-column-text name="Summary" property="summary" />
			</c:if>
			<%-- <portlet:actionURL var="deleteBook" name="deleteBook"> 
				<portlet:param name="bookId" value="${book.id}" />
			</portlet:actionURL>  --%>
			<liferay-ui:search-container-column-text name="Delete Book" value="Delete" href="javascript:deleteBook(${book.id})"/>
		</liferay-ui:search-container-row>
		<liferay-ui:search-iterator />
	</liferay-ui:search-container>
	
	<portlet:renderURL var="addBookURL">
		<portlet:param name="mvcPath" value="/book_registration.jsp" />
	</portlet:renderURL>
	<a href="${addBookURL}"> Add Book </a>
</p>
<!-- Defining the resourceURL -->
<portlet:resourceURL var="sendData" />
<script type="text/javascript">
	function deleteBook(bookId){
		AUI().use('aui-io-request', function(A) {
			A.io.request('<%=sendData%>',{
				method : 'post',
				dataType: 'json',							
				data : {
					bookId: bookId
				},
				on: {
					success: function() {
						var portletId = "#p_p_id_<%=renderRequest.getAttribute(WebKeys.PORTLET_ID)%>_";
						Liferay.Portlet.refresh(portletId);
					}
				}
			});
		});
	}
</script>