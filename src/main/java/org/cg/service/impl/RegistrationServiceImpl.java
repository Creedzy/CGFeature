package org.cg.service.impl;

import java.util.Arrays;
import java.util.Date;

import org.cg.Model.Role;
import org.cg.Model.User;
import org.cg.Model.dto.RegistrationDTO;
import org.cg.Model.dto.RoleDTO;
import org.cg.Model.dto.UserDTO;
import org.cg.config.ConfigurationService;
import org.cg.service.MasterRefService;
import org.cg.service.RegistrationService;
import org.cg.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.nimbusds.oauth2.sdk.token.AccessToken;

import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;

@Service
public class RegistrationServiceImpl implements RegistrationService{
	Logger logger = LoggerFactory.getLogger(RegistrationServiceImpl.class);
	
	@Autowired
	UserService userService;
	@Autowired
	MasterRefService masterRefService;
	@Autowired
	ConfigurationService config;
	RestTemplate client;
	
	@Override
	public UserDTO registerUser(RegistrationDTO userReg) {
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
		user.setPassword(userReg.getPassword());
		RoleDTO role = new RoleDTO();
		role.setDate(new Date());
		role.setRoleName("ROLE_USER");
		user.setRoles(Arrays.asList(role));
		UserDTO registeredUser = userService.addUser(user);
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

}
