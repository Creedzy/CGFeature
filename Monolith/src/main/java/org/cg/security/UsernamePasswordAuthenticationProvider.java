package org.cg.security;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;

import org.cg.Model.dto.RoleDTO;
import org.cg.Model.dto.UserDTO;
import org.cg.service.EncryptionService;
import org.cg.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

@Component
public class UsernamePasswordAuthenticationProvider implements AuthenticationProvider {
    Logger logger = LoggerFactory.getLogger(UsernamePasswordAuthenticationProvider.class);

    @Autowired
    UserService userService;

    @Autowired
    EncryptionService encryptionService;

    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {
        String username = auth.getName();
        String password = auth.getCredentials().toString();
        logger.debug("username: {}, password:{}", username, password);
        // Find user
        UserDTO user = userService.getUserByEmail(username);
        if (user != null) {
            
            if (isPasswordValid(user, password)) {
                if(!user.isActivated()) {
                    throw new BadCredentialsException("user.not.activated");
                }                
                return new UsernamePasswordAuthenticationToken(username, password, resolveAuthorities(user));
            }
            
            throw new BadCredentialsException("External system authentication failed");

        }
        else {
            throw new BadCredentialsException("user.not.found");
        }

    }

    @Override
    public boolean supports(Class<?> auth) {
        return auth.equals(UsernamePasswordAuthenticationToken.class);
    }   
    
    private boolean isPasswordValid(UserDTO user, String password) {
        StringJoiner hash = new StringJoiner("");
        hash.add(user.getEmail());
        hash.add(user.getFirstName());
        hash.add(user.getUsername());
        if (encryptionService.decodeHash(user.getPassword(), hash.toString()).equals(password)) {
            return true;
        }
        return false;
    }

    private List<GrantedAuthority> resolveAuthorities(UserDTO user) {
        List<GrantedAuthority> grantedAuthorities = null;
        if (user.getRoles() != null && !user.getRoles().isEmpty()) {
            List<RoleDTO> roles = user.getRoles();
            List<String> roleNames = new ArrayList<String>();
            for (RoleDTO role : roles) {
                roleNames.add(role.getRoleName());
            }
            logger.debug("Adding roles:{},", roleNames);
            grantedAuthorities = AuthorityUtils.createAuthorityList(roleNames.stream().toArray(String[]::new));
        }
        else {
            logger.debug("No rolenames for user. Adding default User role.");
            grantedAuthorities = AuthorityUtils.createAuthorityList("ROLE_USER");
        }
        logger.debug("authorities: {}", grantedAuthorities);
        return grantedAuthorities;
    }

}
