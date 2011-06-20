package bstorm.action;

import java.util.Map;

import javax.servlet.http.HttpSession;

@WebAction(name="logout", role="user")
public class LogoutAction extends BaseUserAction {
	@Override
	public Map<String, Class<?>> getParamNames() {
		return null;
	}

	@Override
	public Object doAction(Object[] params) throws ActionException {
		if (getUser() == null) throw new ActionException("User is not logged in!");
		
		HttpSession session = getHttpRequest().getSession();
		session.invalidate();
		
		return null;
	}
}
