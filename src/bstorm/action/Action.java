package bstorm.action;

import javax.servlet.http.HttpSession;

public interface Action {
	String getName();
	String getView();
	String[] getParamNames();
	Object doAction(HttpSession session, Object params[]) throws ActionException;
}
