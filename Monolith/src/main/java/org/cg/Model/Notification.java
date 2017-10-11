package org.cg.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.joda.time.DateTime;
import java.io.Serializable;

@Entity
@Table(name = "NOTIFICATION")
public class Notification implements Serializable
{
	@Id
    @GeneratedValue
    @Column(name = "ID")
	Long id;
	String message;
	DateTime date;
    
	String sender;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private User receiver;
	
	
	public void setId(Long id)
	{
		this.id = id;
	}
    
	public Long getId()
	{
		return id;
	}

	public void setDate(DateTime date)
	{
		this.date = date;
	}
    
	public DateTime getDate()
	{
		return date;
	}

	public void setSender(String sender)
	{
		this.sender = sender;
	}
    
	public String getSender()
	{
		return sender;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}
    
	public String getMessage()
	{
		return message;
	}
	


	public void setReceiver(User receiver)
	{
		this.receiver = receiver;
	}
    
	public User getReceiver()
	{
		return receiver;
	}}
