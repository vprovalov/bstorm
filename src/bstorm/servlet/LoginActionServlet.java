package bstorm.servlet;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bstorm.utils.ApplicationContextUtils;

import bstorm.dao.UserDAO;
import bstorm.entity.User;
import bstorm.filter.AuthorizationFilter;

import bstorm.listener.EntityManagerFactoryInjection;

/**
 * Servlet implementation class LoginActionServlet
 */
@WebServlet("/login.do")
public class LoginActionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String DEFAULT_REDIRECT_VALUE = "home.jsp";
	
	public static final String REDIRECT_TO_VARIABLE = "redirect-to";
	public static final String SESSION_USER_VARIABLE = "USER";
	public static final String USER_NAME_VARIABLE = "username";
	public static final String PASSWORD_NAME_VARIABLE = "password";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginActionServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    private void authorizeUser(User user, HttpServletRequest request) {
    	request.getSession().setAttribute(SESSION_USER_VARIABLE, user);
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String loginUserName = (String)request.getParameter(USER_NAME_VARIABLE);
		String loginPassword = (String)request.getParameter(PASSWORD_NAME_VARIABLE);
		String errorMsg = "unknown error";
		
		String redirectTo = request.getParameter(REDIRECT_TO_VARIABLE);
		if (redirectTo == null || redirectTo.isEmpty()) redirectTo = DEFAULT_REDIRECT_VALUE;

		try {
			if (loginUserName != null && loginUserName.length() > 0) {
				EntityManagerFactory emf = ApplicationContextUtils.getEntityManagerFactory(getServletContext());
				EntityManager em = emf.createEntityManager();
				UserDAO dao = new UserDAO(em);
				
				User user = dao.findByName(loginUserName);
				if (user != null && user.getPassword().equals(loginPassword)) {
					authorizeUser(user, request);
					response.sendRedirect(redirectTo);
					return;
				} else {
					errorMsg = "Invalid username/password!";
				}
			} else {
				errorMsg = "User is not specified!";
			}
		} catch(Exception ex) {
			errorMsg = ex.getMessage();
			getServletContext().log("Exception caught:", ex);
		}
		response.sendRedirect(request.getContextPath() + "/login.jsp?" + AuthorizationFilter.FROM_VARIABLE + "=" + redirectTo + "&errormsg=" + URLEncoder.encode(errorMsg, "UTF-8"));	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
