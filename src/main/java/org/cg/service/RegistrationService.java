package org.cg.service;

import org.cg.Model.dto.RegistrationDTO;
import org.cg.Model.dto.UserDTO;
import org.springframework.http.ResponseEntity;

public interface RegistrationService {

	
	public UserDTO registerUser(RegistrationDTO user);
	public RegistrationDTO registerSocialUser(RegistrationDTO user);
	public Boolean verifyRecaptcha(String recaptcha);
}
