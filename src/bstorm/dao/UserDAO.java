package bstorm.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import bstorm.entity.User;

public class UserDAO {
	private EntityManager em;
	public UserDAO(EntityManager em) {
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
}
