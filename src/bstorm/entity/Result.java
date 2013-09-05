package bstorm.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import bstorm.GsonExclude;

@Entity
public class Result {
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@Column(nullable=false)
	private Date created;

	@GsonExclude
	@OneToOne
	private Task task;
	
	public void setTask(Task task) {
		this.task = task;
	}

	public Task getTask() {
		return task;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Long getId() {
		return id;
	}	
}
