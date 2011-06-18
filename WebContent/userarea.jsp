<%@page import="bstorm.servlet.LoginActionServlet"%>
<%@page import="bstorm.entity.User"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>bstorm User Area</title>
	<link type="text/css" href="css/cupertino/jquery-ui-1.8.12.custom.css" rel="stylesheet" />
	<link type="text/css" href="css/simple.css" rel="stylesheet" />	
	<script type="text/javascript" src="js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript" src="js/jquery-ui-1.8.12.custom.min.js"></script>
</head>
<body>
	<h1>Welcome, <%= ((User)session.getAttribute(LoginActionServlet.SESSION_USER_VARIABLE)).getName() %></h1>
</body>
</html>