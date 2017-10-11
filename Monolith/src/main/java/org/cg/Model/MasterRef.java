package org.cg.Model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "MASTER_REF")
public class MasterRef {
	
	@Id
	@GeneratedValue
	private Long masterRefId;
	
	
	private Long userId;
	
	@ElementCollection
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "sender", cascade = CascadeType.ALL)
	private List<Message> sentMessages;
	
	@ElementCollection
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "receiver", cascade = CascadeType.ALL)
	private List<Message> receivedMessages;
	
	@ElementCollection
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "receiver", cascade = CascadeType.ALL)
	private List<Notification> notifications;

	@ElementCollection
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "owner", cascade = CascadeType.ALL)
	private List<Request> requests;
	
	public List<Message> getSentMessages() {
		return sentMessages;
	}
	public void setSentMessages(List<Message> sentMessages) {
		this.sentMessages = sentMessages;
	}
	public List<Message> getReceivedMessages() {
		return receivedMessages;
	}
	public void setReceivedMessages(List<Message> receivedMessages) {
		this.receivedMessages = receivedMessages;
	}
	public List<Request> getRequests() {
		return requests;
	}
	public void setRequests(List<Request> requests) {
		this.requests = requests;
	}

	public List<Notification> getNotifications() {
		return notifications;
	}
	public void setNotifications(List<Notification> notifications) {
		this.notifications = notifications;
	}
	public Long getMasterRefId() {
		return masterRefId;
	}
	public void setMasterRefId(Long masterRefId) {
		this.masterRefId = masterRefId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
}
