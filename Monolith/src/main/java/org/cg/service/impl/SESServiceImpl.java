package org.cg.service.impl;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.cg.config.ConfigurationService;
import org.cg.service.SESService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class SESServiceImpl implements SESService{
    Logger logger = LoggerFactory.getLogger(SESServiceImpl.class);
    MailSender mailSender;
    
    @Autowired
    ConfigurationService config;
    
    @Autowired
    public SESServiceImpl(MailSender mailSender) {
        this.mailSender = mailSender;
    }
    
    @Override
    public void sendConfirmationEmail(String recipient,String hash) {
        SimpleMailMessage message = new SimpleMailMessage();
        String host = null;
        try {
            host = InetAddress.getLocalHost().getHostName();
        }
        catch (UnknownHostException e) {
          logger.error("Unknown host");
        }
        String  body = "Your account has been created. Please activate it by following the link below:\n"
                            +  host +"/activation?key=" + hash + "&email=" + recipient;

        message.setFrom(config.getSesSenderEmail());
        message.setTo(recipient);
        message.setSubject("Email Confirmation");
        message.setText(body);
        this.mailSender.send(message);        
    }

}
