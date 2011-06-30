package bstorm.page;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.http.HttpSession;

import org.apache.click.Page;
import org.apache.click.element.CssImport;
import org.apache.click.element.Element;
import org.apache.click.element.JsImport;

import bstorm.entity.User;
import bstorm.listener.EntityManagerFactoryInjection;

public class BasePage extends Page {
	private EntityManager em = null;
	public User user = null;
		
	@Override
	public List<Element> getHeadElements() {
		if (headElements == null) {
			headElements = super.getHeadElements();
			
			CssImport cssCommon = new CssImport("/css/common.css");
			headElements.add(cssCommon);
			CssImport cssJQueryUI = new CssImport("/css/cupertino/jquery-ui-1.8.12.custom.css");
			headElements.add(cssJQueryUI);

			JsImport jsJQuery = new JsImport("/js/jquery-1.5.1.min.js");
			headElements.add(jsJQuery);
			JsImport jsJQueryUI = new JsImport("/js/jquery-ui-1.8.12.custom.min.js");
			headElements.add(jsJQueryUI);

			JsImport jsBaseInit = new JsImport("/js/base-init.js");
			headElements.add(jsBaseInit);
			
		}
		return headElements;
	}
	
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