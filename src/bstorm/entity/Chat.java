package bstorm.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;

@Entity
public class Chat {
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@ManyToMany
	@OrderBy("created ASC")
	private Set<ChatMessage> messages;

	public Set<ChatMessage> getMessages() {
		return messages;
	}

	public void setMessages(Set<ChatMessage> messages) {
		this.messages = messages;
	}

	public Long getId() {
		return id;
	}	
}
