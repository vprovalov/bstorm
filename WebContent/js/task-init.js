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
		$("#edit-task input[name=maxParticipants]").val(5);		
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

function loadApplicants(taskid) {
	$("#applications-list").animate({scrollTop: 0 }, 1000);
	$('#applications-list .loading').show();	
	$.ajax({
		type: "POST",
		url: "task.htm?pageAction=getTask",
		data: {
			id: taskid
		},
		success: function(result) {
			$('#applications-list .loading').hide();
			if (result.error) {
				$('#applications-list .error strong').html(result.error);
				$('#applications-list .error').show();
			} else {
				var applicationsList = '';
				if (result.participants && result.participants.length > 0) {
					for (var idx = 0; idx < result.applicants.length; ++idx) {
						var application = result.applicants[idx];
						applicationsList += ('<tr>' +
						'<td style="width: 300px">' + application.firstname + ' ' + application.lastname + '</td>' +
						'<td class="data-column">' +
						'<span class="ui-icon ui-icon-check"/>' + 		
						'</td>' + 
						'<td class="actions-column">' +
						'<a href="#" class="button ui-state-default ui-corner-all" style="width:100px"><span class="ui-icon ui-icon-close"></span>Отклонить</a>' +
						'</td>' +
						'</tr>');						
					}
				}
				
				if (result.applicants && result.applicants.length > 0) {					
					for (var idx = 0; idx < result.applicants.length; ++idx) {
						var application = result.applicants[idx];
						applicationsList += ('<tr>' +
						'<td style="width: 300px">' + application.firstname + ' ' + application.lastname + '</td>' +
						'<td class="data-column">' +
						'<span class="ui-icon ui-icon-check"/>' + 		
						'</td>' + 
						'<td class="actions-column">' +
						'<a href="#" class="button ui-state-default ui-corner-all" style="width:100px"><span class="ui-icon ui-icon-check"></span>Принятьs</a>' +
						'</td>' +
						'</tr>');						
					}
				} 
				
				if (applicationsList != '') {
					$("#applications-list table").append(appstr);
				} else {
					$("#applications-list table").append('<tr><td>Заявки отсутствуют</td></tr>');
				}
			}			
		},
		error: function(err) {
			$('#task-dialog .loading').hide();
			$('#task-dialog .error').show();							
		}
	});	
}

function openApplicantsDialog(taskid) {
	$('#applications-list').dialog('destroy');
	$('#applications-list').dialog({
		autoOpen: false,
		width: 500,
		height: 400,
		modal: true,
		resizable: false,
		buttons: {
			'Закрыть' : function() {
				$(this).dialog("close");
			}					
		}
	});
	
	$('#applications-list').dialog('open');
	$("#applications-list table").html('');
	loadApplicants(taskid);
}

function applyTask(taskid) {
	$.ajax({
		type: "POST",
		url: "task.htm?pageAction=applyTask",
		data: {
			id: taskid
		},
		success: function(result) {
			if (result.error) {
				alert(result.error);
			} else {
				window.location.reload();
			}
		},
		error: function(err) {
			alert('Ошибка, повторите попытку позднее!');
		}
	});
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
