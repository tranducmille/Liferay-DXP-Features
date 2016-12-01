package com.xtivia.book.portlet.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
/**
 * @author created by dtran
 * A SessionFactory util class
 */
public class SessionFactoryUtil {
	public static SessionFactory sessionFactory = buildSessionFactory();
    private static ServiceRegistry serviceRegistry;
    
	/**
	 * Singleton method
	 * @return SessionFactory
	 */
	private static SessionFactory buildSessionFactory() {
		Configuration configuration = new Configuration();
        configuration.configure();
        serviceRegistry = new StandardServiceRegistryBuilder().applySettings(
                configuration.getProperties()).build();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        return sessionFactory;
}
 
	/**
	 * call this metehod to get instance of SessionFactory
	 * @return SessionFactory
	 */
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
 
	public static void shutdown() {
		// Close caches and connection pools
		getSessionFactory().close();
	}
}
