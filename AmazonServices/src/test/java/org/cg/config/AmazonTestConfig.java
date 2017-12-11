package org.cg.config;

import org.cg.service.S3Service;
import org.cg.service.SESService;
import org.cg.service.SQSService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.mail.simplemail.SimpleEmailServiceJavaMailSender;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClient;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClient;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/*@Configuration
@ComponentScan(
                basePackageClasses = {SESService.class,SQSService.class,S3Service.class}, 
                useDefaultFilters = false,
                includeFilters = {
                    @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = {SESService.class,SQSService.class,S3Service.class})
                })
                */
public class AmazonTestConfig {
    Logger logger = LoggerFactory.getLogger(AmazonTestConfig.class);
    

    @Autowired
    ApplicationContext ctx;
    
    
    
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
       ObjectMapper mapper = new ObjectMapper();
       mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
       //mapper.enable(Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER);
       mapper.enable(Feature.ALLOW_SINGLE_QUOTES);
       mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
       MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
       converter.setObjectMapper(mapper);
       template.setMessageConverter(converter);
       return template;
   }
   
   @Bean
   AmazonS3 amazonS3(BasicAWSCredentials awsCredentials) {
       AmazonS3 s3 = AmazonS3ClientBuilder
                       .standard()
                       .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                       .withRegion(Regions.EU_WEST_1)
                       .build();
       return s3;
   }   
    
   @Bean
   BasicAWSCredentials awsCredentials(Environment env) {
	   BasicAWSCredentials auth = new BasicAWSCredentials(env.getProperty("application.aws.access-key"), env.getProperty("application.aws.secret-key"));
       return auth;
   }
    
}
