
package com.sdigitizers.notification.email;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
//import javax.mail.internet.AddressException;
//import javax.mail.internet.InternetAddress;

/**
 *
 * @author Shriram Prajapat
 */
public final class EmailMessage {
    
    private List<String> to;
    private List<String> cc;
    private List<String> bcc;
    private String subject;
    private String bodyText;
    private List<String> attachments;

    public EmailMessage() {
    }

    
    public EmailMessage(String to, String subject, String message) {
        setTo(to);
        this.subject = subject;
        this.bodyText = message;
    }
    
    
    
    public List<String> getTo() {
        if(null == to) to = new ArrayList<>(1);
        return to;
    }

    public EmailMessage setTo(List<String> to) {
        this.to = to;
        return this;
    }
    
    public EmailMessage setTo(String email) {
        this.to = new ArrayList<>(1);
        this.to.add(email);
        return this;
    }
    
    public EmailMessage setTo(String... emails) {
        this.to = Arrays.asList(emails);
        return this;
    }

    public List<String> getCc() {
        if(null == cc) cc = new ArrayList<>(1);
        return cc;
    }

    public EmailMessage setCc(List<String> cc) {
        this.cc = cc;
        return this;
    }

    public EmailMessage setCc(String email) {
        this.cc = new ArrayList<>(1);
        this.cc.add(email);
        return this;
    }
    
    public EmailMessage setCc(String... emails) {
        this.cc = Arrays.asList(emails);
        return this;
    }
    
    public List<String> getBcc() {
        if(null == bcc) bcc = new ArrayList<>(1);
        return bcc;
    }

    public EmailMessage setBcc(List<String> bcc) {
        this.bcc = bcc;
        return this;
    }

    public EmailMessage setBcc(String email) {
        this.bcc = new ArrayList<>(1);
        this.bcc.add(email);
        return this;
    }
    
    public EmailMessage setBcc(String... emails) {
        this.bcc = Arrays.asList(emails);
        return this;
    }
    
    public String getSubject() {
        if(null == subject) subject= "";
        return subject;
    }

    public EmailMessage setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public String getBodyText() {
        if(null == bodyText) bodyText= "";
        return bodyText;
    }

    public EmailMessage setBodyText(String bodyText) {
        this.bodyText = bodyText;
        return this;
    }

    public List<String> getAttachments() {
        if(null == attachments) attachments = new ArrayList<>(1);
        return attachments;
    }

    public EmailMessage setAttachments(List<String> attachments) {
        this.attachments = attachments;
        return this;
    }
    
    public static String formatMessageForEmail(String message){
        return "<HTML><BODY>"+message.replace("\n", "<br>")+"</BODY></HTML>";
    }
    
//    public static boolean isValidEmailAddress(String email) {
//        boolean result = true;
//        try {
//           InternetAddress emailAddr = new InternetAddress(email);
//           emailAddr.validate();
//        } catch (AddressException ex) {
//            System.out.println(ex);
//           result = false;
//        }
//        return result;
//     }

}
