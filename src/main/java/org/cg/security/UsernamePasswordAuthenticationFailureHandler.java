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
        error.setError(HttpStatus.UNAUTHORIZED);
        error.setDescription(exception.getMessage());
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(error);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(json);
        
    }
}

class AuthError {

    HttpStatus error;
    String description;
    
    public HttpStatus getError() {
        return error;
    }
    public void setError(HttpStatus error) {
        this.error = error;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
