<%@page import="java.util.List"%>
<%@page import="com.liferay.asset.kernel.service.AssetTagLocalServiceUtil"%>
<%@page import="com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.language.LanguageUtil"%>
<%@page import="com.xtivia.book.portlet.service.BookLocalServiceUtil"%>
<%@page import="com.xtivia.book.portlet.model.Book"%>
<%@ include file="/init.jsp"%>
<%@page import="com.liferay.portal.kernel.util.PortalUtil"%>
<%@page import="com.liferay.portal.kernel.util.ListUtil"%>
<%@page import="com.liferay.portal.kernel.util.ParamUtil"%>
<p>Book Details</p>
<%
	long bookdId = ParamUtil.getLong(request, "bookId");
	Book book = BookLocalServiceUtil.getBook(bookdId);
	book = book.toEscapedModel();
	request.setAttribute("book", book);
%>
<table class="bookdetail">
	<tr>
		<td class="left">Title</td>
		<td class="right">${book.title}</td>
	</tr>
	<tr>
		<td class="left">Author</td>
		<td class="right">${book.author}</td>
	</tr>
	<tr>
		<td class="left">ISBN</td>
		<td class="right">${book.isbn}</td>
	</tr>
	<tr>
		<td class="left">Summary</td>
		<td class="right">${book.summary}</td>
	</tr>
</table>

<%
        AssetEntry assetEntry = AssetEntryLocalServiceUtil.getEntry(Book.class.getName(), book.getBookId());

        String currentURL = PortalUtil.getCurrentURL(request);
        PortalUtil.addPortletBreadcrumbEntry(request, book.getTitle(),currentURL);

        PortalUtil.setPageSubtitle(book.getTitle(), request);
        PortalUtil.setPageDescription(book.getTitle(), request);

        List<AssetTag> assetTags = AssetTagLocalServiceUtil.getTags(Book.class.getName(), book.getBookId());
        PortalUtil.setPageKeywords(ListUtil.toString(assetTags, "name"),request);
%>

<c:if test="<%= themeDisplay.isSignedIn() %>">
        <liferay-ui:panel-container extended="<%= false %>"
                id="bookCollaborationPanelContainer" persistState="<%= true %>">
                <liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>"
                id="bookCollaborationPanel" persistState="<%= true %>"
                title='<%= LanguageUtil.get(request, "collaboration") %>'>
                        <liferay-ui:ratings className="<%= Book.class.getName() %>"
                                classPK="<%= book.getBookId() %>" type="stars" />

                        <br />

                        <portlet:actionURL name="invokeTaglibDiscussion" var="discussionURL" />

                        <liferay-ui:discussion className="<%= Book.class.getName() %>"
                    classPK="<%= book.getBookId() %>"
                    formAction="<%= discussionURL %>" formName="fm2"
                    ratingsEnabled="<%= true %>" redirect="<%= currentURL %>"
                    userId="<%= book.getUserId() %>" />

                </liferay-ui:panel>
        </liferay-ui:panel-container>
</c:if>

<liferay-ui:asset-links
        assetEntryId="<%= (assetEntry != null) ? assetEntry.getEntryId() : 0 %>"
        className="<%= Book.class.getName() %>"
        classPK="<%= book.getBookId() %>" />

<portlet:actionURL name="cancelProcessAction"
	var="cancelProcessActionURL">
	<portlet:param name="mvcPath" value="/view.jsp" />
</portlet:actionURL>
<portlet:renderURL var="editBookProcessActionRUL">
	<portlet:param name="bookId" value="${book.bookId}" />
	<portlet:param name="mvcPath" value="/book_edit.jsp" />
</portlet:renderURL>
<div style="width: 100px; margin-top: 10px">
	<span style="width: 100px"><a href="${cancelProcessActionURL}">Back</a></span>
	<span style="width: 100px"><a href="${editBookProcessActionRUL}">Edit</a></span>
</div>


