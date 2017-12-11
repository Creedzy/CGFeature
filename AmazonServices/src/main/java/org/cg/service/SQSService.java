package org.cg.service;


import org.cg.common.model.SQS.AmazonSesComplaintNotification;
import org.cg.common.model.SQS.AmazonSqsNotification;
import org.springframework.messaging.Message;



public interface SQSService {
  

   public AmazonSqsNotification checkBounceQueue();

   public AmazonSesComplaintNotification checkComplaintQueue();

   void send(String queue, String payload);
    
}
