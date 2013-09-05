package bstorm.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import bstorm.entity.Idea;

public class IdeaDAO {
	private EntityManager em;
	
	public IdeaDAO() {
		setEntityManager(null);
	}
	
	public IdeaDAO(EntityManager em) {
		setEntityManager(em);
	}
	
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}
	
	public Idea findById(Long id) {
		return em.find(Idea.class, id);
	}
	
	public void update(Idea idea) {
		EntityTransaction tr = em.getTransaction();
		tr.begin();
		em.persist(idea);		
		tr.commit();
	}
}
