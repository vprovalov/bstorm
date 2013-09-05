package bstorm.page;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import org.apache.click.ActionResult;
import org.apache.click.element.Element;
import org.apache.click.element.JsImport;
import org.apache.click.util.Bindable;
import org.apache.click.util.ClickUtils;

import bstorm.GsonHelper;
import bstorm.dao.ApplicationDAO;
import bstorm.dao.TaskDAO;
import bstorm.entity.Application;
import bstorm.entity.Solution;
import bstorm.entity.Task;
import bstorm.entity.User;

import com.google.gson.Gson;

public class TaskPage extends BasePage {
	private final static String SERIVICE_ERROR = "Internal Service Error";
	private final static String ERROR_MSG = "Ошибка: %s";
	private final static String INSUFFICIENT_PRIVILEGES_ERROR = "Недостаточно прав";
	private final static String TASK_IS_NOT_DEFINED = "Задача не выбрана";
	
	@Bindable
	private Long id;
	
	@Bindable
	private String shortDescription;
	@Bindable
	private String description;
	@Bindable
	private Integer maxParticipants;
	
	@Bindable
	private Long applicationId;
	
	private TaskDAO taskDao = null;
	private ApplicationDAO applicationDAO = null;
	public Task theTask = null;
	public List<Task> tasks = null;
	public Map<Long, Set<User>> applications = null;
	
	private Gson gson = GsonHelper.createGson();
		
	
	@Override
	public List<Element> getHeadElements() {
		if (headElements == null) {
			headElements = super.getHeadElements();
			
			JsImport jsimport = new JsImport("/js/task-init.js");
			headElements.add(jsimport);
		}
		return headElements;
	}
	
	private static Map<Long, Set<User>> sortApplications(List<Application> applications) {
		Map<Long, Set<User>> result = new HashMap<Long, Set<User>>();
		for (Application a : applications) {
			Long taskid = a.getTo().getId();
			Set<User> users = result.get(taskid);
			if (users == null) {
				users = new HashSet<User>();
				result.put(taskid, users);
			}
			
			users.add(a.getFrom());
		}
		
		return result;
	}
	
	@Override
	public void onInit() {
		super.onInit();

		EntityManager em = getEntityManager();
		if (em != null) {
			taskDao = new TaskDAO(em);
			applicationDAO = new ApplicationDAO(em);
			if (id == null || id == -1) {
				tasks = taskDao.getAllTasks();
				applications = sortApplications(applicationDAO.findAllApplications());
			} else {
				theTask = taskDao.findById(id);
				applications = sortApplications(applicationDAO.findApplicationsByTask(theTask));
			}
		}
	}
	
	public ActionResult updateTask() {
		Map<String, String> result = new HashMap<String, String>();
		onInit();
		
		if (user != null) {
			try {
				if (shortDescription == null || shortDescription.isEmpty()) {
					result.put("error", "Не заполнено поле Название");
				} else if (description == null || description.isEmpty()) {
					result.put("error", "Не заполнено поле Описание");
				} else if (maxParticipants == null || maxParticipants < 1) {
					result.put("error", "Максимальное количество участников <1");
				} else {
					Task task;
					if (id == null || id == -1) {
						task = new Task();
						task.setOwner(user);
					} else {
						task = taskDao.findById(id);
					}
					
					task.setDescription(description);
					task.setShortDescription(shortDescription);
					task.setMaxParticipants(maxParticipants);
					
					taskDao.update(task);
				}
			} catch(PersistenceException ex) {
				result.put("error", String.format(ERROR_MSG, ex.getMessage()));
			}
		} else {
			result.put("error", INSUFFICIENT_PRIVILEGES_ERROR);
		}
		
		return new ActionResult(gson.toJson(result), ClickUtils.getMimeType("json"));
	}
	
	public ActionResult applyTask() {
		Map<String, String> result = new HashMap<String, String>();
		onInit();
		if (user != null) {
			try {
				Task task = taskDao.findById(id);
				
				Application appl = new Application();
				appl.setFrom(user);
				appl.setTo(task);

				applicationDAO.update(appl);
			} catch(PersistenceException ex) {
				result.put("error", String.format(ERROR_MSG, ex.getMessage()));
			}			
		} else {
			result.put("error", INSUFFICIENT_PRIVILEGES_ERROR);
		}
		return new ActionResult(gson.toJson(result), ClickUtils.getMimeType("json"));
	}
	
	public ActionResult acceptApplication() {
		Map<String, String> result = new HashMap<String, String>();
		onInit();
		if (user != null) {
			try {				
				Application application = applicationDAO.findById(applicationId);
				if (application != null) {
					Task task = application.getTo();
				
					if (task != null) {
						
						if (task.getMaxParticipants() < task.getParticipants().size()) {
							taskDao.accept(task, application);
						} else {
							result.put("error", "Превышено количество участников!");
						}
					}
				} else {
					result.put("error", "Не верный номер заявки");					
				}
			} catch(PersistenceException ex) {
				result.put("error", String.format(ERROR_MSG, ex.getMessage()));
			}			
		} else {
			result.put("error", INSUFFICIENT_PRIVILEGES_ERROR);
		}
		return new ActionResult(gson.toJson(result), ClickUtils.getMimeType("json"));		
	}
	
	public ActionResult rejectApplication() {
		Map<String, String> result = new HashMap<String, String>();
		onInit();
		if (user != null) {
			try {
				Application application = applicationDAO.findById(applicationId);
				
				if (application != null) {
					applicationDAO.remove(application);
				}
			} catch(PersistenceException ex) {
				result.put("error", String.format(ERROR_MSG, ex.getMessage()));
			}			
		} else {
			result.put("error", INSUFFICIENT_PRIVILEGES_ERROR);
		}
		return new ActionResult(gson.toJson(result), ClickUtils.getMimeType("json"));		
	}	
	
	public ActionResult getTask() {
		String result = "";
		onInit();
		if (user != null) {
			if (id != null) {
				bstorm.entity.Task task = taskDao.findById(id);
				result = gson.toJson(task);
			} else {
				result = gson.toJson(tasks);
			}
		}
		return new ActionResult(result, ClickUtils.getMimeType("json")); 
	}
	
	public ActionResult getApplications() {
		Map<String, Object> result = new HashMap<String, Object>();
		onInit();
		if (user != null) {
			if (id != null) {
				List<Application> applications = applicationDAO.findApplicationsByTaskId(id);
				result.put("applications", applications);
				result.put("task", theTask);
			} else {
				result.put("applications", applications);
				result.put("tasks", tasks);
			}
		}
		return new ActionResult(gson.toJson(result), ClickUtils.getMimeType("json"));	
	}
	
	public ActionResult startSolution() {
		Map<String, Object> result = new HashMap<String, Object>();
		onInit();
		if (user != null) {
			if (theTask != null) {
				Solution solution = taskDao.startSolution(theTask);
				result.put("solutionid", solution.getId());
			} else {
				result.put("error", TASK_IS_NOT_DEFINED);
			}
		} else {
			result.put("error", INSUFFICIENT_PRIVILEGES_ERROR);
		}
		return new ActionResult(gson.toJson(result), ClickUtils.getMimeType("json"));		
	}
}