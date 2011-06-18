package bstorm.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bstorm.action.Action;
import bstorm.action.LoginAction;
import bstorm.action.LogoutAction;

@WebServlet("/action/*")
public class ActionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String ACTIONS_PARAM = "ACTIONS-PARAM"; 
       
    public ActionServlet() {
        super();
    }
    
    private void addAction(Action action) {
    	Map<String, Action> actions = (Map<String, Action>)getServletContext().getAttribute(ACTIONS_PARAM);
    	actions.put(action.getName(), action);
    }
    
    @Override
    public void init(ServletConfig config) throws ServletException {
    	super.init(config);
    	Map<String, Action> actionsMap = new HashMap<String, Action>();
    	config.getServletContext().setAttribute(ACTIONS_PARAM, actionsMap);
    	
    	addAction(new LoginAction());
    	addAction(new LogoutAction());
    }
    
    private static String getActionName(final String requestURI) {
    	String actionName = requestURI.substring(requestURI.indexOf("/action") + 7);
    	if (actionName.startsWith("/")) actionName = actionName.substring(1);
    	actionName.trim();
    	if (actionName.isEmpty()) actionName = null;
    	
    	return actionName;
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String actionName = getActionName(request.getRequestURI());
		RequestDispatcher dispatcher = null;
		Map<String, Action> actions = (Map<String, Action>)getServletContext().getAttribute(ACTIONS_PARAM);
		if (actionName == null) {			
			request.setAttribute(ACTIONS_PARAM, actions);			
			dispatcher = request.getRequestDispatcher("/WEB-INF/action.jsp");			
		} else {
			Action action = actions.get(actionName);
			if (action != null) {
				String accept = request.getHeader("Accept");
				if (accept.equals("application/json")) {
					response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "JSON is not supported yet!");
				} else {
					dispatcher = request.getRequestDispatcher(action.getView());
				}
			} else {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Action '" + actionName + "' is not defined!");
				return;
			}
		}
		
		if (dispatcher != null) {
			dispatcher.forward(request, response);
		} else {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Can't serve request: " + request.getRequestURI() + "!");			
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
