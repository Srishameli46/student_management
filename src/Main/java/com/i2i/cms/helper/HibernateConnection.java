package com.i2i.cms.helper;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Helper class for managing Hibernate SessionFactory.
 */
public class HibernateConnection {
  private static final SessionFactory sessionFactory = buildSessionFactory();

  /**
   * <p>The method is used to build the SessionFactory.
   * <p>
   * @return SessionFactory instance otherwise null
   */
  private static SessionFactory buildSessionFactory() {
    try {
      return new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
    } catch(Exception ex) {
        System.err.println("Initial SessionFactory creation failed." + ex);
        ex.printStackTrace();
    }
    return null;
  }

  /**
   * <p>Method to retrieve the SessionFactory instance.
   * </p>
   * @return SessionFactory instance
   */
  public static SessionFactory getSessionFactory() {
    return sessionFactory;
  }

  /**
   * <p>Method to shutdown the SessionFactory.
   * </p>
   */
  public static void shutdown() {
    getSessionFactory().close();
  }
}