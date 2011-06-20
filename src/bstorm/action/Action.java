package bstorm.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface Action {
	Map<String, Class<?>> getParamNames();
	Object doAction(HttpServletRequest request, Object params[]) throws ActionException;
}
