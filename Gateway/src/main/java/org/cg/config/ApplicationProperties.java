package org.cg.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to CG Gateway.
 * <p>
 * Properties are configured in the application.yml file.
 * See {@link io.github.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    private final Google google = new Google();
    
    private final Facebook facebook = new Facebook();
    
    private final Recaptcha recaptcha = new Recaptcha();
    
    private final AWS aws = new AWS();
    
    private final General general = new General();
    
    public static class Google {

        private String id;
        
        private String secret;
        
        private String accessCodeUrl;
        
        private String tokenUrl;
        
        private String userInfoUrl;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSecret() {
            return secret;
        }

        public void setSecret(String secret) {
            this.secret = secret;
        }

        public String getAccessCodeUrl() {
            return accessCodeUrl;
        }

        public void setAccessCodeUrl(String accessCodeUrl) {
            this.accessCodeUrl = accessCodeUrl;
        }

        public String getTokenUrl() {
            return tokenUrl;
        }

        public void setTokenUrl(String tokenUrl) {
            this.tokenUrl = tokenUrl;
        }

        public String getUserInfoUrl() {
            return userInfoUrl;
        }

        public void setUserInfoUrl(String userInfoUrl) {
            this.userInfoUrl = userInfoUrl;
        }
  
    }
    

    public static class Facebook {

        private String id;
        
        private String secret;
        
        private String accessCodeUrl;
        
        private String tokenUrl;
        
        private String graphUrl;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSecret() {
            return secret;
        }

        public void setSecret(String secret) {
            this.secret = secret;
        }

        public String getAccessCodeUrl() {
            return accessCodeUrl;
        }

        public void setAccessCodeUrl(String accessCodeUrl) {
            this.accessCodeUrl = accessCodeUrl;
        }

        public String getTokenUrl() {
            return tokenUrl;
        }

        public void setTokenUrl(String tokenUrl) {
            this.tokenUrl = tokenUrl;
        }

        public String getGraphUrl() {
            return graphUrl;
        }

        public void setGraphUrl(String graphUrl) {
            this.graphUrl = graphUrl;
        }
        
    }
    
    public static class Recaptcha {
        
        private String verificationUrl;
        private String secret;
        
        public String getVerificationUrl() {
            return verificationUrl;
        }
        public void setVerificationUrl(String verificationUrl) {
            this.verificationUrl = verificationUrl;
        }
        public String getSecret() {
            return secret;
        }
        public void setSecret(String secret) {
            this.secret = secret;
        }
    }
    
    public static class AWS {
        private String secretKey;
        private String accessKey;
        private String sesSenderMail;
        
        public String getSecretKey() {
            return secretKey;
        }
        public void setSecretKey(String secretKey) {
            this.secretKey = secretKey;
        }
        public String getAccessKey() {
            return accessKey;
        }
        public void setAccessKey(String accessKey) {
            this.accessKey = accessKey;
        }
        public String getSesSenderMail() {
            return sesSenderMail;
        }
        public void setSesSenderMail(String sesSenderMail) {
            this.sesSenderMail = sesSenderMail;
        }
   
    }
    
    public static class General {
        private String encryptionKey;
        private String oauthRedirectUrl;
        
        public String getEncryptionKey() {
            return encryptionKey;
        }
        public void setEncryptionKey(String encryptionKey) {
            this.encryptionKey = encryptionKey;
        }
        public String getOauthRedirectUrl() {
            return oauthRedirectUrl;
        }
        public void setOauthRedirectUrl(String oauthRedirectUrl) {
            this.oauthRedirectUrl = oauthRedirectUrl;
        }
    }
  

    public Google getGoogle() {
        return google;
    }

    public Facebook getFacebook() {
        return facebook;
    }

    public Recaptcha getRecaptcha() {
        return recaptcha;
    }

    public AWS getAws() {
        return aws;
    }

    public General getGeneral() {
        return general;
    }
}
