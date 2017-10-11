package org.cg.config;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.cg.config.Profiles.Dev;
import org.cg.config.Profiles.Prod;
import org.cg.config.Profiles.Web;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

@Configuration
@EnableWebMvc
@Web
@ComponentScan(basePackages= {"org.cg.rest"
}, useDefaultFilters=false, includeFilters={@Filter(org.springframework.stereotype.Controller.class)})
public class WebConfig extends WebMvcConfigurerAdapter {
	Logger logger = LoggerFactory.getLogger(WebConfig.class);
	private String basePath = "dist";
	
	
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
		
		
        registry.addResourceHandler("/resources/**").addResourceLocations("file:"+basePath + "/resources/");
        registry.addResourceHandler("/apps/**/**").addResourceLocations("file:"+basePath + "/apps/");
        registry.addResourceHandler("/404").addResourceLocations("file:"+basePath + "/");
       // registry.addResourceHandler("/*.html").addResourceLocations(array);
        registry.addResourceHandler("/index.html").addResourceLocations("file:"+basePath + "/");
        registry.addResourceHandler("/bower_components/**").addResourceLocations("file:"+basePath + "/bower_components/");
        registry.addResourceHandler("/scripts/**").addResourceLocations("file:"+basePath + "/scripts/");
        registry.addResourceHandler("/js/**").addResourceLocations("file:"+basePath + "/js/");
        registry.addResourceHandler("/styles/**").addResourceLocations("file:"+basePath + "/styles/");
        registry.addResourceHandler("/views/**").addResourceLocations("file:"+basePath + "/views/").resourceChain(false);
       
    }
	
	@Bean  
    public UrlBasedViewResolver setupViewResolver() {  
        UrlBasedViewResolver resolver = new UrlBasedViewResolver();  
        resolver.setPrefix("/");  
        resolver.setSuffix(".html");  
        resolver.setViewClass(JstlView.class);  
        return resolver;  
    }  
	
	@Bean
    public SimpleMappingExceptionResolver exceptionResolver() {
        SimpleMappingExceptionResolver exceptionResolver = new SimpleMappingExceptionResolver();
 
        Properties exceptionMappings = new Properties();
 
        exceptionMappings.put("java.lang.Exception", "error/error");
        exceptionMappings.put("java.lang.RuntimeException", "error/error");
 
        exceptionResolver.setExceptionMappings(exceptionMappings);
 
        Properties statusCodes = new Properties();
 
        statusCodes.put("error/404", "404");
        statusCodes.put("error/error", "500");
 
        exceptionResolver.setStatusCodes(statusCodes);
 
        return exceptionResolver;
    }
	
	public  void iterate(Path loc,List<String> dirs) {
		DirectoryStream.Filter<Path> filter = new DirectoryStream.Filter<Path>() {
	        @Override
	        public boolean accept(Path file) throws IOException {
	            return (Files.isDirectory(file));
	        }
	    };

	   
	    try (DirectoryStream<Path> stream = Files.newDirectoryStream(loc, filter)) {
	        for (Path path : stream) {
	            // Iterate over the paths in the directory and print filenames
	           // System.out.println( path.toAbsolutePath());
	            dirs.add(path.toAbsolutePath().toString());
	            iterate(path,dirs);
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
    }
}
