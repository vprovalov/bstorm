package bstorm.page;

import javax.servlet.http.HttpSession;

public class Logout extends org.apache.click.Page {
	@Override
	public void onInit() {
		HttpSession session = getContext().getRequest().getSession(false);
		if (session != null) {
			session.invalidate();
		}
		setRedirect(Home.class);
	}
}