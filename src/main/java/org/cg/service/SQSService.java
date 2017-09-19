package org.cg.service;

import com.amazonaws.services.sqs.AmazonSQSAsync;

public interface SQSService {

   public void getMessage();
   
   public void send();
    
}
