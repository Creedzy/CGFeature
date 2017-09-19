package org.cg.service.impl;

import org.cg.service.SQSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSAsync;

@Service
public class SQSServiceImpl implements SQSService {
    
    private final QueueMessagingTemplate queueMessagingTemplate;

    @Autowired
    public SQSServiceImpl(AmazonSQSAsync amazonSqs) {
        this.queueMessagingTemplate = new QueueMessagingTemplate(amazonSqs);
    }
    
    @Override
    public void send() {
        this.queueMessagingTemplate.send("ses-bounces-queue", MessageBuilder.withPayload("aa").build());
    }
    
    @Override
    public void getMessage() {
        // TODO Auto-generated method stub
        
    }

}
