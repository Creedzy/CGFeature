package org.cg.Model.dto;

import org.cg.Model.User;
import org.joda.time.DateTime;

public class NotificationDTO
{
	Long id;
	String message;
	
	
	DateTime date;
	private String sender;
	private UserDTO receiver;

	public void setReceiver(UserDTO userDTO)
	{
		this.receiver = userDTO;
	}

	public UserDTO getReceiver()
	{
		return receiver;
	}

	public void setSender(String sender)
	{
		this.sender = sender;
	}

	public String getSender()
	{
		return sender;
	}
	public void setId(Long id)
	{
		this.id = id;
	}

	public Long getId()
	{
		return id;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public String getMessage()
	{
		return message;
	}


	public void setDate(DateTime date)
	{
		this.date = date;
	}

	public DateTime getDate()
	{
		return date;
	}
	
	@Override
	public String toString(){
		return "NotificationDTO:[id="+ id + ", message=" + message +" ,date=" + date + " ,sender=" + sender +" ,receiver=" + receiver + "]";
	
	}

}
