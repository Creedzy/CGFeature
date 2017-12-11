package org.cg.service.impl;



import org.cg.common.model.SQS.AmazonSesComplaintNotification;
import org.cg.common.model.SQS.AmazonSqsNotification;
import org.cg.service.SQSService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSAsync;


@Service
public class SQSServiceImpl implements SQSService {
    Logger logger = LoggerFactory.getLogger(SQSServiceImpl.class);
    private final QueueMessagingTemplate queueMessagingTemplate;

    @Autowired
    public SQSServiceImpl(AmazonSQSAsync amazonSqs) {
        this.queueMessagingTemplate = new QueueMessagingTemplate(amazonSqs);
    }
    
    @Override
    public void send(String queue,String payload) {
        this.queueMessagingTemplate.send(queue, MessageBuilder.withPayload(payload).build());
    }
    
    @Override
    public AmazonSqsNotification checkBounceQueue() {
        AmazonSqsNotification message = this.queueMessagingTemplate.receiveAndConvert("ses-bounces-queue", AmazonSqsNotification.class);
        logger.debug("Messages:{}",message);
        return message;
        
    }
    
    @Override
    public AmazonSesComplaintNotification checkComplaintQueue() {
        AmazonSesComplaintNotification message = this.queueMessagingTemplate.receiveAndConvert("ses-complaints-queue", AmazonSesComplaintNotification.class);
        logger.debug("Messages:{}",message);
        return message;
    }

}
