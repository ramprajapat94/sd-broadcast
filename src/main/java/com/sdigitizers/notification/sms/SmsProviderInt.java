
package com.sdigitizers.notification.sms;

import java.util.Map;

/**
 *
 * @author Shriram Prajapat
 */
public interface SmsProviderInt {
    
    public String getCompanyName();
    public String getWebsite();
    public String getPortalUrl();
    
    public boolean isSuccessful(String response);
    public Map<String, String> getErrorCodes();
    
    public String getHttpRequestType();
    
    public String getApiBaseUrl();
    
    public String getSendApiUrl();
    public String getParamUser();
    public String getParamPassword();
    public String getParamApiKey();
    public String getParamSenderId();
    public String getParamRoute();
    public String getParamLanguage();
    public String getParamMessage();
    public String getParamNumber();
    
    public String getCheckBalanceUrl();
    
}
