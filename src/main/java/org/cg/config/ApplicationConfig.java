package org.cg.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;




@Configuration
@ComponentScan({"org.cg.repository","org.cg.config","org.cg.Model","org.cg.security","org.cg.service","org.cg.rest"})
public class ApplicationConfig {
	
	
	
}
