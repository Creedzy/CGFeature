package org.cg.service.impl;

import org.cg.config.ConfigurationService;
import org.cg.service.SESService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
public class SESServiceImpl implements SESService{

    MailSender mailSender;
    
    @Autowired
    ConfigurationService config;
    
    @Autowired
    public SESServiceImpl(MailSender mailSender) {
        this.mailSender = mailSender;
    }
    
    @Override
    public void sendConfirmationEmail() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(config.getSesSenderEmail());
        message.setTo("astralrap@gmail.com");
        message.setSubject("Email Confirmation");
        message.setText("Please confirm your email address by clicking on the following link.");
        this.mailSender.send(message);        
    }

}
