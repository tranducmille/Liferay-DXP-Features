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

package com.xtivia.book.portlet.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.expando.kernel.model.ExpandoBridge;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link Book}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see Book
 * @generated
 */
@ProviderType
public class BookWrapper implements Book, ModelWrapper<Book> {
	public BookWrapper(Book book) {
		_book = book;
	}

	@Override
	public Class<?> getModelClass() {
		return Book.class;
	}

	@Override
	public String getModelClassName() {
		return Book.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("bookId", getBookId());
		attributes.put("title", getTitle());
		attributes.put("author", getAuthor());
		attributes.put("isbn", getIsbn());
		attributes.put("summary", getSummary());
		attributes.put("status", getStatus());
		attributes.put("statusByUserId", getStatusByUserId());
		attributes.put("statusByUserName", getStatusByUserName());
		attributes.put("statusDate", getStatusDate());
		attributes.put("userId", getUserId());
		attributes.put("companyId", getCompanyId());
		attributes.put("groupId", getGroupId());
		attributes.put("createdDate", getCreatedDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("entryId", getEntryId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long bookId = (Long)attributes.get("bookId");

		if (bookId != null) {
			setBookId(bookId);
		}

		String title = (String)attributes.get("title");

		if (title != null) {
			setTitle(title);
		}

		String author = (String)attributes.get("author");

		if (author != null) {
			setAuthor(author);
		}

		String isbn = (String)attributes.get("isbn");

		if (isbn != null) {
			setIsbn(isbn);
		}

		String summary = (String)attributes.get("summary");

		if (summary != null) {
			setSummary(summary);
		}

		Integer status = (Integer)attributes.get("status");

		if (status != null) {
			setStatus(status);
		}

		Long statusByUserId = (Long)attributes.get("statusByUserId");

		if (statusByUserId != null) {
			setStatusByUserId(statusByUserId);
		}

		String statusByUserName = (String)attributes.get("statusByUserName");

		if (statusByUserName != null) {
			setStatusByUserName(statusByUserName);
		}

		Date statusDate = (Date)attributes.get("statusDate");

		if (statusDate != null) {
			setStatusDate(statusDate);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Date createdDate = (Date)attributes.get("createdDate");

		if (createdDate != null) {
			setCreatedDate(createdDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		Long entryId = (Long)attributes.get("entryId");

		if (entryId != null) {
			setEntryId(entryId);
		}
	}

	@Override
	public Book toEscapedModel() {
		return new BookWrapper(_book.toEscapedModel());
	}

	@Override
	public Book toUnescapedModel() {
		return new BookWrapper(_book.toUnescapedModel());
	}

	/**
	* Returns <code>true</code> if this book is approved.
	*
	* @return <code>true</code> if this book is approved; <code>false</code> otherwise
	*/
	@Override
	public boolean isApproved() {
		return _book.isApproved();
	}

	@Override
	public boolean isCachedModel() {
		return _book.isCachedModel();
	}

	/**
	* Returns <code>true</code> if this book is denied.
	*
	* @return <code>true</code> if this book is denied; <code>false</code> otherwise
	*/
	@Override
	public boolean isDenied() {
		return _book.isDenied();
	}

	/**
	* Returns <code>true</code> if this book is a draft.
	*
	* @return <code>true</code> if this book is a draft; <code>false</code> otherwise
	*/
	@Override
	public boolean isDraft() {
		return _book.isDraft();
	}

	@Override
	public boolean isEscapedModel() {
		return _book.isEscapedModel();
	}

	/**
	* Returns <code>true</code> if this book is expired.
	*
	* @return <code>true</code> if this book is expired; <code>false</code> otherwise
	*/
	@Override
	public boolean isExpired() {
		return _book.isExpired();
	}

	/**
	* Returns <code>true</code> if this book is inactive.
	*
	* @return <code>true</code> if this book is inactive; <code>false</code> otherwise
	*/
	@Override
	public boolean isInactive() {
		return _book.isInactive();
	}

	/**
	* Returns <code>true</code> if this book is incomplete.
	*
	* @return <code>true</code> if this book is incomplete; <code>false</code> otherwise
	*/
	@Override
	public boolean isIncomplete() {
		return _book.isIncomplete();
	}

	@Override
	public boolean isNew() {
		return _book.isNew();
	}

	/**
	* Returns <code>true</code> if this book is pending.
	*
	* @return <code>true</code> if this book is pending; <code>false</code> otherwise
	*/
	@Override
	public boolean isPending() {
		return _book.isPending();
	}

	/**
	* Returns <code>true</code> if this book is scheduled.
	*
	* @return <code>true</code> if this book is scheduled; <code>false</code> otherwise
	*/
	@Override
	public boolean isScheduled() {
		return _book.isScheduled();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _book.getExpandoBridge();
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<Book> toCacheModel() {
		return _book.toCacheModel();
	}

	@Override
	public int compareTo(Book book) {
		return _book.compareTo(book);
	}

	/**
	* Returns the status of this book.
	*
	* @return the status of this book
	*/
	@Override
	public int getStatus() {
		return _book.getStatus();
	}

	@Override
	public int hashCode() {
		return _book.hashCode();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _book.getPrimaryKeyObj();
	}

	@Override
	public java.lang.Object clone() {
		return new BookWrapper((Book)_book.clone());
	}

	/**
	* Returns the author of this book.
	*
	* @return the author of this book
	*/
	@Override
	public java.lang.String getAuthor() {
		return _book.getAuthor();
	}

	/**
	* Returns the isbn of this book.
	*
	* @return the isbn of this book
	*/
	@Override
	public java.lang.String getIsbn() {
		return _book.getIsbn();
	}

	/**
	* Returns the status by user name of this book.
	*
	* @return the status by user name of this book
	*/
	@Override
	public java.lang.String getStatusByUserName() {
		return _book.getStatusByUserName();
	}

	/**
	* Returns the status by user uuid of this book.
	*
	* @return the status by user uuid of this book
	*/
	@Override
	public java.lang.String getStatusByUserUuid() {
		return _book.getStatusByUserUuid();
	}

	/**
	* Returns the summary of this book.
	*
	* @return the summary of this book
	*/
	@Override
	public java.lang.String getSummary() {
		return _book.getSummary();
	}

	/**
	* Returns the title of this book.
	*
	* @return the title of this book
	*/
	@Override
	public java.lang.String getTitle() {
		return _book.getTitle();
	}

	/**
	* Returns the user uuid of this book.
	*
	* @return the user uuid of this book
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _book.getUserUuid();
	}

	/**
	* Returns the uuid of this book.
	*
	* @return the uuid of this book
	*/
	@Override
	public java.lang.String getUuid() {
		return _book.getUuid();
	}

	@Override
	public java.lang.String toString() {
		return _book.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _book.toXmlString();
	}

	/**
	* Returns the created date of this book.
	*
	* @return the created date of this book
	*/
	@Override
	public Date getCreatedDate() {
		return _book.getCreatedDate();
	}

	/**
	* Returns the modified date of this book.
	*
	* @return the modified date of this book
	*/
	@Override
	public Date getModifiedDate() {
		return _book.getModifiedDate();
	}

	/**
	* Returns the status date of this book.
	*
	* @return the status date of this book
	*/
	@Override
	public Date getStatusDate() {
		return _book.getStatusDate();
	}

	/**
	* Returns the book ID of this book.
	*
	* @return the book ID of this book
	*/
	@Override
	public long getBookId() {
		return _book.getBookId();
	}

	/**
	* Returns the company ID of this book.
	*
	* @return the company ID of this book
	*/
	@Override
	public long getCompanyId() {
		return _book.getCompanyId();
	}

	/**
	* Returns the entry ID of this book.
	*
	* @return the entry ID of this book
	*/
	@Override
	public long getEntryId() {
		return _book.getEntryId();
	}

	/**
	* Returns the group ID of this book.
	*
	* @return the group ID of this book
	*/
	@Override
	public long getGroupId() {
		return _book.getGroupId();
	}

	/**
	* Returns the primary key of this book.
	*
	* @return the primary key of this book
	*/
	@Override
	public long getPrimaryKey() {
		return _book.getPrimaryKey();
	}

	/**
	* Returns the status by user ID of this book.
	*
	* @return the status by user ID of this book
	*/
	@Override
	public long getStatusByUserId() {
		return _book.getStatusByUserId();
	}

	/**
	* Returns the user ID of this book.
	*
	* @return the user ID of this book
	*/
	@Override
	public long getUserId() {
		return _book.getUserId();
	}

	@Override
	public void persist() {
		_book.persist();
	}

	/**
	* Sets the author of this book.
	*
	* @param author the author of this book
	*/
	@Override
	public void setAuthor(java.lang.String author) {
		_book.setAuthor(author);
	}

	/**
	* Sets the book ID of this book.
	*
	* @param bookId the book ID of this book
	*/
	@Override
	public void setBookId(long bookId) {
		_book.setBookId(bookId);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_book.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this book.
	*
	* @param companyId the company ID of this book
	*/
	@Override
	public void setCompanyId(long companyId) {
		_book.setCompanyId(companyId);
	}

	/**
	* Sets the created date of this book.
	*
	* @param createdDate the created date of this book
	*/
	@Override
	public void setCreatedDate(Date createdDate) {
		_book.setCreatedDate(createdDate);
	}

	/**
	* Sets the entry ID of this book.
	*
	* @param entryId the entry ID of this book
	*/
	@Override
	public void setEntryId(long entryId) {
		_book.setEntryId(entryId);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_book.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_book.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_book.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this book.
	*
	* @param groupId the group ID of this book
	*/
	@Override
	public void setGroupId(long groupId) {
		_book.setGroupId(groupId);
	}

	/**
	* Sets the isbn of this book.
	*
	* @param isbn the isbn of this book
	*/
	@Override
	public void setIsbn(java.lang.String isbn) {
		_book.setIsbn(isbn);
	}

	/**
	* Sets the modified date of this book.
	*
	* @param modifiedDate the modified date of this book
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_book.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_book.setNew(n);
	}

	/**
	* Sets the primary key of this book.
	*
	* @param primaryKey the primary key of this book
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_book.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_book.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the status of this book.
	*
	* @param status the status of this book
	*/
	@Override
	public void setStatus(int status) {
		_book.setStatus(status);
	}

	/**
	* Sets the status by user ID of this book.
	*
	* @param statusByUserId the status by user ID of this book
	*/
	@Override
	public void setStatusByUserId(long statusByUserId) {
		_book.setStatusByUserId(statusByUserId);
	}

	/**
	* Sets the status by user name of this book.
	*
	* @param statusByUserName the status by user name of this book
	*/
	@Override
	public void setStatusByUserName(java.lang.String statusByUserName) {
		_book.setStatusByUserName(statusByUserName);
	}

	/**
	* Sets the status by user uuid of this book.
	*
	* @param statusByUserUuid the status by user uuid of this book
	*/
	@Override
	public void setStatusByUserUuid(java.lang.String statusByUserUuid) {
		_book.setStatusByUserUuid(statusByUserUuid);
	}

	/**
	* Sets the status date of this book.
	*
	* @param statusDate the status date of this book
	*/
	@Override
	public void setStatusDate(Date statusDate) {
		_book.setStatusDate(statusDate);
	}

	/**
	* Sets the summary of this book.
	*
	* @param summary the summary of this book
	*/
	@Override
	public void setSummary(java.lang.String summary) {
		_book.setSummary(summary);
	}

	/**
	* Sets the title of this book.
	*
	* @param title the title of this book
	*/
	@Override
	public void setTitle(java.lang.String title) {
		_book.setTitle(title);
	}

	/**
	* Sets the user ID of this book.
	*
	* @param userId the user ID of this book
	*/
	@Override
	public void setUserId(long userId) {
		_book.setUserId(userId);
	}

	/**
	* Sets the user uuid of this book.
	*
	* @param userUuid the user uuid of this book
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_book.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this book.
	*
	* @param uuid the uuid of this book
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_book.setUuid(uuid);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof BookWrapper)) {
			return false;
		}

		BookWrapper bookWrapper = (BookWrapper)obj;

		if (Objects.equals(_book, bookWrapper._book)) {
			return true;
		}

		return false;
	}

	@Override
	public Book getWrappedModel() {
		return _book;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _book.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _book.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_book.resetOriginalValues();
	}

	private final Book _book;
}