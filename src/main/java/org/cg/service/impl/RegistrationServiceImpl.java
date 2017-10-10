package org.cg.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.Random;
import java.util.StringJoiner;

import org.cg.Model.dto.RegistrationDTO;
import org.cg.Model.dto.RoleDTO;
import org.cg.Model.dto.UserDTO;
import org.cg.config.ConfigurationService;
import org.cg.service.EncryptionService;
import org.cg.service.MasterRefService;
import org.cg.service.RegistrationService;
import org.cg.service.SESService;
import org.cg.service.UserService;
import org.cg.service.Exception.RegistrationException;
import org.cg.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;

@Service
public class RegistrationServiceImpl implements RegistrationService{
	Logger logger = LoggerFactory.getLogger(RegistrationServiceImpl.class);
	
	@Autowired
	UserService userService;
	
	@Autowired
	EncryptionService encryptionService;
	
	@Autowired
	SESService emailService;
	
	@Autowired
	MasterRefService masterRefService;
	
	@Autowired
	ConfigurationService config;
	
	RestTemplate client;
	
	Random random = new Random();
	
	@Override
	public UserDTO registerUser(RegistrationDTO userReg) throws RegistrationException {
	    //Check if the user is already registered and if it is using a social login provider
        UserDTO checkUser = userService.getUserByEmail(userReg.getEmail());
	    if(checkUserExists(checkUser)) {
	        if(isSocialProviderUser(checkUser)) {
	            throw new RegistrationException("social.user.exists");
	        }
	        throw new RegistrationException("user.exists");
	    } 
		UserDTO user = new UserDTO();
		user.setName(userReg.getFirstName() + " " + userReg.getLastName());
		user.setFirstName(userReg.getFirstName());
		user.setLastName(userReg.getLastName());
		user.setEmail(userReg.getEmail());
		if(userReg.getContactPreference() != null)
		{
			user.setContactPreference(userReg.getContactPreference());
		} else {
			user.setContactPreference("nomail");
		}	
		user.setUsername(userReg.getUsername());
		StringJoiner jr = new StringJoiner("");
		jr.add(userReg.getEmail());
		jr.add(userReg.getFirstName());
		jr.add(userReg.getUsername());
		user.setPassword(encryptionService.encodeHash(userReg.getPassword(), jr.toString()));
		RoleDTO role = new RoleDTO();
		role.setDate(new Date());
		role.setRoleName("ROLE_USER");
		user.setRoles(Arrays.asList(role));		
		String hash = encryptionService.encryptPassword(StringUtils.randomString());
		user.setHashKey(hash);
		UserDTO registeredUser = userService.addUser(user);
		emailService.sendConfirmationEmail(userReg.getEmail(), hash);
		return registeredUser;
		
	}

	@Override
	public RegistrationDTO registerSocialUser(RegistrationDTO userReg) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Boolean verifyRecaptcha(String recaptcha) {
		client = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
    	headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    	MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
    	map.add("secret",config.getRecaptchaSecret());
    	map.add("response", recaptcha);
    	
    	
    	HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
		ResponseEntity<String> response = client.postForEntity(config.getRecaptchaValidationUrl(), request, String.class);
		String responseBody = response.getBody();
        logger.debug("ResponseBody:{}",responseBody);
        JSONParser parser = new JSONParser(JSONParser.MODE_PERMISSIVE);
        JSONObject jsonObject = null;
		try {
			jsonObject = (JSONObject) parser.parse(response.getBody());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        logger.debug("StatusCode:{},Message:{},{},{}",response.getStatusCode(),response.getStatusCodeValue(),jsonObject);
        return Boolean.parseBoolean(jsonObject.getAsString("success"));
	}

    @Override
    public Boolean verifyActivationEmail(String hash, String email) {
        UserDTO user = userService.getUserByEmail(email);
        if(!user.getHashKey().equals(hash)) 
            return false;
        return true;
    } 
    
    private Boolean checkUserExists(UserDTO user) {
        if(user != null)
            return true;
        return false;
    }
    
    private Boolean isSocialProviderUser(UserDTO user) {
        if(user.getSocialProvider()!= null)
            return true;
        return false;
    }

}
