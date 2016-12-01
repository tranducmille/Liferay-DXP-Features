package com.xtivia.book.portlet.base;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

@Transactional
public abstract class BaseDAO<T extends BaseDomain, ID extends Number> {
		
	@Autowired(required = true)
	SessionFactory sessionFactory;
	private final Logger log = Logger.getLogger(this.getClass().getName());
	
	public Class getBaseClass(){
		ParameterizedType parameterizedType = (ParameterizedType) this.getClass().getGenericSuperclass();
		return (Class) parameterizedType.getActualTypeArguments()[0];
	}
	
	private String getPrimaryFieldName(Class<?> clazz) {
		String primaryKeyFieldName = null;
		
		if (primaryKeyFieldName == null) {
			Class<?> superclazz = clazz.getSuperclass();
			if (superclazz != null) {
				primaryKeyFieldName = this.getPrimaryFieldName(superclazz);
			}
		}
		
		return primaryKeyFieldName;
	}
	protected final SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	protected final Session getSession() {
		return getSessionFactory().getCurrentSession();
	}

	@Transactional
	public ID save(T transientInstance) {
		log.debug("saving instance " + transientInstance.getClass().getName());
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = session.getTransaction();
		transaction.begin();
		try {
			ID key = (ID) sessionFactory.getCurrentSession().save(transientInstance);
			transaction.commit();
			log.debug("save successful");
			return key;
		} catch (Exception re) {
			log.error("save failed", re);
			transaction.rollback();
			throw re;
		}
	}
	
	@Transactional
	public T saveOrUpdate(T transientInstance) {
		log.debug("saving instance " + transientInstance.getClass().getName());
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = session.getTransaction();
		transaction.begin();
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(
					transientInstance);
			transaction.commit();
			log.debug("save successful");
			return transientInstance;			
		} catch (Exception re) {
			log.error("save failed", re);
			transaction.rollback();
			throw re;
		}
	}

	@Transactional
	public void update(T transientInstance) {
		log.debug("updating instance " + transientInstance.getClass().getName());
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = session.getTransaction();
		transaction.begin();
		try {
			sessionFactory.getCurrentSession().update(transientInstance);
			transaction.commit();
			log.debug("update successful");
		} catch (Exception re) {
			log.error("update failed", re);
			transaction.rollback();
			throw re;
		}
	}
	@Transactional
	public void delete(T persistentInstance) {
		log.debug("deleting instance " + persistentInstance.getClass().getName());
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = session.getTransaction();
		transaction.begin();
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
			transaction.commit();
		} catch (Exception re) {
			log.error("delete failed");
			transaction.rollback();
			throw re;
		}
	}
	@Transactional
	public boolean delete(ID id) {
		Session session = this.getSession();
		Transaction transaction = session.beginTransaction();
		try {
			String sqlQuery = String.format("delete from %s where %s IN (%s)", this.getBaseClass().getSimpleName(), this.getPrimaryFieldName(this.getBaseClass()), id);
			Query namedQuery = session.createQuery(sqlQuery);
			int count = namedQuery.executeUpdate();
			transaction.commit();
			return count > 0;
		}
		catch(Exception ex) {
			transaction.rollback();
		}
		return true;
	}
	@Transactional
	public T findById(ID id) {
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = session.getTransaction();
		transaction.begin();
		try {
			T instance = (T) session.get(this.getBaseClass(), id);
			if (instance == null) {
				log.debug("get successful, no instance found");
			} 
			else {
				log.debug("getting instance : " + instance.getClass().getName()+ " with id " + id);
				log.debug("get successful, instance found");
			}
			transaction.commit();
			return instance;
		} catch (Exception re) {
			log.error("get failed", re);
			transaction.rollback();
			throw re;
		}
	}
	
	@Transactional
	public final List<T> findByCriteria(String fieldName, Object fieldValue){
		return this.findByCriteria(Restrictions.eq(fieldName, fieldValue));
	}

	@Transactional
	public List<T> findByCriteria(Criterion exp) {
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = session.getTransaction();
		transaction.begin();
		try {
			Criteria criteria = session.createCriteria(this.getBaseClass());
			if (exp != null) {
				criteria.add(exp);
			}
		
			List<T> results = criteria.list();
			transaction.commit();
			return results;
		} catch (Exception re) {
			log.error("find by criteria failed", re);
			transaction.rollback();
			throw re;
		}
	}
	
	@Transactional
	public List<T> findAll() {
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = session.getTransaction();
		transaction.begin();
		try {
			List<T> results = session.createCriteria(this.getBaseClass()).list();
			transaction.commit();
			return results;
		} catch (Exception re) {
			log.error("find by example failed", re);
			transaction.rollback();
			throw re;
		}
	}
}
