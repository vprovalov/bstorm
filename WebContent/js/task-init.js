function enableTaskEdit(enable) {
	$('#task-dialog .error').hide();
	if (enable) {
		$("#edit-task input[name=shortDescription]").removeAttr('disabled');
		$("#edit-task textarea[name=description]").removeAttr('disabled');
		$("#edit-task input[name=maxParticipants]").removeAttr('disabled');
		$('#inc-max-participants').show();
		$('#dec-max-participants').show();
	} else {
		$("#edit-task input[name=shortDescription]").attr('disabled', true);
		$("#edit-task textarea[name=description]").attr('disabled', true);
		$("#edit-task input[name=maxParticipants]").attr('disabled', true);
		$('#inc-max-participants').hide();
		$('#dec-max-participants').hide();
	}
}

function saveTask() {
	$("#task-dialog").animate({scrollTop: 0 }, 1000);
	$('#task-dialog .loading').show();	
	$.ajax({
		type: "POST",
		url: "task.htm?pageAction=updateTask",
		data: {
			id: $("#edit-task input[name=id]").val(),
			shortDescription: $("#edit-task input[name=shortDescription]").val(),
			description: $("#edit-task textarea[name=description]").val(),
			maxParticipants: $("#edit-task input[name=maxParticipants]").val()
		},
		success: function(result) {
			$('#task-dialog .loading').hide();
			if (result.error) {
				$('#task-dialog .error strong').html(result.error);
				$('#task-dialog .error').show();
			} else {
				$("#task-dialog").dialog("close");
				window.location.reload();
			}
		},
		error: function(err) {
			$('#task-dialog .loading').hide();
			$('#task-dialog .error').show();							
		}
	});
}

function loadTask(taskid) {
	$("#task-dialog").animate({scrollTop: 0 }, 1000);
	$('#task-dialog .loading').show();	
	$.ajax({
		type: "POST",
		url: "task.htm?pageAction=getTask",
		data: {
			id: taskid
		},
		success: function(result) {
			$('#task-dialog .loading').hide();
			if (result.error) {
				$('#task-dialog .error strong').html(result.error);
				$('#task-dialog .error').show();
			} else {
				$("#edit-task input[name=id]").val(result.id);
				$("#edit-task input[name=shortDescription]").val(result.shortDescription);
				$("#edit-task textarea[name=description]").val(result.description);
				$("#edit-task input[name=maxParticipants]").val(result.maxParticipants);				
			}
		},
		error: function(err) {
			$('#task-dialog .loading').hide();
			$('#task-dialog .error').show();							
		}
	});	
}

function openEditTaskDialog(taskid) {
	$('#task-dialog').dialog('destroy');
	$('#task-dialog').dialog({
		autoOpen: false,
		width: 600,
		height: 400,
		modal: true,
		title: (taskid != -1 ? 'Изменить задачу #' + taskid : "Новая задача"),
		resizable: false,
		buttons: {
			'Отменить' : function() {
				$(this).dialog("close");
			},
			'Сохранить' : function() {					
				saveTask();
			}					
		}
	});
	
	if (taskid != -1) {
		loadTask(taskid);
	} else {
		$("#edit-task input[name=id]").val(-1);
		$("#edit-task input[name=shortDescription]").val('');
		$("#edit-task textarea[name=description]").val('');
		$("#edit-task input[name=maxParticipants]").val('');			
		
	}
	enableTaskEdit(true);
	$('#task-dialog').dialog('open');
}

function openShowTaskDialog(taskid) {
	$('#task-dialog').dialog('destroy');
	$('#task-dialog').dialog({
		autoOpen: false,
		width: 600,
		height: 400,
		modal: true,
		title: 'Описание задачи',
		resizable: false,
		buttons: {
			'Закрыть' : function() {
				$(this).dialog("close");
			}					
		}
	});
	
	enableTaskEdit(false);
	$('#task-dialog').dialog('open');
	loadTask(taskid);
}

$(function(){
	$('#create-task').click(function() {
		openEditTaskDialog(-1);
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
