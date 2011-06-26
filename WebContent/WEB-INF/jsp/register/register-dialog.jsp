<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="register-dialog" title="Регистрация участника" style="display: none">
	<form action="/register.do" method="post">
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
	</form>		
</div>
	