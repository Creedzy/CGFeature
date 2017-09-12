package org.cg.Model;

import java.util.Date;
import java.util.List;

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
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.joda.time.DateTime;

@Entity
@Table(name = "ROLE")
public class Role {
	
	@Id
    @GeneratedValue
    @Column(name = "ID")
	
	private Long id;
	private String role;
	private Date date;
	
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID")
	private User user;
	
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
	
	
	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}
	public Role(){
		
	}
	
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}


	public String getRole() {
		return role;
	}


	public void setRole(String role) {
		this.role = role;
	}
	
	
}
