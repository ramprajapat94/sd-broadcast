
package com.sdigitizers.notification.pushnotf;

/**
 * @since 23-05-2019
 * @author Shriram Prajapat
 */
public class PushNotfConfig {
    
    private final String appId;
    private final String restApiKey;
    private final String identifier;
    private final boolean android;
    private final boolean ios;

    public PushNotfConfig(String appId, String restApiKey, String identifier, boolean android, boolean ios) {
        this.appId = appId;
        this.restApiKey = restApiKey;
        this.identifier = identifier;
        this.android = android;
        this.ios = ios;
    }

    

    public String getAppId() {
        return appId;
    }

    public String getRestApiKey() {
        return restApiKey;
    }

    public String getIdentifier() {
        return identifier;
    }

    public boolean isAndroid() {
        return android;
    }

    public boolean isIos() {
        return ios;
    }
    

    @Override
    public String toString() {
        return "AppId: "+appId+"\nRest Api Key: "+restApiKey;
    }
    
    
}
