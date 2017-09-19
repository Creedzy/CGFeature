package org.cg;

import javax.sql.DataSource;

import org.cg.config.AmazonConfigs;
import org.cg.config.ApplicationConfig;
import org.cg.config.DatabaseConfig;
import org.cg.config.SecurityConfig;
import org.cg.service.SESService;
import org.cg.service.SQSService;
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
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes={AmazonConfigs.class})
@ActiveProfiles("prod")
public class TestSES {
    
    @Autowired
    SESService mailing;
    
    @Autowired
    SQSService sqs;
    
    @Autowired
    ApplicationContext context;
    
    
    @Before
    public void init() {
        
    } 
    
    @Test
    public void testMail() {
        mailing.sendConfirmationEmail();
    }
    
    @Test
    public void testSQS() {
        sqs.send();
    }
}
