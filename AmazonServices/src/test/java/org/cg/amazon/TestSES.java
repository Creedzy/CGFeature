package org.cg.amazon;

import static org.mockito.Mockito.when;

import java.util.List;

import javax.sql.DataSource;

import org.cg.AmazonServicesApp;
import org.cg.common.model.SQS.AmazonSesComplaintNotification;
import org.cg.common.model.SQS.AmazonSqsNotification;
import org.cg.config.AmazonTestConfig;
import org.cg.service.S3Service;
import org.cg.service.SESService;
import org.cg.service.SQSService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.env.Environment;
import org.springframework.messaging.Message;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.amazonaws.services.s3.model.Bucket;

import io.github.jhipster.config.JHipsterProperties;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AmazonServicesApp.class)
@ActiveProfiles("test")
@ImportResource("application.yml")
@ContextConfiguration(classes = AmazonTestConfig.class,
initializers = ConfigFileApplicationContextInitializer.class)
public class TestSES {
    Logger logger = LoggerFactory.getLogger(TestSES.class);
    
    @Mock
    private Environment environment;

    @Mock
    private JHipsterProperties jHipsterProperties;
    
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
    	MockitoAnnotations.initMocks(this);
        String mockProfile[] = {"test"};
        JHipsterProperties.Ribbon ribbon = new JHipsterProperties.Ribbon();
        ribbon.setDisplayOnActiveProfiles(mockProfile);
        when(jHipsterProperties.getRibbon()).thenReturn(ribbon);

        String activeProfiles[] = {"test"};
        when(environment.getDefaultProfiles()).thenReturn(activeProfiles);
        when(environment.getActiveProfiles()).thenReturn(activeProfiles);

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
