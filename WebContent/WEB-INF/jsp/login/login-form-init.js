$('#login-dialog').dialog({
	autoOpen: false,
	modal: true,
	resizable: false,
	buttons: {
		"Войти" : function() {
			document.loginForm.submit();
		},				
		"Закрыть" : function() {
			$(this).dialog('close');
		}
	}
});		

$('#login').click(function() {
	$('#login-dialog').dialog('open');			
	return false;
});
