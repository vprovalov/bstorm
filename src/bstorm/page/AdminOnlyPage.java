package bstorm.page;

import javax.servlet.http.HttpSession;

import bstorm.entity.User;

public class AdminOnlyPage extends BasePage {
	@Override
	public boolean onSecurityCheck() {
		HttpSession session = getContext().getRequest().getSession(false);
		if (session != null) {
			User user = (User)session.getAttribute("user");
			if (user != null && user.getRole().equals("admin")) {
				return true;
			}
		}		
		
		setRedirect(HomePage.class);
		return false;
	}	
}
