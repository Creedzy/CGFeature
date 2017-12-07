package org.cg.service;

import java.net.UnknownHostException;


public interface SESService {
	

    public void sendConfirmationEmail(String recipient, String hash);

}
