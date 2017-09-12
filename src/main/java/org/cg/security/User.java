package org.cg.security;

import java.util.Collection;
import java.util.List;

import org.cg.Model.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class User implements UserDetails{
	public String userId;
	public String username;
	public String email;
	public String firstname;
	public String lastname;
	public List<Role> roles;
	private Collection<? extends GrantedAuthority> authorities;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	 @Override
	    public Collection<? extends GrantedAuthority> getAuthorities() {
	        return authorities;
	    }

	    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
	        this.authorities = authorities;
	    }
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    
    @Override
    public String toString() {
    	return "AuthenticatedUser:[userId=" + userId + ", username=" + username + ", firstname=" + firstname + ",lastname=" + lastname + 
    			", email=" + email + ", authorities=" + authorities + "]";
    }
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
   
}
