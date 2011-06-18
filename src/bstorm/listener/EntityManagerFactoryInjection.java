package bstorm.listener;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class EntityManagerFactoryInjection implements ServletContextListener {
	public final static String JPA_UNIT = "BrainstormingJPA";
	public final static String EMF_VARIABLE = "EntityManagerFactory" ;

	public EntityManagerFactoryInjection() {
    }

    public void contextInitialized(ServletContextEvent contextEvent) {
    	EntityManagerFactory emf = Persistence.createEntityManagerFactory(JPA_UNIT);
        contextEvent.getServletContext().setAttribute(EMF_VARIABLE, emf);
    }

    public void contextDestroyed(ServletContextEvent contextEvent) {
    	contextEvent.getServletContext().removeAttribute(EMF_VARIABLE);
    }
}
