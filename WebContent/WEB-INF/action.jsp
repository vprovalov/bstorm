<%@page import="java.util.Map"%>
<%@page import="bstorm.action.BaseAction"%>
<%@page import="bstorm.servlet.ActionServlet"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>bStorm actions</title>
	
	<link type="text/css" href="../css/cupertino/jquery-ui-1.8.12.custom.css" rel="stylesheet" />
	<link type="text/css" href="../css/simple.css" rel="stylesheet" />	
	<script type="text/javascript" src="../js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript" src="../js/jquery-ui-1.8.12.custom.min.js"></script>	
</head>
<body>
	<h1>Actions available:</h1>
	<% 
		Map<String, Class<?>> actionsMap = (Map<String, Class<?>>)request.getAttribute(ActionServlet.ACTIONS_PARAM);
		for(Class<?> actionClazz : actionsMap.values()) {
				BaseAction action = (BaseAction)actionClazz.newInstance();
	%>
		<form name="form.<%= action.getName() %>" method="POST" action="<%= request.getServletContext().getContextPath() %>/action/<%= action.getName() %>">
			<h2><%= action.getName() %></h2>
			<%
				if (action.getParamNames() != null) {
					for(String param: action.getParamNames().keySet()) {	
			%>
					<label><%= param%>(<%= action.getParamNames().get(param).getSimpleName() %>):</label> <input type="text" name="ACTION-<%= param%>"/><br/>	
			<%
					}
				}
			%>
			<input type="submit" value="Invoke"/><br/>	
		</form>
	<%
		}		
	%>
</body>
</html>
