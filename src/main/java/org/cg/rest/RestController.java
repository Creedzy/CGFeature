package org.cg.rest;

import org.cg.Model.dto.RegistrationDTO;
import org.cg.service.RegistrationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RestController {
    Logger logger = LoggerFactory.getLogger(RestController.class);
    
    @Autowired
    RegistrationService regService;
    
    @RequestMapping(value = "/registration", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> registration(@RequestBody RegistrationDTO newUser) {

        logger.debug("Reg user:{},resultt: {}", newUser);
        if (!regService.verifyRecaptcha(newUser.getMyRecaptchaResponse())) {
            return new ResponseEntity<>("fail", HttpStatus.BAD_REQUEST);
        }
        regService.registerUser(newUser);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }
    
    @RequestMapping(value = "/activation", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> accountActivation(@RequestParam(value="key",required=true) String hash, @RequestParam(value ="email",required=true) String email) {
        if(regService.verifyActivationEmail(hash, email))
            return new ResponseEntity<String>("success",HttpStatus.OK);
        return new ResponseEntity<String>("fail",HttpStatus.BAD_REQUEST);
        
        
    }
    
   // @RequestMapping(value="/login/auth",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
  //  public ResponseEntity<String> userLogin(@RequestBody LoginValues values) {
   //     
    //}
    
   
    
}


