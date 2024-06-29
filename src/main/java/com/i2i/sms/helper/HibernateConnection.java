package com.i2i.sms.helper;

import io.github.cdimascio.dotenv.Dotenv;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.stereotype.Component;

/**
 * <p>The method is used to build the SessionFactory.
 * <p>
 */
@Component
public class HibernateConnection {

    private static SessionFactory sessionFactory;
    private static final Logger logger = LogManager.getLogger(HibernateConnection.class);

    static {
        try {
            logger.debug("Initializing SessionFactoryProvider.");
            Dotenv dotenv = Dotenv.load();
            Configuration configuration = new Configuration();
            configuration.configure();
            configuration.setProperty("hibernate.connection.driver_class", dotenv.get("DB_DRIVER"));
            if (null != dotenv.get("DB_URL")) {
                configuration.setProperty("hibernate.connection.url", dotenv.get("DB_URL"));
            } else {
                logger.fatal("Database URL is missing in the environment configuration. SessionFactory initialization cannot proceed.");
            }
            configuration.setProperty("hibernate.connection.username", dotenv.get("DB_USERNAME"));
            configuration.setProperty("hibernate.connection.password", dotenv.get("DB_PASSWORD"));
            sessionFactory = configuration.buildSessionFactory();
            logger.info("SessionFactory initialized successfully.");
        } catch (Throwable ex) {
            logger.error("SessionFactory creation failed.", ex);
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

