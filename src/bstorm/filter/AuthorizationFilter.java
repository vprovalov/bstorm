package bstorm.filter;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bstorm.servlet.LoginActionServlet;

public class AuthorizationFilter implements Filter {
	public static final String FROM_VARIABLE = "from";
	private List<String> exclude = new ArrayList<String>();
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest req, 
			ServletResponse resp,
			FilterChain filterChain) throws IOException, ServletException 
	{
		try {
			HttpServletRequest httpRequest = (HttpServletRequest)req;
			HttpServletResponse httpResponse = (HttpServletResponse)resp;
			
			boolean checkSession = true;
			String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());
			for (String str : exclude) {
				if (path.startsWith(str)) {
					checkSession = false;
					break;
				}
			}
			
			if (checkSession) {
				HttpSession session = httpRequest.getSession(false);
				if (session == null || session.getAttribute(LoginActionServlet.SESSION_USER_VARIABLE) == null) {
					httpResponse.sendRedirect("login.jsp?" + FROM_VARIABLE + "=" + URLEncoder.encode(path, "UTF-8"));
					return;
				}
			}
			filterChain.doFilter(req, resp);
		} catch(Exception ex) {
			
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		exclude.add("/login.jsp");
		exclude.add("/login.do");
	}
}
