package bstorm.action;

import javax.persistence.EntityManager;

public interface ActionRequiredEntityManager {
	void setEntityManager(EntityManager em);
}
