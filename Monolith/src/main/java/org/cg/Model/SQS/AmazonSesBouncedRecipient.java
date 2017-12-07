package org.cg.Model.SQS;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
class AmazonSesBouncedRecipient {
    public String EmailAddress;

    public String getEmailAddress() {
        return EmailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        EmailAddress = emailAddress;
    }
    
    @Override
    public String toString() {
        return "emaiL: " + EmailAddress;
    }
}