/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.xtivia.book.portlet.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetLinkConstants;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.WorkflowDefinitionLink;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalServiceUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.xtivia.book.portlet.exception.BookNameException;
import com.xtivia.book.portlet.model.Book;
import com.xtivia.book.portlet.model.Entry;
import com.xtivia.book.portlet.service.BookLocalServiceUtil;
import com.xtivia.book.portlet.service.EntryLocalServiceUtil;
import com.xtivia.book.portlet.service.base.BookLocalServiceBaseImpl;

import aQute.bnd.annotation.ProviderType;

/**
 * The implementation of the book local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.xtivia.book.portlet.service.BookLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see BookLocalServiceBaseImpl
 * @see com.xtivia.book.portlet.service.BookLocalServiceUtil
 */
@ProviderType
public class BookLocalServiceImpl extends BookLocalServiceBaseImpl {
	public List<Book> findBookByISBN(String isbn){
		return this.bookPersistence.findByISBN(isbn);
	}
	
	public List<Book> findAll(){
		return this.bookPersistence.findAll();
	}

	@Override
	public void createBook(Map<String, String> map) {
		// TODO Auto-generated method stub
		
	}
	public List<Book> getBooks(long groupId) throws SystemException {
	    return bookPersistence.findByGroupId(groupId);
	}

	public List<Book> getBooks(long groupId, int start, int end)
	   throws SystemException {
	    return bookPersistence.findByGroupId(groupId, start, end);
	}
	
	// This scope only validate on title
	protected void validate (String name) throws PortalException {
	    if (Validator.isNull(name)) {
	       throw new BookNameException();
	    }
	}
	
	public Book createBook(Book newBook, long userId, ServiceContext context) throws PortalException{
		Date now = new Date();
	    User user = userLocalService.getUser(userId);

		Book book = this.bookPersistence.create(counterLocalService.increment(Book.class.getName()));
		book.setTitle(newBook.getTitle());
		book.setAuthor(newBook.getAuthor());
		book.setIsbn(newBook.getIsbn());
		book.setSummary(newBook.getSummary());
		book.setUserId(context.getUserId());
		book.setCompanyId(context.getCompanyId());
		book.setGroupId(context.getScopeGroupId());
		book.setStatus(WorkflowConstants.STATUS_DRAFT);
		book.setStatusByUserId(userId);
		book.setStatusByUserName(user.getFullName());
		book.setStatusDate(context.getModifiedDate(null));
		book.setUuid(context.getUuid());
		book.setCreatedDate(context.getCreateDate(now));
		book.setModifiedDate(context.getModifiedDate(now));
		
		validate(newBook.getTitle());
		
		this.bookPersistence.update(book);
		
		resourceLocalService.addResources(context.getCompanyId(), context.getScopeGroupId(),
				userId, Book.class.getName(), book.getPrimaryKey(), false, true, true);
		
		AssetEntry assetEntry = AssetEntryLocalServiceUtil.updateEntry(userId, context.getScopeGroupId(), Book.class.getName(), book.getBookId(),
				context.getAssetCategoryIds(), context.getAssetTagNames());
		
		this.assetLinkLocalService.updateLinks(userId, assetEntry.getEntryId(), context.getAssetLinkEntryIds(),
				AssetLinkConstants.TYPE_RELATED);

		WorkflowHandlerRegistryUtil.startWorkflowInstance(book.getCompanyId(),
				book.getGroupId(), book.getUserId(), Book.class.getName(),
				book.getPrimaryKey(), book, context);
		
		 Indexer<Book>  indexer = IndexerRegistryUtil.nullSafeGetIndexer(Book.class);
		 indexer.reindex(book);	
		 
		return book;
	}
	
	public String getTitle(long primePK, Locale locale) throws PortalException{
		Book book = getBook(primePK);
		return book.getTitle();
	}
	
	public Book updateStatus(long userId, long bookId, int status,
		       ServiceContext serviceContext) throws PortalException,
		       SystemException {

		User user = userLocalService.getUser(userId);
		Book book = getBook(bookId);

		book.setStatus(status);
		book.setStatusByUserId(userId);
		book.setStatusByUserName(user.getFullName());
		book.setStatusDate(new Date());

		bookPersistence.update(book);
		if (status == WorkflowConstants.STATUS_APPROVED) {
			AssetEntryLocalServiceUtil.updateVisible(Book.class.getName(), bookId, true);
		} else {
			AssetEntryLocalServiceUtil.updateVisible(Book.class.getName(), bookId, false);
		}

		return book;
	}
	
	public void deleteNewBook(Book book) throws PortalException{
		long companyId = book.getCompanyId();
		resourceLocalService.deleteResource(companyId, Book.class.getName(), 
				ResourceConstants.SCOPE_INDIVIDUAL, book.getPrimaryKey());

		AssetEntryLocalServiceUtil.deleteEntry(Book.class.getName(), book.getBookId());
		this.bookPersistence.remove(book);

		Indexer<Book> indexer = IndexerRegistryUtil.nullSafeGetIndexer(Book.class);
		indexer.delete(book);
		
	}
	
	public Book updateBook(long userId, long bookId, Book oldBook, ServiceContext serviceContext)
			throws PortalException, SystemException {

		Date now = new Date();
		validate(oldBook.getTitle());
		Book book = getBook(bookId);
		book.setUserId(userId);
		book.setIsbn(oldBook.getIsbn());
		book.setAuthor(oldBook.getAuthor());
		book.setTitle(oldBook.getTitle());
		book.setSummary(oldBook.getSummary());
		book.setModifiedDate(serviceContext.getModifiedDate(now));
		book.setExpandoBridgeAttributes(serviceContext);
		bookPersistence.update(book);
		
		resourceLocalService.updateResources(serviceContext.getCompanyId(), serviceContext.getScopeGroupId(), Book.class.getName(),
			bookId, serviceContext.getGroupPermissions(), serviceContext.getGuestPermissions());
		AssetEntry assetEntry = AssetEntryLocalServiceUtil.updateEntry(book.getUserId(),
				  book.getGroupId(), book.getCreatedDate(),
                  book.getModifiedDate(), Book.class.getName(),
                  bookId, book.getUuid(), 0,
                  serviceContext.getAssetCategoryIds(),
                  serviceContext.getAssetTagNames(), true, false, null, null, null,null,
                  ContentTypes.TEXT_HTML, book.getTitle(), null, null, null,
                  null, 0, 0, null);
		
		 assetLinkLocalService.updateLinks(serviceContext.getUserId(),
                 assetEntry.getEntryId(), serviceContext.getAssetLinkEntryIds(),
                 AssetLinkConstants.TYPE_RELATED);

		Indexer<Book> indexer = IndexerRegistryUtil.nullSafeGetIndexer(Book.class);
		indexer.reindex(book);
		 
		return book;
	}
	
	public Book deleteBook(long bookId, ServiceContext serviceContext)
			throws PortalException, SystemException {

		Book book = getBook(bookId);
		resourceLocalService.deleteResource(serviceContext.getCompanyId(), Book.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL, bookId);
		AssetEntry assetEntry = AssetEntryLocalServiceUtil.fetchEntry(Book.class.getName(), bookId);

		assetLinkLocalService.deleteLinks(assetEntry.getEntryId());
		AssetEntryLocalServiceUtil.deleteEntry(assetEntry);
		Indexer<Book> indexer = IndexerRegistryUtil.nullSafeGetIndexer(Book.class);

		indexer.delete(book);
		book = deleteBook(book);
		return book;
	}
	
	public int getBooksCount(long groupId) throws SystemException {
        return bookPersistence.countByGroupId(groupId);
}

	@Override
	public Book updateBook(long userId, long bookId, String name, ServiceContext serviceContext)
			throws PortalException, SystemException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<Book> getBooks(long groupId, int status)
	       throws SystemException {
	    return bookPersistence.findByG_S(groupId, WorkflowConstants.STATUS_APPROVED);
	}
}