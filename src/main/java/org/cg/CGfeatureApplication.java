package org.cg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableAutoConfiguration
@EnableWebMvc
@ComponentScan("org.cg.config")
public class CGfeatureApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(CGfeatureApplication.class, args);
	}
	
	 @Override
	    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
	        //Define different profiles to enable/disable features.
		 	
	        return application.sources(CGfeatureApplication.class);
	    }

	    private static Class<CGfeatureApplication> applicationClass = CGfeatureApplication.class;
	    
	    @Bean
	    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
	        return new PropertySourcesPlaceholderConfigurer();
	    }
}



   

