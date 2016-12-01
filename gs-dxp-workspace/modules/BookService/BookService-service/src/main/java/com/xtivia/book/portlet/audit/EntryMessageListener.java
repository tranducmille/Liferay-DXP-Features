package com.xtivia.book.portlet.audit;

import java.util.List;

import org.osgi.service.component.annotations.Component;

import com.liferay.portal.kernel.audit.AuditMessage;
import com.liferay.portal.kernel.audit.AuditRouterUtil;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.xtivia.book.portlet.model.Book;
import com.xtivia.book.portlet.service.BookLocalServiceUtil;

@Component(
    immediate = true,
    service = ModelListener.class
)
public class EntryMessageListener extends BaseModelListener<Book> {
	public void onBeforeCreate(Book book) throws ModelListenerException {
		auditOnCreateOrRemove(EventTypes.ADD, book);
	}

	public void onBeforeRemove(Book book) throws ModelListenerException {
		auditOnCreateOrRemove(EventTypes.DELETE, book);
	}

	public void onBeforeUpdate(Book book) throws ModelListenerException {
		try {
			Book oldBook = BookLocalServiceUtil.getBook(book.getBookId());

			List<Attribute> attributes = getModifiedAttributes(book, oldBook);

			if (!attributes.isEmpty()) {
				AuditMessage auditMessage = AuditMessageBuilder.buildAuditMessage(EventTypes.UPDATE,
						Book.class.getName(), book.getBookId(), attributes);

				AuditRouterUtil.route(auditMessage);
			}
		} catch (Exception e) {
			throw new ModelListenerException(e);
		}
	}

	protected void auditOnCreateOrRemove(String eventType, Book book) throws ModelListenerException {

		try {
			AuditMessage auditMessage = AuditMessageBuilder.buildAuditMessage(eventType, Book.class.getName(),
					book.getBookId(), null);

			JSONObject additionalInfo = auditMessage.getAdditionalInfo();

			additionalInfo.put("title", book.getTitle());
			additionalInfo.put("isbn", book.getIsbn());
			additionalInfo.put("author", book.getAuthor());
			additionalInfo.put("summary", book.getSummary());

			AuditRouterUtil.route(auditMessage);
		} catch (Exception e) {
			throw new ModelListenerException(e);
		}
	}

	protected List<Attribute> getModifiedAttributes(Book book, Book oldBook) {

		AttributesBuilder attributesBuilder = new AttributesBuilder(book, oldBook);

		attributesBuilder.add("title");
		attributesBuilder.add("isbn");
		attributesBuilder.add("author");
		attributesBuilder.add("summary");

		List<Attribute> attributes = attributesBuilder.getAttributes();

		return attributes;
	}
}
