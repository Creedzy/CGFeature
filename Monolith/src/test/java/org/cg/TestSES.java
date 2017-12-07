package org.cg;

import java.util.List;

import javax.sql.DataSource;

import org.cg.Model.SQS.AmazonSesComplaintNotification;
import org.cg.Model.SQS.AmazonSqsNotification;
import org.cg.config.AmazonConfigs;
import org.cg.config.ApplicationConfig;
import org.cg.config.DatabaseConfig;
import org.cg.config.SecurityConfig;
import org.cg.service.S3Service;
import org.cg.service.SESService;
import org.cg.service.SQSService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.messaging.Message;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import testconfigs.AmazonTestConfig;

import com.amazonaws.services.s3.model.Bucket;

@RunWith(SpringRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes={AmazonTestConfig.class})
@ActiveProfiles("prod")
public class TestSES {
    Logger logger = LoggerFactory.getLogger(TestSES.class);
    @Autowired
    SESService mailing;
    
    @Autowired
    SQSService sqs;
    
    @Autowired
    S3Service s3;
    
    @Autowired
    ApplicationContext context;
    
    
    @Before
    public void init() {
        
    } 
    
    @Test
    public void testMail() {
        mailing.sendConfirmationEmail("astralrap@gmail.com","123");
    }
    
    @Test
    public void testBounce() {
        mailing.sendConfirmationEmail("bounce@simulator.amazonses.com","hash");
       AmazonSqsNotification messages = sqs.checkBounceQueue();
       logger.debug("Messages:{}",messages);
    }
    
    @Test
    public void testComplaint() {
        mailing.sendConfirmationEmail("complaint@simulator.amazonses.com","hash");
        AmazonSesComplaintNotification messages = sqs.checkComplaintQueue();
       logger.debug("Messages:{}",messages);
    }
    
    @Test
    public void testS3() {
        List<Bucket> buckets = s3.listBuckets();
        logger.debug("Buckets:{}",buckets);
    }
}
