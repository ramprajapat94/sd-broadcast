
package com.sdigitizers.notification.sms;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Shriram Prajapat
 */
public class SmsConfig {
    
    private final SmsProvider smsProvider;
    private String userName;
    private String password;
    private String apiKey;
    private String senderId;
    private String route;
    private String language;
    private final Map<String, String> addParams = new HashMap<>(10);

    public SmsConfig(SmsProvider smsProvider) {
        this.smsProvider = smsProvider;
    }

    public SmsProvider getSmsProvider() {
        return smsProvider;
    }
    
    public String getUserName() {
        return userName;
    }

    public SmsConfig setUserName(String userName) {
        this.userName = userName;
        if(this.smsProvider.getParamUser() != null)addParams.put(this.smsProvider.getParamUser(), userName);
        return this;
    }

    public String getPassword() {
        return password;
    }

    public SmsConfig setPassword(String password) {
        this.password = password;
        if(this.smsProvider.getParamPassword() != null)addParams.put(this.smsProvider.getParamPassword(), password);
        return this;
    }

    public String getApiKey() {
        return apiKey;
    }

    public SmsConfig setApiKey(String apiKey) {
        this.apiKey = apiKey;
        if(this.smsProvider.getParamApiKey() != null)addParams.put(this.smsProvider.getParamApiKey(), apiKey);
        return this;
    }

    public String getSenderId() {
        return senderId;
    }

    public SmsConfig setSenderId(String senderId) {
        this.senderId = senderId;
        if(this.smsProvider.getParamSenderId() != null)addParams.put(this.smsProvider.getParamSenderId(), senderId);
        return this;
    }

    public String getRoute() {
        return route;
    }

    public SmsConfig setRoute(String route) {
        this.route = route;
        if(this.smsProvider.getParamRoute() != null)addParams.put(this.smsProvider.getParamRoute(), route);
        return this;
    }

    public String getLanguage() {
        return language;
    }

    public SmsConfig setLanguage(String language) {
        this.language = language;
        if(this.smsProvider.getParamLanguage() != null)addParams.put(this.smsProvider.getParamLanguage(), language);
        return this;
    }

    public Map<String, String> getAddParams() {
        return addParams;
    }

    @Override
    public String toString() {
        return "SmsConfig{" + "smsProvider=" + smsProvider + ", userName=" + userName + ", password=" + password + ", apiKey=" + apiKey + ", senderId=" + senderId + ", route=" + route + ", language=" + language + ", addParams=" + addParams + '}';
    }   
    
}
