package org.cg.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.joda.time.DateTime;
import java.io.Serializable;



@Entity
@Table(name = "MOTION_CAPTURE")
public class MotionCapture implements Serializable {
	
	
	@javax.persistence.Id
    @GeneratedValue
    @Column(name = "ID")
	Long id;
	String format;
	DateTime published;
	Long downloads;
	int length;
	String framerate;
	String loopable;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID")
	private User uploader;
	
	String url;
	
	@OneToOne(fetch = FetchType.LAZY , mappedBy = "motionCapture")
	Request request;

	
	public User getUploader() {
		return uploader;
	}
	public void setUploader(User uploader) {
		this.uploader = uploader;
	}
	public Request getRequest() {
		return request;
	}
	public void setRequest(Request request) {
		this.request = request;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Request getRequestId() {
		return request;
	}
	public void setRequestId(Request requestId) {
		this.request = requestId;
	}
	
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	
	
	public DateTime getPublished() {
		return published;
	}
	public void setPublished(DateTime published) {
		this.published = published;
	}
	
	
	public Long getDownloads() {
		return downloads;
	}
	public void setDownloads(Long downloads) {
		this.downloads = downloads;
	}
	
	
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	
	
	public String getFramerate() {
		return framerate;
	}
	public void setFramerate(String framerate) {
		this.framerate = framerate;
	}
	
	
	public String getLoopable() {
		return loopable;
	}
	public void setLoopable(String loopable) {
		this.loopable = loopable;
	}
	
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	
	
}
