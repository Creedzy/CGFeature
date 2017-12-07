package org.cg.Model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import org.joda.time.DateTime;


@Entity
@Table(name = "MESSAGES")
public class Message implements Serializable  {
	
	@Id
    @GeneratedValue
    @Column(name = "MEESSAGE_ID")
	private Long messageId;
	private DateTime date;
	@ManyToOne(fetch = FetchType.LAZY)
	private User sender;
	@ManyToOne(fetch = FetchType.LAZY)
	private User receiver;
	private String message;
	
	
	public Long getMessageId() {
		return messageId;
	}
	
	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}
	
	public DateTime getDate() {
		return date;
	}
	public void setDate(DateTime date) {
		this.date = date;
	}
	
	public User getSender() {
		return sender;
	}
	public void setSender(User sender) {
		this.sender = sender;
	}
	
	public User getReceiver() {
		return receiver;
	}
	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
