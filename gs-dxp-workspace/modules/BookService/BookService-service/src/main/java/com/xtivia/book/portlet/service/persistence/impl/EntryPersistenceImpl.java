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

package com.xtivia.book.portlet.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.persistence.CompanyProvider;
import com.liferay.portal.kernel.service.persistence.CompanyProviderWrapper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.spring.extender.service.ServiceReference;

import com.xtivia.book.portlet.exception.NoSuchEntryException;
import com.xtivia.book.portlet.model.Entry;
import com.xtivia.book.portlet.model.impl.EntryImpl;
import com.xtivia.book.portlet.model.impl.EntryModelImpl;
import com.xtivia.book.portlet.service.persistence.EntryPersistence;

import java.io.Serializable;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see EntryPersistence
 * @see com.xtivia.book.portlet.service.persistence.EntryUtil
 * @generated
 */
@ProviderType
public class EntryPersistenceImpl extends BasePersistenceImpl<Entry>
	implements EntryPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link EntryUtil} to access the entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = EntryImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(EntryModelImpl.ENTITY_CACHE_ENABLED,
			EntryModelImpl.FINDER_CACHE_ENABLED, EntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(EntryModelImpl.ENTITY_CACHE_ENABLED,
			EntryModelImpl.FINDER_CACHE_ENABLED, EntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(EntryModelImpl.ENTITY_CACHE_ENABLED,
			EntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_B_G = new FinderPath(EntryModelImpl.ENTITY_CACHE_ENABLED,
			EntryModelImpl.FINDER_CACHE_ENABLED, EntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByB_G",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_B_G = new FinderPath(EntryModelImpl.ENTITY_CACHE_ENABLED,
			EntryModelImpl.FINDER_CACHE_ENABLED, EntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByB_G",
			new String[] { Long.class.getName(), Long.class.getName() },
			EntryModelImpl.GROUPID_COLUMN_BITMASK |
			EntryModelImpl.BOOKID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_B_G = new FinderPath(EntryModelImpl.ENTITY_CACHE_ENABLED,
			EntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByB_G",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns all the entries where groupId = &#63; and bookId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param bookId the book ID
	 * @return the matching entries
	 */
	@Override
	public List<Entry> findByB_G(long groupId, long bookId) {
		return findByB_G(groupId, bookId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the entries where groupId = &#63; and bookId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link EntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param bookId the book ID
	 * @param start the lower bound of the range of entries
	 * @param end the upper bound of the range of entries (not inclusive)
	 * @return the range of matching entries
	 */
	@Override
	public List<Entry> findByB_G(long groupId, long bookId, int start, int end) {
		return findByB_G(groupId, bookId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the entries where groupId = &#63; and bookId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link EntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param bookId the book ID
	 * @param start the lower bound of the range of entries
	 * @param end the upper bound of the range of entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching entries
	 */
	@Override
	public List<Entry> findByB_G(long groupId, long bookId, int start, int end,
		OrderByComparator<Entry> orderByComparator) {
		return findByB_G(groupId, bookId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the entries where groupId = &#63; and bookId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link EntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param bookId the book ID
	 * @param start the lower bound of the range of entries
	 * @param end the upper bound of the range of entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching entries
	 */
	@Override
	public List<Entry> findByB_G(long groupId, long bookId, int start, int end,
		OrderByComparator<Entry> orderByComparator, boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_B_G;
			finderArgs = new Object[] { groupId, bookId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_B_G;
			finderArgs = new Object[] {
					groupId, bookId,
					
					start, end, orderByComparator
				};
		}

		List<Entry> list = null;

		if (retrieveFromCache) {
			list = (List<Entry>)finderCache.getResult(finderPath, finderArgs,
					this);

			if ((list != null) && !list.isEmpty()) {
				for (Entry entry : list) {
					if ((groupId != entry.getGroupId()) ||
							(bookId != entry.getBookId())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_ENTRY_WHERE);

			query.append(_FINDER_COLUMN_B_G_GROUPID_2);

			query.append(_FINDER_COLUMN_B_G_BOOKID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(EntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(bookId);

				if (!pagination) {
					list = (List<Entry>)QueryUtil.list(q, getDialect(), start,
							end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<Entry>)QueryUtil.list(q, getDialect(), start,
							end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first entry in the ordered set where groupId = &#63; and bookId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param bookId the book ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching entry
	 * @throws NoSuchEntryException if a matching entry could not be found
	 */
	@Override
	public Entry findByB_G_First(long groupId, long bookId,
		OrderByComparator<Entry> orderByComparator) throws NoSuchEntryException {
		Entry entry = fetchByB_G_First(groupId, bookId, orderByComparator);

		if (entry != null) {
			return entry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", bookId=");
		msg.append(bookId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the first entry in the ordered set where groupId = &#63; and bookId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param bookId the book ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching entry, or <code>null</code> if a matching entry could not be found
	 */
	@Override
	public Entry fetchByB_G_First(long groupId, long bookId,
		OrderByComparator<Entry> orderByComparator) {
		List<Entry> list = findByB_G(groupId, bookId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last entry in the ordered set where groupId = &#63; and bookId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param bookId the book ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching entry
	 * @throws NoSuchEntryException if a matching entry could not be found
	 */
	@Override
	public Entry findByB_G_Last(long groupId, long bookId,
		OrderByComparator<Entry> orderByComparator) throws NoSuchEntryException {
		Entry entry = fetchByB_G_Last(groupId, bookId, orderByComparator);

		if (entry != null) {
			return entry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", bookId=");
		msg.append(bookId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the last entry in the ordered set where groupId = &#63; and bookId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param bookId the book ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching entry, or <code>null</code> if a matching entry could not be found
	 */
	@Override
	public Entry fetchByB_G_Last(long groupId, long bookId,
		OrderByComparator<Entry> orderByComparator) {
		int count = countByB_G(groupId, bookId);

		if (count == 0) {
			return null;
		}

		List<Entry> list = findByB_G(groupId, bookId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the entries before and after the current entry in the ordered set where groupId = &#63; and bookId = &#63;.
	 *
	 * @param entryId the primary key of the current entry
	 * @param groupId the group ID
	 * @param bookId the book ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next entry
	 * @throws NoSuchEntryException if a entry with the primary key could not be found
	 */
	@Override
	public Entry[] findByB_G_PrevAndNext(long entryId, long groupId,
		long bookId, OrderByComparator<Entry> orderByComparator)
		throws NoSuchEntryException {
		Entry entry = findByPrimaryKey(entryId);

		Session session = null;

		try {
			session = openSession();

			Entry[] array = new EntryImpl[3];

			array[0] = getByB_G_PrevAndNext(session, entry, groupId, bookId,
					orderByComparator, true);

			array[1] = entry;

			array[2] = getByB_G_PrevAndNext(session, entry, groupId, bookId,
					orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected Entry getByB_G_PrevAndNext(Session session, Entry entry,
		long groupId, long bookId, OrderByComparator<Entry> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_ENTRY_WHERE);

		query.append(_FINDER_COLUMN_B_G_GROUPID_2);

		query.append(_FINDER_COLUMN_B_G_BOOKID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(EntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(bookId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(entry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Entry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the entries where groupId = &#63; and bookId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param bookId the book ID
	 */
	@Override
	public void removeByB_G(long groupId, long bookId) {
		for (Entry entry : findByB_G(groupId, bookId, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(entry);
		}
	}

	/**
	 * Returns the number of entries where groupId = &#63; and bookId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param bookId the book ID
	 * @return the number of matching entries
	 */
	@Override
	public int countByB_G(long groupId, long bookId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_B_G;

		Object[] finderArgs = new Object[] { groupId, bookId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_ENTRY_WHERE);

			query.append(_FINDER_COLUMN_B_G_GROUPID_2);

			query.append(_FINDER_COLUMN_B_G_BOOKID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(bookId);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_B_G_GROUPID_2 = "entry.groupId = ? AND ";
	private static final String _FINDER_COLUMN_B_G_BOOKID_2 = "entry.bookId = ?";

	public EntryPersistenceImpl() {
		setModelClass(Entry.class);
	}

	/**
	 * Caches the entry in the entity cache if it is enabled.
	 *
	 * @param entry the entry
	 */
	@Override
	public void cacheResult(Entry entry) {
		entityCache.putResult(EntryModelImpl.ENTITY_CACHE_ENABLED,
			EntryImpl.class, entry.getPrimaryKey(), entry);

		entry.resetOriginalValues();
	}

	/**
	 * Caches the entries in the entity cache if it is enabled.
	 *
	 * @param entries the entries
	 */
	@Override
	public void cacheResult(List<Entry> entries) {
		for (Entry entry : entries) {
			if (entityCache.getResult(EntryModelImpl.ENTITY_CACHE_ENABLED,
						EntryImpl.class, entry.getPrimaryKey()) == null) {
				cacheResult(entry);
			}
			else {
				entry.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all entries.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(EntryImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the entry.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(Entry entry) {
		entityCache.removeResult(EntryModelImpl.ENTITY_CACHE_ENABLED,
			EntryImpl.class, entry.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<Entry> entries) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Entry entry : entries) {
			entityCache.removeResult(EntryModelImpl.ENTITY_CACHE_ENABLED,
				EntryImpl.class, entry.getPrimaryKey());
		}
	}

	/**
	 * Creates a new entry with the primary key. Does not add the entry to the database.
	 *
	 * @param entryId the primary key for the new entry
	 * @return the new entry
	 */
	@Override
	public Entry create(long entryId) {
		Entry entry = new EntryImpl();

		entry.setNew(true);
		entry.setPrimaryKey(entryId);

		entry.setCompanyId(companyProvider.getCompanyId());

		return entry;
	}

	/**
	 * Removes the entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param entryId the primary key of the entry
	 * @return the entry that was removed
	 * @throws NoSuchEntryException if a entry with the primary key could not be found
	 */
	@Override
	public Entry remove(long entryId) throws NoSuchEntryException {
		return remove((Serializable)entryId);
	}

	/**
	 * Removes the entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the entry
	 * @return the entry that was removed
	 * @throws NoSuchEntryException if a entry with the primary key could not be found
	 */
	@Override
	public Entry remove(Serializable primaryKey) throws NoSuchEntryException {
		Session session = null;

		try {
			session = openSession();

			Entry entry = (Entry)session.get(EntryImpl.class, primaryKey);

			if (entry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(entry);
		}
		catch (NoSuchEntryException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected Entry removeImpl(Entry entry) {
		entry = toUnwrappedModel(entry);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(entry)) {
				entry = (Entry)session.get(EntryImpl.class,
						entry.getPrimaryKeyObj());
			}

			if (entry != null) {
				session.delete(entry);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (entry != null) {
			clearCache(entry);
		}

		return entry;
	}

	@Override
	public Entry updateImpl(Entry entry) {
		entry = toUnwrappedModel(entry);

		boolean isNew = entry.isNew();

		EntryModelImpl entryModelImpl = (EntryModelImpl)entry;

		Session session = null;

		try {
			session = openSession();

			if (entry.isNew()) {
				session.save(entry);

				entry.setNew(false);
			}
			else {
				entry = (Entry)session.merge(entry);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !EntryModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((entryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_B_G.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						entryModelImpl.getOriginalGroupId(),
						entryModelImpl.getOriginalBookId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_B_G, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_B_G,
					args);

				args = new Object[] {
						entryModelImpl.getGroupId(), entryModelImpl.getBookId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_B_G, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_B_G,
					args);
			}
		}

		entityCache.putResult(EntryModelImpl.ENTITY_CACHE_ENABLED,
			EntryImpl.class, entry.getPrimaryKey(), entry, false);

		entry.resetOriginalValues();

		return entry;
	}

	protected Entry toUnwrappedModel(Entry entry) {
		if (entry instanceof EntryImpl) {
			return entry;
		}

		EntryImpl entryImpl = new EntryImpl();

		entryImpl.setNew(entry.isNew());
		entryImpl.setPrimaryKey(entry.getPrimaryKey());

		entryImpl.setEntryId(entry.getEntryId());
		entryImpl.setUserId(entry.getUserId());
		entryImpl.setGroupId(entry.getGroupId());
		entryImpl.setCompanyId(entry.getCompanyId());
		entryImpl.setUserName(entry.getUserName());
		entryImpl.setCreatedDate(entry.getCreatedDate());
		entryImpl.setModifiedDate(entry.getModifiedDate());
		entryImpl.setName(entry.getName());
		entryImpl.setEmail(entry.getEmail());
		entryImpl.setMessage(entry.getMessage());
		entryImpl.setBookId(entry.getBookId());

		return entryImpl;
	}

	/**
	 * Returns the entry with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the entry
	 * @return the entry
	 * @throws NoSuchEntryException if a entry with the primary key could not be found
	 */
	@Override
	public Entry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchEntryException {
		Entry entry = fetchByPrimaryKey(primaryKey);

		if (entry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return entry;
	}

	/**
	 * Returns the entry with the primary key or throws a {@link NoSuchEntryException} if it could not be found.
	 *
	 * @param entryId the primary key of the entry
	 * @return the entry
	 * @throws NoSuchEntryException if a entry with the primary key could not be found
	 */
	@Override
	public Entry findByPrimaryKey(long entryId) throws NoSuchEntryException {
		return findByPrimaryKey((Serializable)entryId);
	}

	/**
	 * Returns the entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the entry
	 * @return the entry, or <code>null</code> if a entry with the primary key could not be found
	 */
	@Override
	public Entry fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(EntryModelImpl.ENTITY_CACHE_ENABLED,
				EntryImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		Entry entry = (Entry)serializable;

		if (entry == null) {
			Session session = null;

			try {
				session = openSession();

				entry = (Entry)session.get(EntryImpl.class, primaryKey);

				if (entry != null) {
					cacheResult(entry);
				}
				else {
					entityCache.putResult(EntryModelImpl.ENTITY_CACHE_ENABLED,
						EntryImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(EntryModelImpl.ENTITY_CACHE_ENABLED,
					EntryImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return entry;
	}

	/**
	 * Returns the entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param entryId the primary key of the entry
	 * @return the entry, or <code>null</code> if a entry with the primary key could not be found
	 */
	@Override
	public Entry fetchByPrimaryKey(long entryId) {
		return fetchByPrimaryKey((Serializable)entryId);
	}

	@Override
	public Map<Serializable, Entry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, Entry> map = new HashMap<Serializable, Entry>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			Entry entry = fetchByPrimaryKey(primaryKey);

			if (entry != null) {
				map.put(primaryKey, entry);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(EntryModelImpl.ENTITY_CACHE_ENABLED,
					EntryImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (Entry)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_ENTRY_WHERE_PKS_IN);

		for (Serializable primaryKey : uncachedPrimaryKeys) {
			query.append(String.valueOf(primaryKey));

			query.append(StringPool.COMMA);
		}

		query.setIndex(query.index() - 1);

		query.append(StringPool.CLOSE_PARENTHESIS);

		String sql = query.toString();

		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(sql);

			for (Entry entry : (List<Entry>)q.list()) {
				map.put(entry.getPrimaryKeyObj(), entry);

				cacheResult(entry);

				uncachedPrimaryKeys.remove(entry.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(EntryModelImpl.ENTITY_CACHE_ENABLED,
					EntryImpl.class, primaryKey, nullModel);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		return map;
	}

	/**
	 * Returns all the entries.
	 *
	 * @return the entries
	 */
	@Override
	public List<Entry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link EntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of entries
	 * @param end the upper bound of the range of entries (not inclusive)
	 * @return the range of entries
	 */
	@Override
	public List<Entry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link EntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of entries
	 * @param end the upper bound of the range of entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of entries
	 */
	@Override
	public List<Entry> findAll(int start, int end,
		OrderByComparator<Entry> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link EntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of entries
	 * @param end the upper bound of the range of entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of entries
	 */
	@Override
	public List<Entry> findAll(int start, int end,
		OrderByComparator<Entry> orderByComparator, boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL;
			finderArgs = FINDER_ARGS_EMPTY;
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_ALL;
			finderArgs = new Object[] { start, end, orderByComparator };
		}

		List<Entry> list = null;

		if (retrieveFromCache) {
			list = (List<Entry>)finderCache.getResult(finderPath, finderArgs,
					this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_ENTRY);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_ENTRY;

				if (pagination) {
					sql = sql.concat(EntryModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<Entry>)QueryUtil.list(q, getDialect(), start,
							end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<Entry>)QueryUtil.list(q, getDialect(), start,
							end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (Entry entry : findAll()) {
			remove(entry);
		}
	}

	/**
	 * Returns the number of entries.
	 *
	 * @return the number of entries
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_ENTRY);

				count = (Long)q.uniqueResult();

				finderCache.putResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY,
					count);
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return EntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the entry persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(EntryImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@ServiceReference(type = CompanyProviderWrapper.class)
	protected CompanyProvider companyProvider;
	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;
	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;
	private static final String _SQL_SELECT_ENTRY = "SELECT entry FROM Entry entry";
	private static final String _SQL_SELECT_ENTRY_WHERE_PKS_IN = "SELECT entry FROM Entry entry WHERE entryId IN (";
	private static final String _SQL_SELECT_ENTRY_WHERE = "SELECT entry FROM Entry entry WHERE ";
	private static final String _SQL_COUNT_ENTRY = "SELECT COUNT(entry) FROM Entry entry";
	private static final String _SQL_COUNT_ENTRY_WHERE = "SELECT COUNT(entry) FROM Entry entry WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "entry.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No Entry exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No Entry exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(EntryPersistenceImpl.class);
}