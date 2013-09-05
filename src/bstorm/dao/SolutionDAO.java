package bstorm.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import bstorm.entity.Idea;
import bstorm.entity.Solution;
import bstorm.entity.User;

public class SolutionDAO {
	private EntityManager em;

	public void setEntityManager(EntityManager em) {
		this.em = em;
	}
	
	public SolutionDAO() {
		setEntityManager(null);
	}
	
	public SolutionDAO(EntityManager em) {
		setEntityManager(em);
	}
	
	public Solution findById(Long id) {
		return em.find(Solution.class, id);
	}
	
	public void update(Solution solution) {
		EntityTransaction tr = em.getTransaction();
		tr.begin();
		em.persist(solution);
		tr.commit();
	}
	
	public Idea addIdea(final String text, final User author, Solution solution) {
		EntityTransaction tr = em.getTransaction();
		tr.begin();
		Idea idea = new Idea();
		idea.setAuthor(author);
		//idea.setSolution(solution);
		idea.setText(text);
		
		solution.getIdeas().add(idea);
		
		em.persist(idea);		
		em.persist(solution);
		tr.commit();
		
		return idea;
	}
}
