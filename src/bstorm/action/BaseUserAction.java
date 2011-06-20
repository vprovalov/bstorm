package bstorm.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import bstorm.entity.User;

public abstract class BaseUserAction extends BaseAction {
	public static final String SESSION_USER_VARIABLE = "USER";
	
	private HttpServletRequest request;
	private User user = null;
	
	protected HttpServletRequest getHttpRequest() {
		return request;
	}
	
	protected User getUser() {
		return user;
	}

	@Override
	final public Object doAction(HttpServletRequest request, Object[] params)
			throws ActionException {
		this.request = request;
		HttpSession session = request.getSession(false);
		if (session != null) {
			Object user = session.getAttribute(SESSION_USER_VARIABLE);
			if (user != null && user instanceof User) {
				this.user = (User)user;				
			}
		}
		return doAction(params);
	}

	public abstract Object doAction(Object[] params) throws ActionException;
}
