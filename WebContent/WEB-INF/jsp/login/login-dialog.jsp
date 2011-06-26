<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="bstorm.servlet.LoginActionServlet"%>
<%@page import="bstorm.entity.User"%>
<div id="login-dialog" title="Вход" style="display: none">
	<form name="loginForm" action="<%= getServletContext().getContextPath() + "/login.do" %>" method="post">
		<table cellspacing="3">
			<tr>
				<td align="right">
					<label>Учетная запись</label>						
				</td>
				<td>
					<input name="<%= LoginActionServlet.USER_NAME_VARIABLE %>" type="text" style="width: 150px"/>
				</td>					
			</tr>
			<tr>
				<td align="right">
					<label>Пароль</label>						
				</td>
				<td>
					<input name="<%= LoginActionServlet.PASSWORD_NAME_VARIABLE %>" type="password" style="width: 150px"/>
				</td>					
			</tr>				
		</table>
	</form>
</div>
