package org.cg.common.model;

public class RegistrationDTO {

	String name;
	String firstName;
	String lastName;
	String email;
	String username;
	String password;
	String myRecaptchaResponse;
	
	String contactPreference;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getContactPreference() {
		return contactPreference;
	}
	public void setContactPreference(String contactPreference) {
		this.contactPreference = contactPreference;
	}
	
	
	public String getMyRecaptchaResponse() {
		return myRecaptchaResponse;
	}
	public void setMyRecaptchaResponse(String myRecaptchaResponse) {
		this.myRecaptchaResponse = myRecaptchaResponse;
	}
	@Override
	public String toString() {
		return "Reg DTO:[firstName:" + firstName + " ,lastName:" + lastName +" ,email:" + email + " ,username:" + username + " ,password:" +
	   password + " ,myRecaptchaResponse:" +myRecaptchaResponse + "]";
	}
	
}
