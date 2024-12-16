package org.example;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


//crear la sesion con hibernate

public class HibernateUtil {

    private static final SessionFactory sessionFactory;

    // Iniciar el  SessionFactory
    static {
        try {
            // Carga la configuración desde hibernate.cfg.xml
            sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        } catch (Throwable ex) {

            System.err.println("Error al inicializar SessionFactory: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    //Nueva sesion
    public static Session getSession() {
        return sessionFactory.openSession();
    }

    // Cerrar  sesión
    public static void closeSession(Session session) {
        if (session != null && session.isOpen()) {
            session.close();
        }
    }
}
