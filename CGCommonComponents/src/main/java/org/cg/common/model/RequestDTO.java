package org.cg.common.model;

import org.joda.time.DateTime;

public class RequestDTO
{

	Long requestId;
	String description;
	String shortDescription;
	DateTime submittionDate;
	DateTime responseDate;
	String name;
	boolean completed;
	DateTime date;
	UserDTO owner;
	MotionCaptureDTO motionCapture;
	String motionCaptureVideo;
	
	public UserDTO getOwner() {
		return owner;
	}

	public void setOwner(UserDTO owner) {
		this.owner = owner;
	}

	public MotionCaptureDTO getMotionCapture() {
		return motionCapture;
	}

	public void setMotionCapture(MotionCaptureDTO motionCapture) {
		this.motionCapture = motionCapture;
	}

	public String getMotionCaptureVideo() {
		return motionCaptureVideo;
	}

	public void setMotionCaptureVideo(String motionCaptureVideo) {
		this.motionCaptureVideo = motionCaptureVideo;
	}

	

	public void setRequestId(Long requestId)
	{
		this.requestId = requestId;
	}

	public Long getRequestId()
	{
		return requestId;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getDescription()
	{
		return description;
	}

	public void setShortDescription(String shortDescription)
	{
		this.shortDescription = shortDescription;
	}

	public String getShortDescription()
	{
		return shortDescription;
	}

	public void setSubmittionDate(DateTime submittionDate)
	{
		this.submittionDate = submittionDate;
	}

	public DateTime getSubmittionDate()
	{
		return submittionDate;
	}

	public void setResponseDate(DateTime responseDate)
	{
		this.responseDate = responseDate;
	}

	public DateTime getResponseDate()
	{
		return responseDate;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}

	public void setCompleted(boolean completed)
	{
		this.completed = completed;
	}

	public boolean isCompleted()
	{
		return completed;
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
		return "NotificationDTO:[id=requestId"+ requestId + ", description=" + description +" ,shortDescription=" + shortDescription + " ,submittionDate=" + submittionDate +" ,responseDate=" + responseDate
				+" ,name=" + name +" ,completed=" + completed +" ,date=" + date +" ,owner=" + owner +" ,motionCapture=" + motionCapture	+" ,motionCaptureVideo=" + motionCaptureVideo 
				+ "]";
	
	}
	
	

}
