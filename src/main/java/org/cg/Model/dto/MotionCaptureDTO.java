package org.cg.Model.dto;

import org.cg.Model.User;
import org.joda.time.DateTime;

public class MotionCaptureDTO
{
	Long Id;
	String format;
	DateTime published;
	Long Downloads;
	int Length;
	String framerate;
	String loopable;
	User uploader;
	String url;
	String requestId;

	public void setUploader(User uploader)
	{
		this.uploader = uploader;
	}

	public User getUploader()
	{
		return uploader;
	}
	


	public void setId(Long id)
	{
		Id = id;
	}

	public Long getId()
	{
		return Id;
	}

	public void setFormat(String format)
	{
		this.format = format;
	}

	public String getFormat()
	{
		return format;
	}

	public void setPublished(DateTime published)
	{
		this.published = published;
	}

	public DateTime getPublished()
	{
		return published;
	}

	public void setDownloads(Long downloads)
	{
		Downloads = downloads;
	}

	public Long getDownloads()
	{
		return Downloads;
	}

	public void setLength(int length)
	{
		Length = length;
	}

	public int getLength()
	{
		return Length;
	}

	public void setFramerate(String framerate)
	{
		this.framerate = framerate;
	}

	public String getFramerate()
	{
		return framerate;
	}

	public void setLoopable(String loopable)
	{
		this.loopable = loopable;
	}

	public String getLoopable()
	{
		return loopable;
	}

	
	public void setUrl(String url)
	{
		this.url = url;
	}

	public String getUrl()
	{
		return url;
	}

	public void setRequestId(String requestId)
	{
		this.requestId = requestId;
	}

	public String getRequestId()
	{
		return requestId;
	}
	
	@Override
	public String toString(){
		return "NotificationDTO:[id=Id"+ Id + ", format=" + format +" ,published=" + published + " ,Downloads=" + Downloads +" ,Length=" + Length
				+" ,framerate=" + framerate +" ,loopable=" + loopable +" ,uploader=" + uploader +" ,url=" + url +" ,requestId=" + requestId
				+ "]";
	
	}
	

	
}
