<%@ include file="init.jsp" %>

<portlet:actionURL var="updateMessageURL">
	<portlet:param name="action" value="updateMsgPrefAction" />
</portlet:actionURL>

<form id="<portlet:namespace/>updateForm" action="<%=updateMessageURL%>" method="post">
	<aui:input type="text" name="message" size="200"/>
	<input type="submit" id="updateMessage" title="Update Message" value="Update Message">
</form>