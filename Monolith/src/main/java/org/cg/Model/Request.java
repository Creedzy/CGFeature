package org.cg.Model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.joda.time.DateTime;
import java.io.Serializable;
@Entity
@Table(name = "REQUEST")
public class Request implements Serializable {
	
	@Id
    @GeneratedValue
    @Column(name = "REQUEST_ID")
	Long requestId;
	String description;
	String shortDescription;
	DateTime submittionDate;
	DateTime responseDate;
	String name;
	boolean completed;
	DateTime date;
	@ManyToOne(fetch = FetchType.LAZY)
	User owner;
	@OneToOne
	MotionCapture motionCapture;
	String motionCaptureVideo;
	public User getOwner() {
		return owner;
	}
	public void setOwner(User owner) {
		this.owner = owner;
	}
	
	public Long getRequestId() {
		return requestId;
	}
	public void setRequestId(Long requestId) {
		this.requestId = requestId;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getShortDescription() {
		return shortDescription;
	}
	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}
	
	public DateTime getDate() {
		return date;
	}
	public void setDate(DateTime date) {
		this.date = date;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public DateTime getSubmittionDate() {
		return submittionDate;
	}
	public void setSubmittionDate(DateTime submittionDate) {
		this.submittionDate = submittionDate;
	}
	
	public DateTime getResponseDate() {
		return responseDate;
	}
	public void setResponseDate(DateTime responseDate) {
		this.responseDate = responseDate;
	}
	
	public boolean isCompleted() {
		return completed;
	}
	public void setCompleted(boolean completed) {
		this.completed = completed;
	}
	public MotionCapture getMotionCapture() {
		return motionCapture;
	}
	public void setMotionCapture(MotionCapture motionCapture) {
		this.motionCapture = motionCapture;
	}
	public String getMotionCaptureVideo() {
		return motionCaptureVideo;
	}
	public void setMotionCaptureVideo(String motionCaptureVideo) {
		this.motionCaptureVideo = motionCaptureVideo;
	}
}
