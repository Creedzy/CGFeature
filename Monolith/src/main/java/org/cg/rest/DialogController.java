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
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;

@Controller
public class DialogController {
	Logger logger = LoggerFactory.getLogger(DialogController.class);

	
	@CrossOrigin(origins = "http://stest.com:8080/")
	@RequestMapping("/")
	public String index() {

		return "index";
	}
	
	 @RequestMapping("/login")
	    public String login() {
	         Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	         logger.debug("security:{},{},{}",auth,auth.isAuthenticated(),auth.getClass());
	         if(SecurityContextHolder.getContext().getAuthentication() == null || !SecurityContextHolder.getContext().getAuthentication().isAuthenticated()
	                        || SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken) {
	             return "apps/login/index";
	         }
	         return "redirect:/";
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

	@RequestMapping(value = { "/error" }, method = RequestMethod.GET)
	public String error() {
		return "404";

	}

}
