package com.i2i.sms.helper;

import io.github.cdimascio.dotenv.Dotenv;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * <p>The method is used to build the SessionFactory.
 * <p>
 */
public class HibernateConnection {

    private static SessionFactory sessionFactory;

    static {
        try {
            Dotenv dotenv = Dotenv.load();
            Configuration configuration = new Configuration();
            configuration.configure();
            configuration.setProperty("hibernate.connection.driver_class", dotenv.get("DB_DRIVER"));
            configuration.setProperty("hibernate.connection.url", dotenv.get("DB_URL"));
            configuration.setProperty("hibernate.connection.username", dotenv.get("DB_USERNAME"));
            configuration.setProperty("hibernate.connection.password", dotenv.get("DB_PASSWORD"));
            sessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * <p>Method to retrieve the SessionFactory instance.
     * </p>
     *
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

