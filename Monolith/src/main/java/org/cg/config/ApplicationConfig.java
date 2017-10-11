package org.cg.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;




@Configuration
@ComponentScan({"org.cg.repository","org.cg.Model","org.cg.security","org.cg.service","org.cg.rest"})
@PropertySource(value={"classpath:store.properties"})
public class ApplicationConfig {
	
    @Bean
    public static PropertySourcesPlaceholderConfigurer
      propertySourcesPlaceholderConfigurer() {
       return new PropertySourcesPlaceholderConfigurer();
    }
    
	
}
