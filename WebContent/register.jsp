<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<jsp:include page="/WEB-INF/jsp/header.jsp" />

<div class="page">
	<h3>Регистрация нового участника</h3>
	<form action="<%= request.getContextPath() + "/register.do" %>" method="post">
		<table cellspacing="3">
			<tr>
				<td align="right">
					<label>Учетная запись</label>						
				</td>
				<td>
					<input name="login" type="text" style="width: 150px"/>
				</td>					
			</tr>
			<tr>
				<td align="right">
					<label>Пароль</label>						
				</td>
				<td>
					<input name="password" type="password" style="width: 150px"/>
				</td>
			</tr>
			
			<tr>
				<td align="right">
					<label>Имя</label>						
				</td>
				<td>
					<input name="text" type="password" style="width: 150px"/>
				</td>
			</tr>							
			<tr>
				<td align="right">
					<label>Фамилия</label>						
				</td>
				<td>
					<input name="text" type="password" style="width: 150px"/>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label>E-mail</label>						
				</td>
				<td>
					<input name="text" type="password" style="width: 150px"/>
				</td>
			</tr>				
		</table>
		<a id="register" href="#" title="Регистрация" class="button ui-state-default ui-corner-all">
			<span class="ui-icon ui-icon-document"></span>
			Регистрация...</a>
		<script>
			$("#register").click(function() {
				alert("Boo");
			});
		</script>
	</form>
</div>

<jsp:include page="/WEB-INF/jsp/footer.jsp" />	
