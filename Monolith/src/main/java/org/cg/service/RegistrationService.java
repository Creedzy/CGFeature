package org.cg.service;

import org.cg.Model.dto.RegistrationDTO;
import org.cg.Model.dto.UserDTO;
import org.cg.service.Exception.RegistrationException;
import org.springframework.http.ResponseEntity;

public interface RegistrationService {

	
	public UserDTO registerUser(RegistrationDTO user) throws RegistrationException;
	public RegistrationDTO registerSocialUser(RegistrationDTO user) throws RegistrationException;
	public Boolean verifyRecaptcha(String recaptcha);
	public Boolean verifyActivationEmail(String hash,String email);
}
