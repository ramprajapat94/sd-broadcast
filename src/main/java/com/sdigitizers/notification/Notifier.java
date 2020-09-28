
package com.sdigitizers.notification;

import com.sdigitizers.notification.email.EmailMessage;
import com.sdigitizers.notification.email.EmailSender;
import com.sdigitizers.notification.pushnotf.PushNotfMessage;
import com.sdigitizers.notification.pushnotf.PushNotfSender;
import com.sdigitizers.notification.sms.SmsMessage;
import com.sdigitizers.notification.sms.SmsSender;
import com.sdigitizers.notification.sms.SmsType;
import java.text.MessageFormat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Shriram Prajapat
 */
public class Notifier {
    
    private static final Logger LOGGER = LogManager.getLogger(Notifier.class);
    private final NotifierConfig config;
    

    public Notifier(NotifierConfig config) {
        this.config = config;
    }

    public NotifierConfig getConfig() {
        return config;
    }
    
    
    public void sendPushNotf(PushNotfMessage msg){
         new Thread(() -> {
             PushNotfSender.send(config.getPushNotfConfig(), msg);
         }).start();
    }
    
    public void sendSMS(SmsMessage msg){
        new Thread(() -> {
            if(msg.getNumbers().isEmpty())return;
            msg.setMessage(config.getMessagePrefix() + msg.getMessage() + config.getMessagePostfix());
            Response res = SmsSender.send(msg.getType()==SmsType.TRANS?config.getTransSmsConfig():config.getPromoSmsConfig(), msg);
            if(!res.isSuccessful()){
             LOGGER.error("Sms sending error: ", res.getMessage());   
            }            
        }).start();
    }
    
    public void sendPhoneOtp(String otp, long phone, String sender, String appHash){
        SmsMessage sm = new SmsMessage(SmsType.TRANS)
                .setMessage(MessageFormat.format("<#> Your OTP for verification is : {0} \n{1}\n{2}", otp, sender, appHash))
                .setNumbers(phone+"");
        new Thread(() -> {
            Response res = SmsSender.send(config.getTransSmsConfig(), sm);
            if(!res.isSuccessful()){
             LOGGER.error("Sms (otp) sending error: "+res, res);   
            }            
        }).start();
    }
    
    
    
    public void sendEmailOtp(String text, String toEmail){
        this.sendEmail(new EmailMessage(toEmail, "OTP", text));
    }
    
    public void sendEmail(EmailMessage em){
        if(em == null)return;
        new Thread(() -> {
             em.setSubject(config.getSubjectPrefix() + em.getSubject() + config.getSubjectPostfix());
             em.setBodyText(em.getBodyText());
             Response res = EmailSender.send(config.getEmailConfig(), em);
             if(!res.isSuccessful())LOGGER.error("Email sending error: ", res.getMessage());
        }).start();
    }

    @Override
    public String toString() {
        return "Notifier{" + "config=" + config + '}';
    }
  
}
