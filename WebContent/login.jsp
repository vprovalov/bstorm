<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="bstorm.servlet.LoginActionServlet"%>
<%@page import="bstorm.filter.AuthorizationFilter"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Login</title>
	
	<link type="text/css" href="css/cupertino/jquery-ui-1.8.12.custom.css" rel="stylesheet" />
	<link type="text/css" href="css/simple.css" rel="stylesheet" />	
	<script type="text/javascript" src="js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript" src="js/jquery-ui-1.8.12.custom.min.js"></script>
	
	<style>
		.login-form-button {padding: .4em .4em .4em .4em; text-decoration: none;position: relative;}
	</style>
	<script type="text/javascript">
		$(function(){
			
			$('#login_link').click(function(){
				document.loginForm.submit();
				return false;
			});
			
			$('#reset_link').click(function(){
				document.loginForm.reset();
				return false;
			});

			
			$('#login_link,#reset_link').hover(
				function() { $(this).addClass('ui-state-hover'); }, 
				function() { $(this).removeClass('ui-state-hover'); }
			);
		});
	</script>
</head>
<body>
	<% if (request.getParameter("errormsg") != null) {%>	
		<div class="ui-widget">
			<div class="ui-state-error ui-corner-all" style="padding: 0 .7em;"> 
				<p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span> 
				<strong>Error:</strong> <%= request.getParameter("errormsg") %></p>
			</div>
		</div>		
	<% } %>
	<form name="loginForm" method="POST" action="login.do" > 
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
		    <td align="right"><a href="#" id="login_link" class="login-form-button ui-state-default ui-corner-all">Login</a></td>
		    <td align="left"><a href="#" id="reset_link" class="login-form-button ui-state-default ui-corner-all">Reset</a></td> 
		</tr> 
	    </table>
	    <input type="hidden" name="<%= LoginActionServlet.REDIRECT_TO_VARIABLE %>" value="<%= request.getParameter(AuthorizationFilter.FROM_VARIABLE) %>" />
    </form>	
</body>
</html>