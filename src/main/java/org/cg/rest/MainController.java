package org.cg.rest;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.cg.Model.dto.RegistrationDTO;
import org.cg.service.RegistrationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;

@Controller
public class MainController {
	Logger logger = LoggerFactory.getLogger(MainController.class);
	@Autowired
	RegistrationService regService;

	@RequestMapping("/")
	public String index() {

		return "index";
	}
	
	 @RequestMapping("/login")
	    public String login() {
	    	return "apps/login/index";
	    }
	 
	 @RequestMapping("/logout")
	 public String logout(HttpServletRequest request,HttpServletResponse response) {
		HttpSession session = request.getSession();
		session.invalidate();
		logger.debug("Session ivalidated");
		return "apps/login/index";
	 }
	 

	@RequestMapping("/register")
	public String registrationPage() {
		return "apps/registration/index";
	}

	@RequestMapping(value = "/registration", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> registration(@RequestBody RegistrationDTO newUser) {

		logger.debug("Reg user:{},resultt: {}", newUser);
		if (!regService.verifyRecaptcha(newUser.getMyRecaptchaResponse())) {
			return new ResponseEntity<>("fail", HttpStatus.BAD_REQUEST);
		}
		regService.registerUser(newUser);
		return new ResponseEntity<>("success", HttpStatus.OK);
	}

	@RequestMapping(value = { "/error" }, method = RequestMethod.GET)
	public String error() {
		return "404";

	}

}
