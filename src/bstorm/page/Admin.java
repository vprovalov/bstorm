package bstorm.page;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.click.element.Element;
import org.apache.click.element.JsImport;

import bstorm.entity.User;

public class Admin extends BasePage {
	@Override
	public List<Element> getHeadElements() {
		if (headElements == null) {
			headElements = super.getHeadElements();

			JsImport jsBaseInit = new JsImport("/js/admin-init.js");
			headElements.add(jsBaseInit);
		}
		return headElements;
	}
	
	@Override
	public boolean onSecurityCheck() {
		HttpSession session = getContext().getRequest().getSession(false);
		if (session != null) {
			User user = (User)session.getAttribute("user");
			if (user != null && user.getRole().equals("admin")) {
				return true;
			}
		}		
		
		setRedirect(Home.class);
		return false;
	}	
}