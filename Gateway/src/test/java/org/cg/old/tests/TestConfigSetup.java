package org.cg.old.tests;

import org.cg.config.ApplicationConfig;
import org.cg.config.DatabaseConfig;
import org.cg.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

//@RunWith(SpringRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes={DatabaseConfig.class,ApplicationConfig.class})
@ActiveProfiles({"web","prod"})
public class TestConfigSetup {

	
	
	@Test
	public void test(){
		
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.getEnvironment().addActiveProfile("prod");
		ctx.register(ApplicationConfig.class,DatabaseConfig.class);
		ctx.refresh();
		System.out.println(ctx.getBean(UserRepository.class));
	}
}
