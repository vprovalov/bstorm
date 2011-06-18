package bstorm.filter;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bstorm.servlet.LoginActionServlet;

@WebFilter(urlPatterns={"/*"}, initParams={@WebInitParam(name="url-pattern", value=".*user.*\\.jsp" )})
public class AuthorizationFilter implements Filter {
	public static final String FROM_VARIABLE = "from";
	private Pattern urlPattern = null; 
	
	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest req, 
			ServletResponse resp,
			FilterChain filterChain) throws IOException, ServletException 
	{
		HttpServletRequest httpRequest = (HttpServletRequest)req;
		HttpServletResponse httpResponse = (HttpServletResponse)resp;
		
		try {
			String path = httpRequest.getRequestURI();
			HttpSession session = httpRequest.getSession(false);
			if (urlPattern != null) {
				Matcher matcher = urlPattern.matcher(path);
				if (matcher.matches()) {
					if (session == null || session.getAttribute(LoginActionServlet.SESSION_USER_VARIABLE) == null) {
						httpResponse.sendRedirect(req.getServletContext().getContextPath() + "/login.jsp?" + FROM_VARIABLE + "=" + URLEncoder.encode(path, "UTF-8"));
						return;
					}
				}
			}			
			filterChain.doFilter(req, resp);
		} catch(Exception ex) {
			httpResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
		}
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		String pat = config.getInitParameter("url-pattern");
		if (pat == null) pat = ".*";
		
		urlPattern = Pattern.compile(pat);
	}
}
