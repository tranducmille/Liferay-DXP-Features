<%@ include file="/init.jsp" %>
<jsp:useBean id="addNameURL" class="java.lang.String" scope="request"></jsp:useBean>
<form id="<portlet:namespace/>helloForm" action="<%=addNameURL%>" method="post">
	<table>
		<tr>
			<td>Name:</td>
			<td><input type="text" name="<portlet:namespace/>username" id="<portlet:namespace/>username"></td>
		</tr>
	</table>
	<input type="submit" id="nameButton" title="Add Name" value="Add Name">
</form>