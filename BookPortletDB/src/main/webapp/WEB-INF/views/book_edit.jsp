<%@ include file="init.jsp"%>
<%@page import="com.xtivia.book.portlet.entity.Book"%>
<%@page import="com.liferay.portal.kernel.util.UnicodeFormatter"%>
<%@page import="com.liferay.portal.kernel.util.PortalUtil"%> 
<%@page import="com.liferay.portal.kernel.util.ListUtil"%>
<%@page import="com.liferay.portal.kernel.util.ParamUtil"%>
<p>Edit Book Details</p>
<portlet:actionURL var="updateBookProcessActionURL">
	<portlet:param name="action" value="updateBook" />
	<portlet:param name="jspPage" value="view" />
</portlet:actionURL>
<liferay-ui:error key="error-while-adding" message="error-while-adding"/>
<liferay-ui:error key="title-required" message="title-required"/>
<liferay-ui:error key="author-required" message="author-required"/>
<liferay-ui:error key="isbn-required" message="isbn-required"/>
<liferay-ui:error key="summary-required" message="summary-required"/>
<form:form name="<portlet:namespace />updateBookForm" action="${updateBookProcessActionURL}" modelAttribute="book" method="post">
	<input name="<portlet:namespace/>bookId" type="hidden" value="${book.bookId}"  size="2000"/> 
	<aui:input type="text" name="title" size="100" value="${book.title}"> </aui:input>
	<aui:input type="text" name="author" size="100" value="${book.author}"/>
	<aui:input type="text" name="isbn" size="50" value="${book.isbn}"/>
	<aui:field-wrapper label="description">
	    <liferay-ui:input-editor name="summary" initMethod="initEditor"/>
	    <script type="text/javascript">
	        function <portlet:namespace />initEditor() { return  "${book.summary}"; }
	        function <portlet:namespace />extractCodeFromEditor() {
                var x = document.<portlet:namespace />updateBookForm.<portlet:namespace />
                summary.value = window.<portlet:namespace />editor.getHTML();
    		}
	    </script>
	</aui:field-wrapper>
    <input name="<portlet:namespace />summary" type="hidden" />
	<input type="submit" name="<portlet:namespace />updateBookBtnSubmit" value="Submit Query"/>
</form:form>

<portlet:renderURL var="cancelProcessActionURL">
	<portlet:param name="action" value="returnMainPage" />
	<portlet:param name="jspPage" value="view" />
</portlet:renderURL>
<p>
	<br/><a href="${cancelProcessActionURL}">Cancel</a>
</p>