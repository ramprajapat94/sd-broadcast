
package com.sdigitizers.broadcast.email;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Shriram Prajapat
 */
public class EmailMessage {
    
    private List<String> to;
    private List<String> cc;
    private List<String> bcc;
    private String subject;
    private String bodyText;
    private List<File> attachments;
    
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

    public List<File> getAttachments() {
        if(null == attachments) attachments = new ArrayList<>(1);
        return attachments;
    }

    public EmailMessage setAttachments(List<File> attachments) {
        this.attachments = attachments;
        return this;
    }
    
    public String send(String email, String pass){
         return new EmailSender(email, pass).sendMail(this);
    }
    
}
