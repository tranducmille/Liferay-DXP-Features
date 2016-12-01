package com.xtivia.book.portlet.workflow;

import java.io.Serializable;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.workflow.BaseWorkflowHandler;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandler;
import com.xtivia.book.portlet.model.Book;
import com.xtivia.book.portlet.service.BookLocalServiceUtil;

@Component(property = { "model.class.name=com.xtivia.book.portlet.model.Book" }, service = WorkflowHandler.class)
public class BookEntityWorkflowHandler extends BaseWorkflowHandler<Book> {

	public static final String CLASS_NAME = Book.class.getName();

	@Override
	public String getTitle(long classPK, Locale locale){
		String title = null;
		try {
			title = BookLocalServiceUtil.getTitle(classPK, null);
		} catch (PortalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return title;
	}
	
	@Override
	public String getClassName() {
		return CLASS_NAME;
	}

	@Override
	public String getType(Locale locale) {
		return "Book Resource";
	}

	@Override
	public Book updateStatus(int status, Map<String, Serializable> workflowContext) throws PortalException {

		long userId = GetterUtil.getLong((String) workflowContext.get(WorkflowConstants.CONTEXT_USER_ID));
		long bookId = GetterUtil.getLong((String) workflowContext.get(WorkflowConstants.CONTEXT_ENTRY_CLASS_PK));

		ServiceContext serviceContext = (ServiceContext) workflowContext.get("serviceContext");

		return BookLocalServiceUtil.updateStatus(userId, bookId, status, serviceContext);
	}

}
