package keter;

import org.hibernate.*;
import org.hibernate.cfg.*;

/**
 * Startup Hibernate and provide access to the singleton SessionFactory
 */
public class HibernateUtil {

	private static SessionFactory sessionFactory;

	static {
		try {
			// 使用AnnotationConfiguration配置初始化hibernate
			sessionFactory = new AnnotationConfiguration().configure(
					"keter/hibernate.cfg.annotation.xml")
					.buildSessionFactory();
		} catch (Throwable ex) {
			ex.printStackTrace();
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public static void shutdown() {
		getSessionFactory().close();
	}
}
