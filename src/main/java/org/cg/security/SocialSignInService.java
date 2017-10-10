package org.cg.security;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;

import org.cg.Model.SocialMediaService;
import org.cg.Model.dto.UserDTO;
import org.cg.config.ConfigurationService;
import org.cg.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport.Builder;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.nimbusds.oauth2.sdk.ParseException;
import com.nimbusds.oauth2.sdk.id.State;
import com.nimbusds.oauth2.sdk.token.AccessToken;
import com.nimbusds.openid.connect.sdk.Nonce;
import com.nimbusds.openid.connect.sdk.token.OIDCTokens;

@Service
@Component("authenticationService")
public class SocialSignInService implements AuthenticationProvider {

    public static Logger logger = LoggerFactory.getLogger(SocialSignInService.class);

    @Autowired
    ConfigurationService globalProps;

    @Autowired
    UserService userService;

    public final static String NONCE_SESSION_VARIABLE = "nonce";

    private final JsonFactory factory;

    private HttpTransport HTTP_TRANSPORT;

    private HttpRequestFactory requestFactory;

    ObjectMapper objectMapper;

    private State state;

    private Nonce nonce;

    private static Map<String, User> userCache = new ConcurrentHashMap<String, User>();

    public static Map<String, User> getUserCache() {
        return userCache;
    }

    public SocialSignInService() throws GeneralSecurityException, IOException {
        objectMapper = new ObjectMapper();
        Builder builder = new NetHttpTransport.Builder();
        builder.doNotValidateCertificate();
        HTTP_TRANSPORT = builder.build();
        this.requestFactory = HTTP_TRANSPORT.createRequestFactory();
        factory = JacksonFactory.getDefaultInstance();
    }

    public Authentication getAuthentication(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession();
        final String token = (String) session.getAttribute("session_bearer_token");
        logger.info("TOKEN: " + token);

        if (token != null) {
            User user = userCache.get(token);
            if (user != null) {
                return new UserAuthentication(user);
            }
        }
        logger.debug("No user in cache. Prepare for authentication.");
        return null;
    }

    public void sendRedirectRequest(HttpServletRequest request, HttpServletResponse response, String provider)
                    throws URISyntaxException {
        HttpSession session = request.getSession();
        session.setAttribute("provider", provider);
        State state = new State();
        this.state = state;
        session.setAttribute("state", state);
        try {
            String authEndpoint = buildRedirectUrl(provider);

            logger.debug("Sending redirect request to:{}", authEndpoint);

            response.sendRedirect(authEndpoint);

            logger.debug(" Redirecting request...");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public Authentication buildAuthFromTokens(AccessToken tokens, String provider) throws Exception {

        String userInfoUrl = null;
        User result = new User();
        RestTemplate client = new RestTemplate();
        JSONParser parser = new JSONParser(JSONParser.MODE_PERMISSIVE);

        if (provider.equalsIgnoreCase("google")) {
            userInfoUrl = globalProps.getGoogleUserInfoUrl();
            logger.debug("Getting userInfo from URL:{}", userInfoUrl);
            HttpHeaders header = new HttpHeaders();
            header.setContentType(MediaType.APPLICATION_JSON);
            header.add("Authorization", "Bearer " + tokens.getValue());
            HttpEntity<String> headers = new HttpEntity<String>(header);
            ResponseEntity<String> response = client.exchange(userInfoUrl, HttpMethod.GET, headers, String.class);
            String responseBody = response.getBody();
            logger.debug("ResponseBody:{}", responseBody);
            JSONObject jsonObject = (JSONObject) parser.parse(response.getBody());

            List<GrantedAuthority> grantedAuthorities = null;
            grantedAuthorities = AuthorityUtils.createAuthorityList("ROLE_USER");
            result.setAuthorities(grantedAuthorities);
            result.setUserId(jsonObject.getAsString("id"));
            result.setEmail(jsonObject.getAsString("email"));
            result.setFirstname(jsonObject.getAsString("given_name"));
            result.setLastname(jsonObject.getAsString("family_name"));
            result.setUsername(jsonObject.getAsString("email"));
        }
        else if (provider.equalsIgnoreCase("facebook")) {
            StringBuilder sb = new StringBuilder();
            sb.append(globalProps.getFacebookGraphUrl());
            sb.append("/v2.9/me?fields=id,email,first_name,last_name");
            sb.append("&access_token=" + tokens.getValue());
            userInfoUrl = sb.toString();
            logger.debug("Getting userInfo from URL:{}", userInfoUrl);
            ResponseEntity<String> response = client.getForEntity(userInfoUrl, String.class);
            String responseBody = response.getBody();
            logger.debug("ResponseBody:{}", responseBody);
            JSONObject jsonObject = (JSONObject) parser.parse(response.getBody());

            List<GrantedAuthority> grantedAuthorities = null;
            grantedAuthorities = AuthorityUtils.createAuthorityList("ROLE_USER");
            result.setAuthorities(grantedAuthorities);
            result.setUserId(jsonObject.getAsString("id"));
            result.setEmail(jsonObject.getAsString("email"));
            result.setFirstname(jsonObject.getAsString("first_name"));
            result.setLastname(jsonObject.getAsString("last_name"));
            result.setUsername(jsonObject.getAsString("email"));
        }
        //
        if (userService.getUserByEmail(result.getEmail()) == null) {
            UserDTO userToAdd = new UserDTO();
            userToAdd.setFirstName(result.getFirstname());
            userToAdd.setLastName(result.getLastname());
            userToAdd.setEmailActive(true); 
            userToAdd.setActivated(true);
            userToAdd.setEmail(result.getEmail());
            userToAdd.setSocialProvider(SocialMediaService.valueOf(provider.toUpperCase()));
            userService.addUser(userToAdd);
        }
        userCache.put(tokens.getValue(), result);
        logger.debug("Creating cache.");
        return new UserAuthentication(result);

    }

    public AccessToken getTokens(HttpServletRequest request) throws Exception {
        logger.debug("About to get ID and Access tokens ");
        logger.debug(request.getRequestURI());

        String provider = (String) request.getSession().getAttribute("provider");
        String authorizationCode = (request.getParameter("code"));

        logger.debug(provider);
        logger.debug("Getting authcode from request. Authorization Code: {}", authorizationCode);
        RequestObject tokenRequest = null;
        if (provider.equalsIgnoreCase("google")) {

            tokenRequest = new RequestObject(globalProps.getGoogleTokenUrl(), globalProps.getGoogleId(),
                            globalProps.getGoogleSecret(), globalProps.getRedirectUrl(), authorizationCode, provider);

        }
        if (provider.equalsIgnoreCase("facebook")) {
            tokenRequest = new RequestObject(globalProps.getFacebookTokenUrl(), globalProps.getFacebookId(),
                            globalProps.getFacebookSecret(), globalProps.getRedirectUrl(), authorizationCode, provider);
        }

        AccessToken responseTokens = sendTokenRequest(tokenRequest);

        if (!request.getParameter("state").equals(this.state.getValue())) {
            logger.debug("State from request ({}) does not match initial state ({}).", request.getParameter("state"), this.state);
            throw new AuthenticationServiceException("States do not match.");
        }

        if (responseTokens == null) {
            logger.debug("emtpy tokens");
            throw new Exception("Response tokens empty");
        }

        logger.debug("Returning token: {}", responseTokens);
        return responseTokens;
    }

    public AccessToken sendTokenRequest(RequestObject req) throws Exception {
        logger.debug("Preparing tokens request....");
        AccessToken token = null;
        try {
            /*
             * Generate the request
             */

            RestTemplate client = new RestTemplate();
            ResponseEntity<String> response = null;
            if (req.provider.equalsIgnoreCase("facebook")) {
                StringBuilder sb = new StringBuilder();
                sb.append(req.getUrl());
                sb.append("?client_id=" + req.getClientid());
                sb.append("&redirect_uri=" + req.getRedirect_uri());
                sb.append("&client_secret=" + req.client_secret);
                sb.append("&code=" + req.getClient_secret());

                URI tokenEndpoint = new URI(sb.toString());
                logger.debug("Sending request to:{}", tokenEndpoint);
                response = client.getForEntity(tokenEndpoint, String.class);
            }
            else if (req.getProvider().equalsIgnoreCase("google")) {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
                map.add("client_id", req.clientid);
                map.add("client_secret", req.client_secret);
                map.add("redirect_uri", req.redirect_uri);
                map.add("grant_type", "authorization_code");
                map.add("code", req.getCode());

                HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
                URI tokenEndpoint = new URI(req.getUrl());
                logger.debug("Sending request to:{}", tokenEndpoint);
                response = client.postForEntity(tokenEndpoint, request, String.class);
            }

            String responseBody = response.getBody();
            logger.debug("ResponseBody:{}", responseBody);
            JSONParser parser = new JSONParser(JSONParser.MODE_PERMISSIVE);

            JSONObject jsonOjbect = (JSONObject) parser.parse(response.getBody());

            logger.debug("StatusCode:{},Message:{},{},{}", response.getStatusCode(), response.getStatusCodeValue(), jsonOjbect);
            token = AccessToken.parse(jsonOjbect);

        }
        catch (net.minidev.json.parser.ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (HttpClientErrorException e) {
            logger.debug("Error response:{}", e.getResponseBodyAsString());
            throw new Exception(e);
        }
        return token;

    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        if (!supports(authentication.getClass())) {
            return null;
        }
        if (authentication.isAuthenticated()) {
            return authentication;
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UserAuthentication.class);
    }

    public boolean validate(OIDCTokens tokens, String client_secret) {
        boolean valid = false;
        JWT jwttoken = tokens.getIDToken();
        logger.debug("JWT:{},{},{}", jwttoken.toString(), jwttoken.getHeader().getAlgorithm(), jwttoken);

        try {
            JWSVerifier verifier = new MACVerifier(client_secret);
            SignedJWT signedJWT = (SignedJWT) jwttoken;
            signedJWT.verify(verifier);
            JWTClaimsSet claims = signedJWT.getJWTClaimsSet();
            logger.debug("Claims:{}", claims);

            // check expiration
            if (claims.getExpirationTime() == null) {
                throw new AuthenticationServiceException("Id Token does not have required expiration claim");
            }
            else {
                // it's not null, see if it's expired
                Date now = new Date(System.currentTimeMillis() - (300 * 1000));
                if (now.after(claims.getExpirationTime())) {
                    throw new AuthenticationServiceException("Id Token is expired: " + claims.getExpirationTime());
                }
            }

        }
        catch (JOSEException e) {
            logger.debug("Caught:{}", e);
        }
        catch (java.text.ParseException e) {
            logger.debug("Caught:{}", e);
        }
        catch (Exception e) {
            logger.debug("Caught:{}", e);
        }

        return valid;

    }

    public String buildRedirectUrl(String provider) {
        StringBuilder sb = new StringBuilder();
        if (provider.equalsIgnoreCase("facebook")) {
            sb.append(globalProps.getFacebookAccessCodeUrl());
            sb.append("?response_type=code&client_id=" + globalProps.getFacebookId());
            sb.append("&redirect_uri=" + globalProps.getRedirectUrl());
            sb.append("&state=" + state);
            sb.append("&scope=email,public_profile");

        }
        else {
            sb.append(globalProps.getGoogleAccessCodeUrl());
            sb.append("?response_type=code&client_id=" + globalProps.getGoogleId());
            sb.append("&redirect_uri=" + globalProps.getRedirectUrl());
            sb.append("&state=" + state);
            sb.append("&scope=profile+email");
        }
        System.out.println("redirect url" + sb.toString());
        return sb.toString();

    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Nonce getNonce() {
        return nonce;
    }

    public void setNonce(Nonce nonce) {
        this.nonce = nonce;
    }

    private class RequestObject {

        String url;

        String clientid;

        String client_secret;

        String redirect_uri;

        String code;

        String provider;

        public RequestObject(String url, String clientid, String clientsecret, String redirect_uri, String code, String provider) {
            this.url = url;
            this.clientid = clientid;
            this.client_secret = clientsecret;
            this.redirect_uri = redirect_uri;
            this.code = code;
            this.provider = provider;
        }

        public String getUrl() {
            return url;
        }

        public String getClientid() {
            return clientid;
        }

        public String getClient_secret() {
            return client_secret;
        }

        public String getRedirect_uri() {
            return redirect_uri;
        }

        public String getCode() {
            return code;
        }

        public String getProvider() {
            return provider;
        }
    }

}
