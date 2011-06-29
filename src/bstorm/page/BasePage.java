package bstorm.page;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.http.HttpSession;

import org.apache.click.Page;

import bstorm.entity.User;
import bstorm.listener.EntityManagerFactoryInjection;

public class BasePage extends Page {
	private EntityManager em = null;
	public User user = null;
		
	@Override
	public String getTemplate() {
		return "/base.htm";
	}
	
	@Override
	public void onInit() {
		super.onInit();
		EntityManagerFactory emf = (EntityManagerFactory)getContext().getServletContext().getAttribute(EntityManagerFactoryInjection.EMF_VARIABLE);
		if (emf != null) {
			em = emf.createEntityManager();
		}
		
		HttpSession session = getContext().getRequest().getSession(false);
		if (session != null) {
			user = (User)session.getAttribute("user");
		}
	}
	
	protected EntityManager getEntityManager() {
		return em;
	}
}