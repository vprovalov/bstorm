package bstorm.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import bstorm.entity.User;

public class UserDAO {
	private EntityManager em;
	
	public UserDAO() {
		setEntityManager(null);
	}
	
	public UserDAO(EntityManager em) {
		setEntityManager(em);
	}
	
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}
	
	public User findByName(final String name) {
		Query q = em.createQuery("SELECT u FROM User u WHERE name='" + name + "'");
		List result = q.getResultList();
		if (result != null && result.size() > 0) {
			Object u = result.get(0);
			if (u instanceof User) return (User)u;
		}
		return null;
	}
	
	public void update(final User user) {
		EntityTransaction tr = em.getTransaction();
		tr.begin();
		em.persist(user);
		tr.commit();
	}
}
