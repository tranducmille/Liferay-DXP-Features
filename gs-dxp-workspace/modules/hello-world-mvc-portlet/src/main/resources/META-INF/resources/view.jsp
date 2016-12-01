<%@ include file="/init.jsp" %>
<jsp:useBean id="userName" class="java.lang.String" scope="request"></jsp:useBean>
<p>This is the Hello You Portlet</p>
<p>Hello = <%=userName%>!</p>