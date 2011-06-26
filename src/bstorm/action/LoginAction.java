package bstorm.action;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import bstorm.dao.UserDAO;
import bstorm.entity.User;

@WebAction(name="login", view="/login.jsp")
public class LoginAction extends BaseUserAction implements ActionRequiredEntityManager {	
	private UserDAO dao;
	
	private final static Map<String, Class<?>> paramsMap = new HashMap<String, Class<?>>();
	static {
		paramsMap.put("username", String.class);
		paramsMap.put("password", String.class);
	}
	
	@Override
	public Map<String, Class<?>> getParamNames() {
		return paramsMap;
	}	
	
    private void authorizeUser(User user, HttpServletRequest request) {
    	request.getSession().setAttribute(SESSION_USER_VARIABLE, user);
    }
	
	@Override
	public Object doAction(Object[] params) throws ActionException {
		if (params.length != 2) throw new InvalidParameters("Required 2 params!");
		if (getUser() != null) throw new ActionException("User already logged in!");

		String loginUserName = (String)params[0];
		String loginPassword = (String)params[1];
		
		User user = dao.findByName(loginUserName);
		if (user != null && user.getPassword().equals(loginPassword)) {
			authorizeUser(user, getHttpRequest());
		} else {
			throw new SecurityException("Invalid username/password!");
		}	
		
		return null;
	}

	@Override
	public void setEntityManager(EntityManager em) {
		this.dao = new UserDAO(em);
	}
}
