package org.cg.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@Service
@PropertySource(value={"classpath:store.properties"},
ignoreResourceNotFound = true)
public class ConfigurationService {
	
	@Value("${facebook.access.code.url}")
	String facebookAccessCodeUrl;
		
	@Value("${facebook.token.url}")
	String facebookTokenUrl;
	
	@Value("${facebook.graph.url}")
	String facebookGraphUrl;
	
	@Value("${facebook.id}")
	String facebookId;

	@Value("${facebook.secret}")
	String facebookSecret;
	
	@Value("${google.id}")
	String googleId;
	
	@Value("${google.secret}")
	String googleSecret;
	
	@Value("${google.access.code.url}")
	String googleAccessCodeUrl;
	
	@Value("${google.token.url}")
	String googleTokenUrl;
	
	@Value("${google.userinfo.url}")
	String googleUserInfoUrl;
	
	@Value("${recaptcha.verification.url}")
	String recaptchaValidationUrl;
	
	@Value("${recaptcha.secret}")
	String recaptchaSecret;
	
	@Value("${redirect.url}")
	String redirectUrl;
	
	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	public String getFacebookId() {
		return facebookId;
	}

	public void setFacebookId(String facebookId) {
		this.facebookId = facebookId;
	}

	public String getFacebookSecret() {
		return facebookSecret;
	}

	public void setFacebookSecret(String facebookSecret) {
		this.facebookSecret = facebookSecret;
	}

	public String getGoogleId() {
		return googleId;
	}

	public void setGoogleId(String googleId) {
		this.googleId = googleId;
	}

	public String getGoogleSecret() {
		return googleSecret;
	}

	public void setGoogleSecret(String googleSecret) {
		this.googleSecret = googleSecret;
	}

	public String getFacebookAccessCodeUrl() {
		return facebookAccessCodeUrl;
	}

	public void setFacebookAccessCodeUrl(String facebookAccessCodeUrl) {
		this.facebookAccessCodeUrl = facebookAccessCodeUrl;
	}

	public String getFacebookTokenUrl() {
		return facebookTokenUrl;
	}

	public void setFacebookTokenUrl(String facebookTokenUrl) {
		this.facebookTokenUrl = facebookTokenUrl;
	}

	public String getGoogleAccessCodeUrl() {
		return googleAccessCodeUrl;
	}

	public void setGoogleAccessCodeUrl(String googleAccessCodeUrl) {
		this.googleAccessCodeUrl = googleAccessCodeUrl;
	}

	public String getGoogleTokenUrl() {
		return googleTokenUrl;
	}

	public void setGoogleTokenUrl(String googleTokenUrl) {
		this.googleTokenUrl = googleTokenUrl;
	}

	public String getFacebookGraphUrl() {
		return facebookGraphUrl;
	}

	public void setFacebookGraphUrl(String facebookGraphUrl) {
		this.facebookGraphUrl = facebookGraphUrl;
	}

	public String getGoogleUserInfoUrl() {
		return googleUserInfoUrl;
	}

	public void setGoogleUserInfoUrl(String googleUserInfoUrl) {
		this.googleUserInfoUrl = googleUserInfoUrl;
	}

	public String getRecaptchaValidationUrl() {
		return recaptchaValidationUrl;
	}

	public void setRecaptchaValidationUrl(String recaptchaValidationUrl) {
		this.recaptchaValidationUrl = recaptchaValidationUrl;
	}

	public String getRecaptchaSecret() {
		return recaptchaSecret;
	}

	public void setRecaptchaSecret(String recaptchaSecret) {
		this.recaptchaSecret = recaptchaSecret;
	}
	
}
