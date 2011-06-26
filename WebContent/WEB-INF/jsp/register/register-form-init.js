$('#register-dialog').dialog({
	autoOpen: false,
	modal: true,
	resizable: false,
	buttons: {
		"Зарегистрировать" : function() {
			$(this).dialog('close');
		},
		"Закрыть" : function() {
			$(this).dialog('close');
		}
	}
});
$('#register').click(function() {
	$('#register-dialog').dialog('open');			
	return false;
});