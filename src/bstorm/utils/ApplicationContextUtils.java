package bstorm.utils;

import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletContext;

import bstorm.listener.EntityManagerFactoryInjection;

public class ApplicationContextUtils {
	public static EntityManagerFactory getEntityManagerFactory(ServletContext context) {
		Object emf = context.getAttribute(EntityManagerFactoryInjection.EMF_VARIABLE);
		if (emf != null && emf instanceof EntityManagerFactory) {
			return (EntityManagerFactory)emf;
		}
		return null;
	}

}
