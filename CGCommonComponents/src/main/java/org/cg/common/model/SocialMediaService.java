package org.cg.common.model;

public enum SocialMediaService {
    FACEBOOK("Facebook"),
    TWITTER("Twitter"),
    GOOGLE("Google");
    
    private String provider;
    SocialMediaService(String provider) {
        this.provider = provider;
    }
    
    public String provider() {
        return this.provider;
    }
}