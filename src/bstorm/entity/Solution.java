package bstorm.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import bstorm.GsonExclude;

@Entity
public class Solution {
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@GsonExclude
	@OneToOne
	private Task task;
	
	private Date started = new Date();
		
	private boolean finished;
	
	@OneToMany(mappedBy = "solution")
	private Set<Idea> ideas;	
	
	public Set<Idea> getIdeas() {
		if (ideas == null) {
			ideas = new HashSet<Idea>();
		}
		return ideas;
	}
	
	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	public Solution() {}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public Long getId() {
		return id;
	}	
}
