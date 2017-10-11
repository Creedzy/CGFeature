package org.cg.config;

import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

@Component
public class BootConfig
  implements EmbeddedServletContainerCustomizer {
  
    @Override
    public void customize(ConfigurableEmbeddedServletContainer container) {        
        container.addErrorPages(new ErrorPage(HttpStatus.BAD_REQUEST, "/400"));
        container.addErrorPages(new ErrorPage("/errorHeaven"));
    }
    
    @RequestMapping("/errorHeaven")
    String errorHeaven() {
        return "You have reached the heaven of errors!!!";
    }
    
    @Bean
    public PropertySourcesPlaceholderConfigurer propertyPlaceHolderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
