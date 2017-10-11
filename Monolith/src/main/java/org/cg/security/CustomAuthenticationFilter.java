package org.cg.security;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.PortResolver;
import org.springframework.security.web.PortResolverImpl;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

import com.google.api.client.util.Strings;
import com.nimbusds.oauth2.sdk.ParseException;
import com.nimbusds.oauth2.sdk.token.AccessToken;
import com.nimbusds.openid.connect.sdk.token.OIDCTokens;






public class CustomAuthenticationFilter  extends AbstractAuthenticationProcessingFilter {
    public static Logger logger = LoggerFactory.getLogger(CustomAuthenticationFilter.class);

    String provider;
    
    @Autowired
    private  SocialSignInService authenticationService;

    public CustomAuthenticationFilter(String string) {
        super(string);
    }

    public CustomAuthenticationFilter(RequestMatcher rm) {
        super(rm);
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
            HttpServletResponse response) throws AuthenticationException,
            IOException, ServletException {
        logger.debug("Attempting authentication.");
        Authentication auth;
        
        auth = authenticationService.getAuthentication(request,response);
        if( auth !=null)
        {
            return auth;
        }
        if (!Strings.isNullOrEmpty(request.getParameter("error"))) {

            handleError(request, response);
            return null; // no auth, response is sent to display page or something

        } else if (!Strings.isNullOrEmpty(request.getParameter("code"))) {
            logger.debug("Received code in request. Going to handle code response.");
            //  got back the code, need to process this to get our tokens
             auth = handleAuthorizationCodeResponse(request, response);
            return auth;

        } else {
            // not an error, not a code, must be an initial login of some type
            try {
                logger.debug("No code in request, going to attempt an authentication request.");
                
                authenticationService.sendRedirectRequest(request, response,extractProvider(request));
            } catch (URISyntaxException e) {
                logger.debug("Error:{}",e);
            }
            return null; // no auth, response redirected to the server's Auth Endpoint
        }

    }



    private void handleError(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.debug("Request returned with error. {}",request.getParameter("error"));
        response.sendError(401, request.getParameter("error"));

    }

    private Authentication handleAuthorizationCodeResponse(
            HttpServletRequest request, HttpServletResponse response) {
    	AccessToken tokens = null;
        Authentication auth = null;
        try {
        	 HttpSession session = request.getSession();
        	
             tokens  = authenticationService.getTokens(request);
             auth = authenticationService.buildAuthFromTokens(tokens,(String) session.getAttribute("provider"));
             logger.debug("Saving in session.");            
             session.setAttribute("session_bearer_token", tokens.getValue());
             DefaultSavedRequest redirect = new DefaultSavedRequest(request, new PortResolverImpl());
             logger.debug("saved request:{}",redirect.getRedirectUrl());

        } catch (ParseException | URISyntaxException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return auth;
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
    	logger.debug("setting Authentication");
        SecurityContextHolder.getContext().setAuthentication(authResult);       
        getAuthenticationManager().authenticate(authResult);       
        this.getSuccessHandler().onAuthenticationSuccess(request, response, authResult);        
    }
    
    
    
    String extractProvider(HttpServletRequest request){
    	String provider = request.getParameter("provider");
    	logger.debug("Provider:{}",provider);
    	return provider;
    }

}