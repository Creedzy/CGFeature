package org.cg.Model.dto;

import java.util.List;

import org.cg.Model.MasterRef;
import org.cg.Model.Role;
import org.cg.Model.SocialMediaService;

public class UserDTO
{
	private Long userId;
	private String username;
	private List<RoleDTO> roles;
	private String name;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private boolean activated;
	private String contactPreference;
	private boolean emailActive;
	private byte[] hashKey;
	private SocialMediaService socialProvider;
	
	@Override
	public String toString() {
		return "[ UserID:" + userId + ", Username:" + username + ", Roles:" + roles + ", Name:" + name + ", email:" + email + ", socialProvider:" +
				socialProvider +", activated: "
				+ activated + ", contactPreference:" + contactPreference + ", emaiActive:" + emailActive  +", hashKey:" + hashKey + "]";
		
	}

	public void setUserId(Long userId)
	{
		this.userId = userId;
	}

	public Long getUserId()
	{
		return userId;
	}

	

	public void setRoles(List<RoleDTO> roles)
	{
		this.roles = roles;
	}

	public List<RoleDTO> getRoles()
	{
		return roles;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getEmail()
	{
		return email;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getPassword()
	{
		return password;
	}

	

	public void setActivated(boolean activated)
	{
		this.activated = activated;
	}

	public boolean isActivated()
	{
		return activated;
	}

	public void setContactPreference(String contactPreference)
	{
		this.contactPreference = contactPreference;
	}

	public String isContactPreference()
	{
		return contactPreference;
	}

	public void setEmailActive(boolean emailActive)
	{
		this.emailActive = emailActive;
	}

	public boolean isEmailActive()
	{
		return emailActive;
	}

	public void setHashKey(byte[] hashKey)
	{
		this.hashKey = hashKey;
	}

	public byte[] getHashKey()
	{
		return hashKey;
	}

	

	public String getContactPreference() {
		return contactPreference;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public SocialMediaService getSocialProvider() {
		return socialProvider;
	}

	public void setSocialProvider(SocialMediaService socialProvider) {
		this.socialProvider = socialProvider;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}}
