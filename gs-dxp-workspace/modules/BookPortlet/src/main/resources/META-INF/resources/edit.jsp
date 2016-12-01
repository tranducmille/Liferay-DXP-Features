<%@ include file="/init.jsp" %>
<portlet:actionURL name="updateMessageAction" var="updateMessageURL">
	
</portlet:actionURL>
<form id="<portlet:namespace/>updateForm" action="<%=updateMessageURL%>" method="post">
	<aui:input type="text" name="message" size="200"/>
	<input type="submit" id="updateMessage" title="Update Message" value="Update Message">
</form>