package bstorm.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bstorm.action.Action;
import bstorm.action.ActionException;
import bstorm.action.ActionRequiredEntityManager;
import bstorm.action.ActionUtils;
import bstorm.action.BaseUserAction;
import bstorm.action.LoginAction;
import bstorm.action.LogoutAction;
import bstorm.action.WebAction;
import bstorm.entity.User;
import bstorm.utils.ApplicationContextUtils;
import bstorm.utils.UserUtils;

@WebServlet("/action/*")
public class ActionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String ACTIONS_PARAM = "ACTIONS-PARAM";
	public static final String ACTION_RESULT = "ACTION-RESULT";
	public static final String ACTION_EXCEPTION = "ACTION-EXCEPTION";
       
    public ActionServlet() {
        super();
    }
    
    private void addAction(Class<?> action) {
    	if (action.isAnnotationPresent(WebAction.class)) {
	    	Map<String, Class<?>> actions = (Map<String, Class<?>>)getServletContext().getAttribute(ACTIONS_PARAM);
	    	actions.put(ActionUtils.getActionName(action), action);
    	}
    }
    
    @Override
    public void init(ServletConfig config) throws ServletException {
    	super.init(config);
    	Map<String, Action> actionsMap = new HashMap<String, Action>();
    	config.getServletContext().setAttribute(ACTIONS_PARAM, actionsMap);
    	
    	addAction(LoginAction.class);
    	addAction(LogoutAction.class);
    }
    
    private static String getActionName(final String requestURI) {
    	String actionName = requestURI.substring(requestURI.indexOf("/action") + 7);
    	if (actionName.startsWith("/")) actionName = actionName.substring(1);
    	actionName.trim();
    	if (actionName.isEmpty()) actionName = null;
    	
    	return actionName;
    }
    
    private Map<String, Class<?>> getActionsMap() {
    	return (Map<String, Class<?>>)getServletContext().getAttribute(ACTIONS_PARAM);
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			final String actionName = getActionName(request.getRequestURI());
			RequestDispatcher dispatcher = null;
			Map<String, Class<?>> actions = getActionsMap();
			if (actionName == null) {			
				request.setAttribute(ACTIONS_PARAM, actions);
				dispatcher = request.getRequestDispatcher("/WEB-INF/action.jsp");
				dispatcher.forward(request, response);
			} else {
				Class<?> actionClazz = actions.get(actionName);
				if (actionClazz != null) {
					String roleRequired = ActionUtils.getRole(actionClazz);
					if (!roleRequired.isEmpty()) {
						HttpSession session = request.getSession(false);
						if (session == null || session.getAttribute(BaseUserAction.SESSION_USER_VARIABLE) == null) {
							throw new SecurityException("User session is required to execute this action!");
						}
						User user = (User)session.getAttribute(BaseUserAction.SESSION_USER_VARIABLE);
						if (!UserUtils.isInRole(user, roleRequired)) {
							throw new SecurityException("Not enough priveleges to execute this action!");
						}						
					}					
					
					Action action = (Action)actionClazz.newInstance();
					if (action instanceof ActionRequiredEntityManager) {
						EntityManagerFactory emf = ApplicationContextUtils.getEntityManagerFactory(request.getServletContext());
						ActionRequiredEntityManager actionRequiredEntityManager = (ActionRequiredEntityManager)action;
						actionRequiredEntityManager.setEntityManager(emf.createEntityManager());
					}
					
					String accept = request.getHeader("Accept");
					if (accept.equals("application/json")) {
						response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "JSON is not supported yet!");
					} else {
						String view = ActionUtils.getView(actionClazz);
						if (!view.isEmpty()) dispatcher = request.getRequestDispatcher(view);
					}
					
					List<String> params = new ArrayList<String>();
					Enumeration<String> paramNames = request.getParameterNames(); 
					while (paramNames.hasMoreElements()) {
						String paramName = paramNames.nextElement();
						if (paramName.startsWith("ACTION-")) {
							params.add(request.getParameter(paramName));
						}
					}
					
					Object result = null;
					try {
						result = action.doAction(request, params.toArray());						
					} catch (ActionException e) {
						request.setAttribute(ACTION_EXCEPTION, e);
					} finally {
						if (dispatcher != null) {
							request.setAttribute(ACTION_RESULT, result);
							dispatcher.forward(request, response);
						} else {
							if (request.getAttribute(ACTION_EXCEPTION) == null) {
								response.setContentType("text/html");
								response.setStatus(HttpServletResponse.SC_OK);
								response.flushBuffer();
							} else {
								ActionException ex = (ActionException)request.getAttribute(ACTION_EXCEPTION);
								response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error executing action '" + ActionUtils.getActionName(action) + "': " + ex.getMessage());
							}
						}						
					}
				} else {
					response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Action '" + actionName + "' is not defined!");
					return;
				}
			}			
		} catch (Exception ex) {
			getServletContext().log("Exception caught:", ex);
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Exception caught: " + ex.getMessage());
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
