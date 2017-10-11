package org.cg.security;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cg.rest.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    Logger logger = LoggerFactory.getLogger(CustomUsernamePasswordAuthenticationFilter.class);
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,HttpServletResponse response) {
        if(!request.getMethod().equalsIgnoreCase("POST")) {
            throw new AuthenticationServiceException("Authenticaiton method not supported:" + request.getMethod());
        }     
        LoginValues login = this.getLoginRequest(request);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(login.getUsername(),login.getPassword());
        setDetails(request,token);
        
        return this.getAuthenticationManager().authenticate(token);
        
    }
    
    
    private LoginValues getLoginRequest(HttpServletRequest request) {
        BufferedReader reader = null;
        ServletInputStream stream = null;
        LoginValues loginRequest = null;
        try {
            stream = request.getInputStream();
            ObjectMapper mapper = new ObjectMapper();
            loginRequest = mapper.readValue(stream,LoginValues.class);
        } catch (IOException ex) {
            logger.error("{}",ex);
        } finally {
            try {
                stream.close();
            } catch (IOException ex) {
                logger.error("{}",ex);
            }
        }

        if (loginRequest == null) {
            loginRequest = new LoginValues();
        }

        return loginRequest;
    }
}

class LoginValues {
    
    String username;
    String password;
    
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    
    @Override
    public String toString() {
        return "Username:" + username + "Password:{}" + password;
    }
}