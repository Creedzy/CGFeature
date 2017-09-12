package org.cg;

import javax.sql.DataSource;

import org.cg.config.ApplicationConfig;
import org.cg.config.DatabaseConfig;
import org.cg.config.SecurityConfig;
import org.cg.service.MasterRefService;
import org.cg.service.MessageService;
import org.cg.service.NotificationService;
import org.cg.service.RequestService;
import org.cg.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes={DatabaseConfig.class,ApplicationConfig.class,SecurityConfig.class})
@ActiveProfiles("prod")
public class OtherTestts {

		
		
		@Autowired
		ApplicationContext context;
		
		@Autowired
		NotificationService notificationService;
		
		@Autowired
		MessageService messageService;
		
		@Autowired
		RequestService requestService;
		
		@Autowired
		MasterRefService masterRefService;
		
		@Autowired
		UserService userService;
		
		@Autowired
		DataSource dataSource;
		
		@Test
		public void testFilter() {
			
		}
			
	
}
