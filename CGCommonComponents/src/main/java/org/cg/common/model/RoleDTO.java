package org.cg.common.model;

import java.util.Date;
import java.util.List;

public class RoleDTO
{
	private Long id;
	private String role;
	private Date date;
		
	@Override
	public String toString() {
		return "Role: [id=" + id + " ,role=" + role + " ,date=" + date + "]";
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Long getId()
	{
		return id;
	}
	
	

	public void setRoleName(String roleName)
	{
		this.role = roleName;
	}

	public String getRoleName()
	{
		return role;
	}

	public void setDate(Date date)
	{
		this.date = date;
	}

	public Date getDate()
	{
		return date;
	}

	}
