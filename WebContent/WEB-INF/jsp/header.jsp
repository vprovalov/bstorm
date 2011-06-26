<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="bstorm.servlet.LoginActionServlet"%>
<%@page import="bstorm.entity.User"%>

<%		
	User user = null;
	if (session != null) {
		user = (User)session.getAttribute(LoginActionServlet.SESSION_USER_VARIABLE);
	}
%>
    
<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<title>Метод мозгового штурма</title>
	<link href="css/common.css" rel="stylesheet" />
	<link href="css/user.css" rel="stylesheet" />
	<link type="text/css" href="css/cupertino/jquery-ui-1.8.12.custom.css" rel="stylesheet" />	
	<script type="text/javascript" src="js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript" src="js/jquery-ui-1.8.12.custom.min.js"></script>
	<script type="text/javascript">
	$(function(){
		$('.button.ui-state-default').hover(
			function() { $(this).addClass('ui-state-hover'); }, 
			function() { $(this).removeClass('ui-state-hover'); }
		);
		<% if (user == null) {%>
			<%@ include file="/WEB-INF/jsp/login/login-form-init.js" %>
			<%@ include file="/WEB-INF/jsp/register/register-form-init.js"%>
		<% } %>
	});
	</script>
</head>
<body>
	<div class="header">
		<div class="header-left">
		<a href="<%= request.getContextPath() + "/home.jsp" %>">Метод мозгового штурма</a>
		</div>
		<div class="header-right">
			<table cellspacing="4">
				<tr>
				<% if (user == null) {%>
					<td>
						<a id="login" href="#">Вход</a>
					</td>
					<td>
						<a href="<%= request.getContextPath() + "/register.jsp" %>">Регистрация</a>
					</td>
				<% } else { %>
					<tr>
						<td>
							<%= user.getName() %>
						</td>
						<td>
							[сообщений: нет]<span class="ui-icon ui-icon-mail-closed"></span>						
						</td>
						<td>
							<a href="<%= request.getContextPath() +"/logout.do"%>" title="Выход">Выйти</a>
						</td>
					</tr>
				<% } %>
				</tr>
			</table>
		</div>
	</div>
<% if (user == null) {%>
	<%@ include file="/WEB-INF/jsp/login/login-dialog.jsp" %>
	<%@ include file="/WEB-INF/jsp/register/register-dialog.jsp" %>
<% } %>	
	
	