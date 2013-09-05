package bstorm.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import bstorm.GsonExclude;

@Entity
public class ChatMessage {
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@Column(length=1024)
	private String message;

	@ManyToOne
	private User author;
	
	@Column(nullable=false)
	private Date created;
	
	@GsonExclude
	@OneToOne
	private Chat chat;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Chat getChat() {
		return chat;
	}

	public void setChat(Chat chat) {
		this.chat = chat;
	}

	public Long getId() {
		return id;
	}	
}
