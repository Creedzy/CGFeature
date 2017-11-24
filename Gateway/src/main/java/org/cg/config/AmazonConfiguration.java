package org.cg.config;


import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.mail.simplemail.SimpleEmailServiceJavaMailSender;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class AmazonConfiguration {
    Logger logger = LoggerFactory.getLogger(AmazonConfiguration .class);
    
    ApplicationContext context;
    ApplicationProperties properties;
    
    @Autowired
    public AmazonConfiguration(ApplicationContext ctx,ApplicationProperties properties) {
        this.context = ctx;
        this.properties = properties;
    }
    
    public AmazonConfiguration() {
        
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
   BasicAWSCredentials awsCredentials(Environment env) {
       //logger.debug("properties:{}",getAllKnownProperties(env));
       BasicAWSCredentials auth = new BasicAWSCredentials(env.getProperty("application.aws.access-key"), env.getProperty("application.aws.secret-key"));
       return auth;
   }
   
   public static Map<String, Object> getAllKnownProperties(Environment env) {
       Map<String, Object> rtn = new HashMap<>();
       if (env instanceof ConfigurableEnvironment) {
           for (PropertySource<?> propertySource : ((ConfigurableEnvironment) env).getPropertySources()) {
               if (propertySource instanceof EnumerablePropertySource) {
                   for (String key : ((EnumerablePropertySource) propertySource).getPropertyNames()) {
                       rtn.put(key, propertySource.getProperty(key));
                   }
               }
           }
       }
       return rtn;
   }
}
