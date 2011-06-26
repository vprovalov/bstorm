package bstorm.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

@Entity
public class Task {
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String state;
	private String shortDescription;
	@Column(length=1024)
	private String description;
	
	@OneToMany
	@OrderBy("name ASC")
	private Set<User> users;

	public Task() {
	}   

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public Long getId() {
		return this.id;
	}
}
