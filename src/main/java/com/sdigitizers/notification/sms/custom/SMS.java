
package com.sdigitizers.notification.sms.custom;

/**
 *
 * @author Shriram Prajapat
 */
public interface SMS {
    
    public String sendSms(String msg, String number);
    public String getResponseMessage();
    public String getErrorInfo(int code);
    public boolean isSuccessfull();
    
}
