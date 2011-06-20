package bstorm.action;


public class ActionUtils {
	public static String getActionName(final Class<?> actionClazz) {
		if (actionClazz.isAnnotationPresent(WebAction.class)) {
			WebAction a = actionClazz.getAnnotation(WebAction.class);
			return a.name();
		} 
		return null;
	}
	
	public static String getActionName(final Action action) {
		return getActionName(action.getClass());
	}

	public static String getView(final Class<?> actionClazz) {
		if (actionClazz.isAnnotationPresent(WebAction.class)) {
			WebAction a = actionClazz.getAnnotation(WebAction.class);
			return a.view();
		} 
		return null;
	}
	
	public static String getView(final Action action) {
		return getView(action.getClass());
	}	
	
	public static String getRole(final Class<?> actionClazz) {
		if (actionClazz.isAnnotationPresent(WebAction.class)) {
			WebAction a = actionClazz.getAnnotation(WebAction.class);
			return a.role();
		} 
		return null;
	}
	
	public static String getRoles(final Action action) {
		return getRole(action.getClass());
	}
}
