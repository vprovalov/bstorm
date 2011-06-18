<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>404: not found</title>
	<link type="text/css" href="css/cupertino/jquery-ui-1.8.12.custom.css" rel="stylesheet" />
	<link type="text/css" href="css/simple.css" rel="stylesheet"/>	
</head>
<body>
	<h1>ERROR:</h1>
	Resource: "<%= request.getAttribute("javax.servlet.forward.request_uri") %>" can't be found!
</body>
</html>