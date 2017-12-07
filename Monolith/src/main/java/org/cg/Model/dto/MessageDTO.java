package org.cg.Model.dto;

import org.joda.time.DateTime;

public class MessageDTO
{
	private Long messageId;
	private DateTime date;
	private UserDTO sender;
	private UserDTO receiver;
	private String message;


	public void setMessageId(Long messageId)
	{
		this.messageId = messageId;
	}

	public Long getMessageId()
	{
		return messageId;
	}

	public void setDate(DateTime date)
	{
		this.date = date;
	}

	public DateTime getDate()
	{
		return date;
	}

	public void setSender(UserDTO sender)
	{
		this.sender = sender;
	}

	public UserDTO getSender()
	{
		return sender;
	}

	public void setReceiver(UserDTO receiver)
	{
		this.receiver = receiver;
	}

	public UserDTO getReceiver()
	{
		return receiver;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public String getMessage()
	{
		return message;
	}
	
	@Override
	public String toString(){
		return "MessageDTO:[id=messageId"+ messageId + ", date=" + date +" ,sender=" + sender + " ,receiver=" + receiver +" ,message=" + message + "]";
	
	}
	
	
}
