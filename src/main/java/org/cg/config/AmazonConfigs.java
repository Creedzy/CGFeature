package org.cg.config;

import org.cg.service.SESService;
import org.cg.service.SQSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.mail.simplemail.SimpleEmailServiceJavaMailSender;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClient;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClient;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;

@Configuration
@ComponentScan(
                basePackageClasses = {SESService.class,SQSService.class,ConfigurationService.class}, 
                useDefaultFilters = false,
                includeFilters = {
                    @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = {SESService.class,SQSService.class,ConfigurationService.class})
                })
@PropertySource(value = { "classpath:store.properties" })
@ImportResource("classpath:awsconfig.xml")
public class AmazonConfigs {
    
    @Value("${access.key}")
    private String accessKey;
    
    @Value("${secret.key}")
    private String secretKey;
    
    @Bean 
    JavaMailSender javaMailSender(AmazonSimpleEmailService aMSES) {
        return new SimpleEmailServiceJavaMailSender(aMSES);
    }
    
    @Bean
    AmazonSimpleEmailService amazonSESService(BasicAWSCredentials awsCredentials ) {
        AmazonSimpleEmailService amazonSimpleEmailService =  AmazonSimpleEmailServiceClientBuilder
                        .standard()
                        .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                        .withRegion(Regions.EU_WEST_1)
                        .build();
    
        return amazonSimpleEmailService;
    }
    
   @Bean
   AmazonSQSAsync amazonSQS(BasicAWSCredentials awsCredentials) {
       AmazonSQSAsync client = AmazonSQSAsyncClientBuilder
                       .standard()
                       .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                       .withRegion(Regions.EU_WEST_1)
                       .build();
       return client;
   }
   
   @Bean
   QueueMessagingTemplate queueMessagingTemplate(AmazonSQSAsync aMSQS) {
       QueueMessagingTemplate template = new QueueMessagingTemplate(aMSQS);
       return template;
   }
    
   @Bean
   BasicAWSCredentials awsCredentials() {
       BasicAWSCredentials auth = new BasicAWSCredentials(accessKey, secretKey);
       return auth;
   }
    
}
