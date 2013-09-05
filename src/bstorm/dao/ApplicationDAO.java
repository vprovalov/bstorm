package bstorm.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import bstorm.entity.Application;
import bstorm.entity.Task;

public class ApplicationDAO {
	private EntityManager em;
	
	public ApplicationDAO() {
		setEntityManager(null);
	}
	
	public ApplicationDAO(EntityManager em) {
		setEntityManager(em);
	}
	
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}
	
	public Application findById(Long id) {
		return em.find(Application.class, id);
	}
	
	public List<Application> findApplicationsByTask(Task task) {
		return em.createQuery("SELECT a FROM Application a WHERE a.to.id = '" + task.getId() + "'", Application.class).getResultList();
	}
	
	public List<Application> findApplicationsByTaskId(Long taskid) {
		return em.createQuery("SELECT a FROM Application a WHERE a.to.id = '" + taskid + "'", Application.class).getResultList();
	}
	
	public List<Application> findAllApplications() {
		return em.createQuery("SELECT a FROM Application a", Application.class).getResultList();
	}
	
	public void remove(final Application appl) {
		EntityTransaction tr = em.getTransaction();
		tr.begin();
		
		em.remove(appl);
		tr.commit();
	}

	public void update(final Application appl) {
		EntityTransaction tr = em.getTransaction();
		tr.begin();
		em.persist(appl);
		tr.commit();
	}
}
