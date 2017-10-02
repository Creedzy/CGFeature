package org.cg.config;

import org.apache.http.client.RedirectStrategy;
import org.cg.config.Profiles.Prod;
import org.cg.repository.UserRepository;
import org.cg.security.CustomAuthenticationFilter;
import org.cg.security.CustomUsernamePasswordAuthenticationFilter;
import org.cg.security.SocialSignInService;
import org.cg.security.UsernamePasswordAuthenticationFailureHandler;
import org.cg.security.UsernamePasswordAuthenticationProvider;
import org.cg.security.UsernamePasswordAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;


@Configuration
@EnableWebSecurity
@Prod
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	SocialSignInService authenticationProvider;
	
	@Autowired
	UsernamePasswordAuthenticationProvider upAuthProvider;
	
	@Autowired
	UserRepository userRepository;
	
	 @Bean
	 protected AbstractAuthenticationProcessingFilter getAuthTokenFilter() throws Exception {

	        //CustomAuthenticationFilter authFilter = new CustomAuthenticationFilter(new RegexRequestMatcher("^/"+properties.getApplicationName()+".*", null));
	        CustomAuthenticationFilter authFilter = new CustomAuthenticationFilter("/auth");
	        authFilter.setAuthenticationManager(authenticationManagerBean());
	        return authFilter;
	}
	 
	@Bean
	AuthenticationSuccessHandler successHandler() {
	    UsernamePasswordAuthenticationSuccessHandler handler = new UsernamePasswordAuthenticationSuccessHandler();
	    DefaultRedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	    redirectStrategy.setContextRelative(true);
	    handler.setRedirectStrategy(redirectStrategy);
	    return handler;
	}
	
	@Bean
	AuthenticationFailureHandler failureHandler() {
	    UsernamePasswordAuthenticationFailureHandler handler = new UsernamePasswordAuthenticationFailureHandler();
	    return handler;
	}
	
	@Bean
	protected UsernamePasswordAuthenticationFilter usernamePasswordFilter() throws Exception {
	    CustomUsernamePasswordAuthenticationFilter filter = new CustomUsernamePasswordAuthenticationFilter();
	    filter.setAuthenticationManager(authenticationManagerBean());
	    filter.setAuthenticationSuccessHandler(successHandler());
	    filter.setFilterProcessesUrl("/login/authenticate");
	    filter.setAuthenticationFailureHandler(failureHandler());
	    return filter;
	}
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        	.addFilterAfter(getAuthTokenFilter(), SecurityContextPersistenceFilter.class)
        	.addFilterAt(usernamePasswordFilter(), UsernamePasswordAuthenticationFilter.class)
        	.csrf().disable()
             .formLogin()
             	.loginPage("/login")
             .and()
             	.logout()
             		.deleteCookies("JSESSIONID")
             		.logoutUrl("/logout")
                    .logoutSuccessUrl("/login")
                    .permitAll()
        .and()
        	.authorizeRequests()
        	.antMatchers( 
        			"/login","/404","/500",
        			"/resources/**","/scripts/**","/styles/**","/bower_components/**","/apps/**","/views/**","/js/**").permitAll()
        	.antMatchers("/auth/facebook","/auth/google","/signup/**","/register/**","/registration").permitAll()
        	.antMatchers("/","/**","/users").hasRole("USER");
        	
                      
    }
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
		.authenticationProvider(authenticationProvider);
		auth.authenticationProvider(upAuthProvider);
	}
	
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .inMemoryAuthentication()
            	.withUser("user")  // #1
            	.password("password")
            	.roles("USER")
            .and()
            	.withUser("admin") // #2
            	.password("password")
            	.roles("ADMIN","USER");
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }
 
   
}
