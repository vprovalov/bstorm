$(function(){
	$('#create-task').click(function() {
		$('#task-dialog').dialog('destroy');
		$('#task-dialog').dialog({
			autoOpen: false,
			width: 600,
			height: 400,
			modal: true,
			title: 'Новая задача',
			resizable: false,
			buttons: {
				'Отменить' : function() {
					$(this).dialog("close");
				},
				'Сохранить' : function() {
					$(this).dialog("close");
				}					
			}
		});
		
		$('#task-dialog').dialog('open');
		return false;
	});
	
	$('#inc-max-participants').click(function() {
		var current = parseInt($("input[name=maxParticipants]").val());
		++current;
		$("input[name=maxParticipants]").val(current);
	});
	
	$('#dec-max-participants').click(function() {
		var current = parseInt($("input[name=maxParticipants]").val());
		--current;
		if (current < 1) {
			current = 1;
		}
		$("input[name=maxParticipants]").val(current);		
	});
	
	$('#add-task-file').click(function() {
		var count = $('#files table tr').size();
		var tmp = '<tr><td><input type="file" id="hidden_select_file"/></td><td><a href="#" title="Удалить" class="small-button ui-state-default ui-corner-all"><span class="ui-icon ui-icon-circle-close"></span></a></td></tr>'
		$('#files table').append(tmp);
		$('#hidden_select_file').trigger('click');
		if ($("#hidden_select_file").val() != '') {
			$("#task-file").html($("#hidden_select_file").val());
		}
	});
});
