package org.cg.service;


import org.cg.Model.SQS.AmazonSesComplaintNotification;
import org.cg.Model.SQS.AmazonSqsNotification;
import org.springframework.messaging.Message;



public interface SQSService {
  

   public AmazonSqsNotification checkBounceQueue();

   public AmazonSesComplaintNotification checkComplaintQueue();

   void send(String queue, String payload);
    
}
