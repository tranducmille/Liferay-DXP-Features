<%@ include file="init.jsp" %>
 
<portlet:actionURL var="actionUrl">
	<portlet:param name="action" value="searchByISBN" />
</portlet:actionURL>
 
<form id="<portlet:namespace/>searchISBNForm" action="${actionUrl}" method="POST">
Enter ISBN Number: <input type="text" value="${isbn}" name="<portlet:namespace/>isbn" id="<portlet:namespace/>isbn">
 
<input type="submit" id="submitBtn" name="Submit" value="Submit">
</form>