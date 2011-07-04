package bstorm.page;

import java.util.List;

import javax.persistence.EntityManager;

import org.apache.click.ActionResult;
import org.apache.click.control.Form;
import org.apache.click.element.CssImport;
import org.apache.click.element.Element;
import org.apache.click.element.JsImport;
import org.apache.click.util.Bindable;
import org.apache.click.util.ClickUtils;

import bstorm.dao.TaskDAO;
import bstorm.entity.Task;

import com.google.gson.Gson;

public class TaskPage extends BasePage {
	@Bindable
	private Long id;
	
	@Bindable
	private String shortDescription;
	@Bindable
	private String description;
	@Bindable
	private Integer maxParticipants;

	
	private TaskDAO taskDao = null;
	public Task theTask = null;
	public List<Task> tasks = null;
		
	
	@Override
	public List<Element> getHeadElements() {
		if (headElements == null) {
			headElements = super.getHeadElements();
			
			JsImport jsimport = new JsImport("/js/task-init.js");
			headElements.add(jsimport);
			jsimport = new JsImport("/js/jquery.form.js");
			headElements.add(jsimport);			
		}
		return headElements;
	}
	
	@Override
	public void onInit() {
		super.onInit();

		EntityManager em = getEntityManager();
		if (em != null) {
			taskDao = new TaskDAO(em);
			if (id == null) {
				tasks = taskDao.getAllTasks();			
			} else {
				theTask = taskDao.findById(id);
			}
		}
	}
	
	public ActionResult updateTask() {
		onInit();
		
		if (user != null) {
			
			Task task;
			if (id == null || id == -1) {
				task = new Task();
				task.setOwner(user);
				task.setState("INIT");
			} else {
				task = taskDao.findById(id);
			}
			
			task.setDescription(description);
			task.setShortDescription(shortDescription);
			task.setMaxParticipants(maxParticipants);
			
			taskDao.update(task);
			return new ActionResult("{}", ClickUtils.getMimeType("json"));
		}
		
		return new ActionResult("{error='error'}", ClickUtils.getMimeType("json"));
	}
	
	public ActionResult getTask() {
		String result = "";
		onInit();
		if (user != null) {
			if (id != null) {
				bstorm.entity.Task task = taskDao.findById(id);
				result = (new Gson()).toJson(task);
			} else {
				result = (new Gson()).toJson(tasks);
			}
		}
		return new ActionResult(result, ClickUtils.getMimeType("json")); 
	}
}