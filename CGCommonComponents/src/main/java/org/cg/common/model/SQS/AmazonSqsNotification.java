package org.cg.common.model.SQS;

import java.io.IOException;
import java.util.List;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AmazonSqsNotification {

    @JsonCreator
    public AmazonSqsNotification(@JsonProperty("Type") String ty,@JsonProperty("Message") String mem) throws JsonParseException, JsonMappingException, IOException {
        this.Type = ty;
        ObjectMapper mapper = new ObjectMapper();
        AmazonSesBounceNotification mes = mapper.readValue(mem, AmazonSesBounceNotification.class);
        this.Message = mes;
    }
    public String Type;

    public AmazonSesBounceNotification Message;

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public AmazonSesBounceNotification getMessage() {
        return Message;
    }

    public void setMessage(AmazonSesBounceNotification message) {
        Message = message;
    }
    
    @Override
    public String toString() {
        return "Type:" + Type + ", Message:" + Message;
    }
}

// / <summary>Represents an Amazon SES bounce notification.</summary>


// / <summary>Represents meta data for the bounce notification from Amazon SES.</summary>

// / <summary>Represents the email address of recipients that bounced
// / when sending from Amazon SES.</summary>

