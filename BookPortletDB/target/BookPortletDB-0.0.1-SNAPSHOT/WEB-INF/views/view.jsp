<%@page import="com.liferay.portal.kernel.util.Validator"%>
<%@ include file="init.jsp"%>
<%@page import="java.util.List"%>
<%@page import="com.liferay.portal.kernel.util.WebKeys"%>
<%@page import="javax.portlet.PortletPreferences"%>
<%@page import="com.xtivia.book.portlet.entity.Book"%>
<%@page import="com.liferay.portal.kernel.util.PortalUtil"%> 
<%@page import="com.liferay.portal.kernel.util.ListUtil"%>
<%@page import="com.xtivia.book.portlet.util.BookValidator" %>
<%@page import="com.xtivia.book.portlet.configuration.BookConfiguration" %>
<p>
	<%
	    BookConfiguration bookConfig = (BookConfiguration)renderRequest.getAttribute(BookConfiguration.class.getName());
		List<Book> books = (List<Book>) renderRequest.getAttribute("books");
		String title = (String) prefs.getValue("title", "false");
		String author = (String) prefs.getValue("author", "false");
		String isbn = (String) prefs.getValue("isbn", "false");
		String summary = (String) prefs.getValue("summary", "false") ;
		
		if(title.equalsIgnoreCase("false") && author.equalsIgnoreCase("false") 
				&& isbn.equalsIgnoreCase("false") && summary.equalsIgnoreCase("false")){
			if(Validator.isNotNull(bookConfig)){
				title = String.valueOf(bookConfig.title());
				author = String.valueOf(bookConfig.author());
			}else{
				title = "true";
				author = "true";
				prefs.setValue("title", title);
				prefs.setValue("author", author);
			}
		}
	%>
	<liferay-ui:success key="columns-update-preference" message="columns-update-preference"/>
	<liferay-ui:success key="book-updated" message="book-updated"/>
	<liferay-ui:success key="book-added" message="book-added"/>
	<liferay-ui:error key="error-while-adding" message="error-while-adding"/>
	<liferay-ui:search-container var="searchContainer" emptyResultsMessage="emptyBooks" delta="10">
		<liferay-ui:search-container-results results="<%=ListUtil.subList(books, searchContainer.getStart(), searchContainer.getEnd())%>" />
		<liferay-ui:search-container-row className="com.xtivia.book.portlet.entity.Book" keyProperty="id" modelVar="book">
			
			<portlet:renderURL var="bookDetailsURL">
				<portlet:param name="bookId" value="${book.bookId}" />
				<portlet:param name="action" value="displayBookDetails" />
				<portlet:param name="jspPage" value="book_details"/>
			</portlet:renderURL>
			<c:if test='<%=title.equalsIgnoreCase("true") %>'>
				<liferay-ui:search-container-column-text name="Title" property="title" href="${bookDetailsURL}"/>
			</c:if>
			<c:if test='<%=author.equalsIgnoreCase("true") %>'>
				<liferay-ui:search-container-column-text name="Author" property="author" />
			</c:if>
			<c:if test='<%=isbn.equalsIgnoreCase("true") %>'>
				<liferay-ui:search-container-column-text name="ISBN" property="isbn" />
			</c:if>
			<c:if test='<%=summary.equalsIgnoreCase("true") %>'>
				<liferay-ui:search-container-column-text name="Summary" value="<%=BookValidator.extractWords(book.getSummary(), 5) %>" />
				
			</c:if>
			<liferay-ui:search-container-column-text name="Delete Book" value="Delete" href="javascript:deleteBookURL(${book.bookId})"/>
		</liferay-ui:search-container-row>
		<liferay-ui:search-iterator />
	</liferay-ui:search-container>
	
	<portlet:renderURL var="addBookURL">
		<portlet:param name="action" value="redirectToBookReg" />
		<portlet:param name="jspPage" value="book_registration" />
	</portlet:renderURL>
	<a href="${addBookURL}"> Add Book </a>
</p>
<!-- Defining the resourceURL -->
<portlet:resourceURL var="sendDataURL" id="sendDataURL"/>
<script type="text/javascript">
	function deleteBookURL(bookId){
		AUI().use('aui-io-request', function(A) {
			A.io.request('<%=sendDataURL%>',{
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