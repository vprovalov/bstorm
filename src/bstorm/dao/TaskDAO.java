package bstorm.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import bstorm.entity.Application;
import bstorm.entity.Solution;
import bstorm.entity.Task;

public class TaskDAO {
	private EntityManager em;
	
	public TaskDAO() {
		setEntityManager(null);
	}
	
	public TaskDAO(EntityManager em) {
		setEntityManager(em);
	}
	
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}
	
	public Task findById(final Long id) {
		return em.find(Task.class, id);
	}

	public List<Task> getAllTasks() {
		return em.createQuery("SELECT t FROM Task t", Task.class).getResultList();
	}
	
	public List<Task> findTasksNew() {
		return em.createQuery("SELECT t FROM Task t WHERE t.state = 'INIT'", Task.class).getResultList();
	}
	
	public List<Task> findTasksInProgress() {
		return em.createQuery("SELECT t FROM Task t WHERE t.state NOT IN ('INIT', 'FINISHED')", Task.class).getResultList();
	}
	
	public List<Task> findTasksFinished() {
		return em.createQuery("SELECT t FROM Task t WHERE t.state = 'FINISHED'", Task.class).getResultList();
	}
	
	public void accept(Task task, Application application) {
		task.getParticipants().add(application.getFrom());
		
		EntityTransaction tr = em.getTransaction();	
		tr.begin();	
		em.persist(task);
		em.remove(application);
		tr.commit();
	}
	
	public Solution startSolution(Task task) {
		Solution solution = new Solution();
		
		solution.setFinished(false);
		solution.setTask(task);
		task.setSolution(solution);
		
		EntityTransaction tr = em.getTransaction();	
		tr.begin();	
		em.persist(task);
		em.persist(solution);		
		tr.commit();
		
		return solution;
	}
	
	public void update(final Task task) {
		EntityTransaction tr = em.getTransaction();
		tr.begin();
		em.persist(task);
		tr.commit();
	}
}
