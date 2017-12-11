package org.cg.common.model.SQS;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AmazonSesBounceNotification {
    public String notificationType;

    public AmazonSesBounce bounce;
    
    @JsonCreator
    public AmazonSesBounceNotification(@JsonProperty("notificationType") String notType,@JsonProperty("bounce") AmazonSesBounce bnc) {
        this.notificationType = notType;
        this.bounce = bnc;
    }
    
    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notType) {
        notificationType = notType;
    }

    public AmazonSesBounce getBounce() {
        return bounce;
    }

    public void setBounce(AmazonSesBounce Bounce) {
        bounce = Bounce;
    }
    
    @Override
    public String toString() {
        return "AmazonSesBounceNotification[ notificationType:" + notificationType + " ,bounce:" + bounce + "]";
    }
}