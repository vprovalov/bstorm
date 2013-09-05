package bstorm.listener;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import bstorm.dao.UserDAO;
import bstorm.entity.User;

@WebListener
public class EntityManagerFactoryInjection implements ServletContextListener {
	public final static String JPA_UNIT = "BrainstormingJPA";
	public final static String EMF_VARIABLE = "EntityManagerFactory" ;
	public final static String DB_PROPS_VARIABLE = "bstorm.properties";

	public EntityManagerFactoryInjection() {
    }
	
	private Map getPersistenceProperties() {
		String propsFname = System.getenv(DB_PROPS_VARIABLE);
		if (propsFname == null) return null;
		
		Properties props = new Properties();
		try {
			props.load(new FileInputStream(new File(propsFname)));
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
		return props;
	}
	
	private void initializeDB(EntityManagerFactory emf) {
		EntityManager em = emf.createEntityManager();
		
		UserDAO userdao = new UserDAO(em);
		List<User> admins = userdao.findAllAdmins();
		if (admins == null || admins.size() == 0) {
			User admin = new User();
			admin.setActive(true);
			admin.setName("admin");
			admin.setPassword("admin");
			admin.setRole("admin");
			userdao.update(admin);
		}
	}

    public void contextInitialized(ServletContextEvent contextEvent) {
    	try {
    		EntityManagerFactory emf = Persistence.createEntityManagerFactory(JPA_UNIT, getPersistenceProperties());
    		contextEvent.getServletContext().setAttribute(EMF_VARIABLE, emf);
    		initializeDB(emf);
    	} catch (PersistenceException ex) {
    		contextEvent.getServletContext().log("Exception caught during persistence system initialization:", ex);
    	}
    }

    public void contextDestroyed(ServletContextEvent contextEvent) {
    	contextEvent.getServletContext().removeAttribute(EMF_VARIABLE);
    }
}
