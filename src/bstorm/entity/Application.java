package bstorm.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Application {
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@ManyToOne
	private User from;
	
	@ManyToOne
	private Task to;
	
	public User getFrom() {
		return from;
	}
	public void setFrom(User from) {
		this.from = from;
	}
	public Task getTo() {
		return to;
	}
	public void setTo(Task to) {
		this.to = to;
	}	
}
