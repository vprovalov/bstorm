package bstorm.page;

import java.util.List;

import javax.persistence.EntityManager;

import org.apache.click.ActionResult;
import org.apache.click.util.Bindable;
import org.apache.click.util.ClickUtils;

import bstorm.dao.TaskDAO;

import com.google.gson.Gson;

public class TaskPage extends BasePage {
	@Bindable
	private Long id;
	private TaskDAO taskDao = null;
	
	public FullTasksInformation info = null;
	
	@Override
	public void onInit() {
		super.onInit();

		EntityManager em = getEntityManager();
		if (em != null) {
			taskDao = new TaskDAO(em);
			if (id == null) {
				info = new FullTasksInformation();			
			}
		}
	}
	
	public ActionResult getTask() {
		String result = "";
		onInit();
		if (user != null) {
			if (id != null) {
				bstorm.entity.Task task = taskDao.findById(id);
				result = (new Gson()).toJson(task);
			} else {
				result = (new Gson()).toJson(info);
			}
		}
		return new ActionResult(result, ClickUtils.getMimeType("json")); 
	}	
	
	public class FullTasksInformation {
		private List<bstorm.entity.Task> newTasks = null;
		private List<bstorm.entity.Task> inProgressTasks = null;
		private List<bstorm.entity.Task> finishedTasks = null;
		
		public List<bstorm.entity.Task> getNewTasks() {
			return newTasks;
		}
		
		public List<bstorm.entity.Task> getInProgressTasks() {
			return inProgressTasks;
		}

		public List<bstorm.entity.Task> getFinishedTasks() {
			return finishedTasks;
		}

		public FullTasksInformation() {
			if (taskDao != null) {
				newTasks = taskDao.findTasksNew();
				inProgressTasks = taskDao.findTasksInProgress();
				finishedTasks = taskDao.findTasksFinished();
			}
		}
	}
}