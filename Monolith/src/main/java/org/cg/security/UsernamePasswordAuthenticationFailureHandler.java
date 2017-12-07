package org.cg.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

public class UsernamePasswordAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void
                    onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                    AuthenticationException exception) throws IOException, ServletException {
        AuthError error = new AuthError();
        error.setStatus(HttpStatus.UNAUTHORIZED);
      
        switch(exception.getMessage()) {
        case "user.not.found":
            error.setErrorCode(exception.getMessage());
            error.setDescription("Incorrect username or Password");
        case "user.not.activated":
            error.setErrorCode("user.not.activated");
            error.setDescription("User is not activated");
            default: 
                error.setErrorCode("authentication.error");
                error.setDescription("Failed to authenticate user");
        }
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(error);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(json);
        
    }
}

class AuthError {

    HttpStatus status;
    String errorCode;
    String description;
    
    public HttpStatus getStatus() {
        return status;
    }
    public void setStatus(HttpStatus error) {
        this.status = error;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getErrorCode() {
        return errorCode;
    }
    public void setErrorCode(String error) {
        this.errorCode = error;
    }
}
