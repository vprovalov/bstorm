package bstorm.action;

import javax.servlet.http.HttpSession;

public abstract class BaseUserAction extends BaseAction {
	private HttpSession session;

	@Override
	final public Object doAction(HttpSession session, Object[] params)
			throws ActionException {
		this.session = session;		
		return doAction(params);
	}

	public abstract Object doAction(Object[] params) throws ActionException;
}
