package bstorm.listener;

import java.io.File;
import java.io.FileInputStream;
import java.util.Map;
import java.util.Properties;

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
	
	private Map getPersistenceProperties() {
		String propsFname = System.getenv("gstorm.properties");
		Properties props = new Properties();
		try {
			props.load(new FileInputStream(new File(propsFname)));
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
		return props;
	}

    public void contextInitialized(ServletContextEvent contextEvent) {
    	EntityManagerFactory emf = Persistence.createEntityManagerFactory(JPA_UNIT, getPersistenceProperties());
        contextEvent.getServletContext().setAttribute(EMF_VARIABLE, emf);
    }

    public void contextDestroyed(ServletContextEvent contextEvent) {
    	contextEvent.getServletContext().removeAttribute(EMF_VARIABLE);
    }
}
