<%@ include file="init.jsp"%>
<%@page import="com.liferay.portal.kernel.util.UnicodeFormatter"%>
<%@page import="com.liferay.portal.kernel.util.PortalUtil"%> 
<%@page import="com.liferay.portal.kernel.util.ListUtil"%>
<%@page import="com.liferay.portal.kernel.util.ParamUtil"%>
<p>Register A Book</p>
<portlet:actionURL var="addBookProcessActionURL">
	<portlet:param name="action" value="addANewBook" />
	<portlet:param name="jspPage" value="view" />
</portlet:actionURL>
<liferay-ui:error key="error-while-adding" message="error-while-adding"/>
<liferay-ui:error key="title-required" message="title-required"/>
<liferay-ui:error key="author-required" message="author-required"/>
<liferay-ui:error key="isbn-required" message="isbn-required"/>
<liferay-ui:error key="summary-required" message="summary-required"/>
<form:form name="addBookForm" action="${addBookProcessActionURL}" modelAttribute="book" method="post">
	<aui:input type="text" name="title" size="100" />
	<aui:input type="text" name="author" size="100"/>
	<aui:input type="text" name="isbn" size="50"/>
   	<aui:field-wrapper label="summary">
		<liferay-ui:input-editor name="summary"/>
		<input name="<portlet:namespace/>summary" type="hidden" size="2000"/>
		</aui:field-wrapper>
	<input type="submit" name="addBookBtnSubmit" value="Submit Query"/>
</form:form>

<portlet:renderURL var="cancelProcessActionURL">
	<portlet:param name="action" value="returnMainPage" />
	<portlet:param name="jspPage" value="view" />
</portlet:renderURL>
<p>
	<br/><a href="${cancelProcessActionURL}">Cancel</a>
</p>