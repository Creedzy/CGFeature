package org.cg.Model.SQS;

import java.io.IOException;
import java.util.List;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AmazonSesBounce {
    
    @JsonCreator
    public AmazonSesBounce(@JsonProperty("bounceType") String bType,
                           @JsonProperty("bounceSubType") String bSubType,
                           @JsonProperty("timestamp") String tstamp,
                           @JsonProperty("bouncedRecipients") List<AmazonSesBouncedRecipient> bRecipients) throws JsonParseException, JsonMappingException, IOException {
        
        this.bounceType = bType;
        this.bounceSubType = bSubType;
        this.timestamp = new DateTime(tstamp);
        ObjectMapper mapper = new ObjectMapper();
        this.bouncedRecipients = bRecipients;
        //bouncedRecipients = mapper.readValue(bRecipients,
          //              mapper.getTypeFactory().constructCollectionType(List.class, AmazonSesBouncedRecipient.class));
    }
    public String bounceType;

    public String bounceSubType;

    public DateTime timestamp;

    public List<AmazonSesBouncedRecipient> bouncedRecipients;

    public String getBounceType() {
        return bounceType;
    }

    public void setBounceType(String BounceType) {
        bounceType = BounceType;
    }

    public String getBounceSubType() {
        return bounceSubType;
    }

    public void setBounceSubType(String BounceSubType) {
        bounceSubType = BounceSubType;
    }

    public DateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(DateTime Timestamp) {
        timestamp = Timestamp;
    }

    public List<AmazonSesBouncedRecipient> getBouncedRecipients() {
        return bouncedRecipients;
    }

    public void setBouncedRecipients(List<AmazonSesBouncedRecipient> BouncedRecipients) {
        bouncedRecipients = BouncedRecipients;
    }
    
    @Override
    public String toString() {
        return "bounceType:" + bounceType + ", bounceSubType:" + bounceSubType + ", timestamp:" + timestamp + ", bouncedRecipients: " + bouncedRecipients;
    }
}
