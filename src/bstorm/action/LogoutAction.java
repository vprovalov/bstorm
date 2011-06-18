package bstorm.action;

import javax.servlet.http.HttpSession;

public class LogoutAction extends BaseAction {

	@Override
	public String getName() {
		return "logout";
	}

	@Override
	public String getView() {
		return null;
	}

	@Override
	public String[] getParamNames() {
		return null;
	}

	@Override
	public Object doAction(HttpSession session, Object[] params)
			throws ActionException {
		// TODO Auto-generated method stub
		return null;
	}
}
