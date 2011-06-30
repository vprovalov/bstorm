package bstorm.dao;

import java.util.List;

import javax.persistence.EntityManager;

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
	
	public List<Task> findTasksNew() {
		return em.createQuery("SELECT t FROM Task t WHERE t.state = 'INIT'", Task.class).getResultList();
	}
	
	public List<Task> findTasksInProgress() {
		return em.createQuery("SELECT t FROM Task t WHERE t.state NOT IN ('INIT', 'FINISHED')", Task.class).getResultList();
	}
	
	public List<Task> findTasksFinished() {
		return em.createQuery("SELECT t FROM Task t WHERE t.state = 'FINISHED'", Task.class).getResultList();
	}
}
