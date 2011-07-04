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
					$("#task-dialog").animate({scrollTop: 0 }, 1000);
					$('#task-dialog .loading').show();
					$.ajax({
						type: "POST",
						url: "task.htm?pageAction=updateTask",
						data: {
							id: $("#edit-task input[name=id]").val(),
							shortDescription: $("#edit-task input[name=shortDescription]").val(),
							description: $("#edit-task input[name=description]").val(),
							maxParticipants: $("#edit-task input[name=maxParticipants]").val()
						},
						success: function(result) {
							if (result.error) {
								$('#task-dialog .error strong').html(result.error);
								$('#task-dialog .error').show();
							} else {
								$("#task-dialog").dialog("close");							
							}
						},
						error: function(err) {
							$('#task-dialog .loading').hide();
							$('#task-dialog .error').show();							
						}
					});
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
});
