
package com.sdigitizers.notification;

import com.sdigitizers.notification.email.EmailConfig;
import com.sdigitizers.notification.email.sendgrid.SendGridConfig;
import com.sdigitizers.notification.pushnotf.PushNotfConfig;
import com.sdigitizers.notification.sms.SmsConfig;

/**
 *
 * @author Shriram Prajapat
 */
public class NotifierConfig {
    
    private SmsConfig promoSmsConfig;
    private SmsConfig transSmsConfig;
    private SendGridConfig sendGridConfig;
    private EmailConfig emailConfig;
    private PushNotfConfig pushNotfConfig;
    
    private String subjectPrefix = "";
    private String subjectPostfix = "";
    private String messagePrefix = "";
    private String messagePostfix = "";

    public NotifierConfig(SmsConfig promoSmsConfig, SmsConfig transSmsConfig, SendGridConfig sendGridConfig, EmailConfig emailConfig, PushNotfConfig pushNotfConfig) {
        this.promoSmsConfig = promoSmsConfig;
        this.transSmsConfig = transSmsConfig;
        this.sendGridConfig = sendGridConfig;
        this.emailConfig = emailConfig;
        this.pushNotfConfig = pushNotfConfig;
    }

    public SmsConfig getPromoSmsConfig() {
        return promoSmsConfig;
    }

    public NotifierConfig setPromoSmsConfig(SmsConfig promoSmsConfig) {
        this.promoSmsConfig = promoSmsConfig;
        return this;
    }

    public SmsConfig getTransSmsConfig() {
        return transSmsConfig;
    }

    public NotifierConfig setTransSmsConfig(SmsConfig transSmsConfig) {
        this.transSmsConfig = transSmsConfig;
        return this;
    }

    public SendGridConfig getSendGridConfig() {
        return sendGridConfig;
    }

    public NotifierConfig setSendGridConfig(SendGridConfig sendGridConfig) {
        this.sendGridConfig = sendGridConfig;
        return this;
    }

    public EmailConfig getEmailConfig() {
        return emailConfig;
    }

    public NotifierConfig setEmailConfig(EmailConfig emailConfig) {
        this.emailConfig = emailConfig;
        return this;
    }

    public PushNotfConfig getPushNotfConfig() {
        return pushNotfConfig;
    }

    public NotifierConfig setPushNotfConfig(PushNotfConfig pushNotfConfig) {
        this.pushNotfConfig = pushNotfConfig;
        return this;
    }

    public String getSubjectPrefix() {
        return subjectPrefix;
    }

    public NotifierConfig setSubjectPrefix(String subjectPrefix) {
        this.subjectPrefix = subjectPrefix;
        return this;
    }

    public String getSubjectPostfix() {
        return subjectPostfix;
    }

    public NotifierConfig setSubjectPostfix(String subjectPostfix) {
        this.subjectPostfix = subjectPostfix;
        return this;
    }

    public String getMessagePrefix() {
        return messagePrefix;
    }

    public NotifierConfig setMessagePrefix(String messagePrefix) {
        this.messagePrefix = messagePrefix;
        return this;
    }

    public String getMessagePostfix() {
        return messagePostfix;
    }

    public NotifierConfig setMessagePostfix(String messagePostfix) {
        this.messagePostfix = messagePostfix;
        return this;
    }

    @Override
    public String toString() {
        return "NotifierConfig{" + "promoSmsConfig=" + promoSmsConfig + ", transSmsConfig=" + transSmsConfig + ", sendGridConfig=" + sendGridConfig + ", emailConfig=" + emailConfig + ", pushNotfConfig=" + pushNotfConfig + ", subjectPrefix=" + subjectPrefix + ", subjectPostfix=" + subjectPostfix + ", messagePrefix=" + messagePrefix + ", messagePostfix=" + messagePostfix + '}';
    }
    
    
    
}
