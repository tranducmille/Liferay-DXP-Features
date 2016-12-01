<%@ include file="/init.jsp" %>
 
<portlet:actionURL name="isbnActionHanlder" var="actionUrl">
	<portlet:param name="mvcPath" value="/view.jsp" />
</portlet:actionURL>
 
<form  id="<portlet:namespace/>searchISBNForm" action="${actionUrl}" method="POST">
Enter ISBN Number: <input type="text" name="<portlet:namespace/>isbn" id="<portlet:namespace/>isbn">
 
<input type="submit" id="submitBtn" name="Submit" value="Submit">
</form>