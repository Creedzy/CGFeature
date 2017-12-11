package org.cg.common.model;

import java.util.List;



public class MasterRefDTO {

	private Long masterRefId;
	private Long userId;
	private List<MessageDTO> sentMessages;
	private List<MessageDTO> receivedMessages;
	private List<NotificationDTO> notifications;
	private List<RequestDTO> requests;
	
	
	
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
	public List<MessageDTO> getSentMessages() {
		return sentMessages;
	}
	public void setSentMessages(List<MessageDTO> sentMessages) {
		this.sentMessages = sentMessages;
	}
	public List<MessageDTO> getReceivedMessages() {
		return receivedMessages;
	}
	public void setReceivedMessages(List<MessageDTO> receivedMessages) {
		this.receivedMessages = receivedMessages;
	}
	public List<NotificationDTO> getNotifications() {
		return notifications;
	}
	public void setNotifications(List<NotificationDTO> notifications) {
		this.notifications = notifications;
	}
	public List<RequestDTO> getRequests() {
		return requests;
	}
	public void setRequests(List<RequestDTO> requests) {
		this.requests = requests;
	}
	
	@Override
	public String toString(){
		return "MessageDTO:[id=masterRefId"+ masterRefId + ", userId=" + userId +" ,sentMessages=" + sentMessages + " ,receivedMessages=" + receivedMessages 
				+" ,notifications=" + notifications +" ,requests=" + requests + "]";
	
	}
	
	
}
