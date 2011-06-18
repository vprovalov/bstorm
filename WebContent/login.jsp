<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="bstorm.servlet.LoginActionServlet"%>
<%@page import="bstorm.filter.AuthorizationFilter"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>
</head>
<body>
	<% if (request.getParameter("errormsg") != null) {%>
		<b><font color="red">
		<%= request.getParameter("errormsg") %>
		</font></b>
	<% } %>
	<form method="POST" action="login.do" > 
	    <table border="0" cellspacing="5"> 
		<tr> 
		    <th align="right">Username:</th> 
		    <td align="left"><input type="text" name="username"></td> 
		</tr> 
		<tr> 
		    <th align="right">Password:</th> 
		    <td align="left"><input type="password" name="password"></td> 
		</tr> 
		<tr> 
		    <td align="right"><input type="submit" value="Log In"></td> 
		    <td align="left"><input type="reset" value="Reset"></td> 
		</tr> 
	    </table>
	    <input type="hidden" name="<%= LoginActionServlet.REDIRECT_TO_VARIABLE %>" value="<%= request.getParameter(AuthorizationFilter.FROM_VARIABLE) %>" />
    </form>	
</body>
</html>