
package com.sdigitizers.notification.sms.custom;

/**
 *
 * @author Shriram Prajapat
 */
public class SmsFactory {
    
    private final SmsCompany smsCompany;
    private final String username;
    private final String password;
    private final String apiKey;
    private final String senderId;
    private final String route;
    
    
    public SMS getInstance(){
        switch(smsCompany){
            case BHASH_SMS : return new BhashSMS(username, password, senderId);
            case FAST_2_SMS :  return new Fast2SMS(apiKey, senderId);
            case ROUND_SMS : return new RoundSMS(apiKey, RoundSMS.DCS.NORMAL, RoundSMS.RoundSmsRoute.Transactional);
            case TEXTLOCAL : return new TextLocalSMS(apiKey, senderId);
            case MESSAGE_INDIA : return new MessageIndia(username, password, apiKey, senderId, route);
            case FREE_BEE_SMS : return new FreebeeSms(apiKey, senderId, route);
            case NEAT_DIGITAL_SMS : return new NeatDigitalSms(username, password, senderId, route);
            case HSP_SMS : return new HspSms(username, apiKey, senderId, route);
            case DEAL_SMS : return new DealSms(apiKey, senderId, "1", route);
            default : return new TextLocalSMS(apiKey, senderId);
        }
    }

    public SmsFactory(SmsCompany smsCompany, String username, String password, String apiKey, String senderId, String route) {
        this.smsCompany = smsCompany;
        this.username = username;
        this.password = password;
        this.apiKey = apiKey;
        this.senderId = senderId;
        this.route = route;
    }
    
     
    public SmsCompany getSmsCompany() {
        return smsCompany;
    }


    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getSenderId() {
        return senderId;
    }
    
}
