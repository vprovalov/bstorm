package bstorm.action;

public class LoginAction extends BaseUserAction {
	@Override
	public String getName() {		
		return "login";
	}

	@Override
	public String getView() {
		return null;
	}

	@Override
	public String[] getParamNames() {
		return new String[] { "username", "password" };
	}

	@Override
	public Object doAction(Object[] params) throws ActionException {
		if (params.length != 2) throw new InvalidParameters("");
		return null;
	}
}
