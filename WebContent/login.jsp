<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="bstorm.servlet.LoginActionServlet"%>
<%@page import="bstorm.filter.AuthorizationFilter"%>

<jsp:include page="/WEB-INF/jsp/header.jsp"></jsp:include>

<div class="page">
	<% String err = request.getParameter("errormsg");
		if (err != null) {
	%>	
	<div class="ui-widget">
		<div class="ui-state-error ui-corner-all" style="padding: 0 .7em;"> 
			<p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span> 
			<strong>Error:</strong> <%= err %></p>
		</div>
	</div>		
	<% } %>
	<form name="loginForm" method="POST" action="<%= getServletContext().getContextPath() + "/login.do" %>" > 
	    <table border="0" cellspacing="5"> 
		<tr> 
		    <th align="right">Username:</th> 
		    <td align="left"><input type="text" name="<%= LoginActionServlet.USER_NAME_VARIABLE %>"></td> 
		</tr> 
		<tr> 
		    <th align="right">Password:</th> 
		    <td align="left"><input type="password" name="LoginActionServlet.PASSWORD_NAME_VARIABLE"></td> 
		</tr> 
		<tr> 
		    <td align="right"><a href="#" id="login_link" class="login-form-button ui-state-default ui-corner-all">Login</a></td>
		    <td align="left"><a href="#" id="reset_link" class="login-form-button ui-state-default ui-corner-all">Reset</a></td> 
		</tr> 
	    </table>
    </form>	
</div>
<script>
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
</script>

<jsp:include page="/WEB-INF/jsp/footer.jsp"></jsp:include>
