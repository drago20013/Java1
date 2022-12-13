package pl.polsl.michal.smaluch.cipher.caesar.servlets;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 *
 * @author Michal Smaluch
 * @version 1.0
 */
@WebListener
public class JPAConnectionServletListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("CipherAppPersistanceUnit");
        event.getServletContext().setAttribute("emf", emf);
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        EntityManagerFactory emf = (EntityManagerFactory) event.getServletContext().getAttribute("emf");

        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}