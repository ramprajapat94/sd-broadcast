
package com.sdigitizers.broadcast.email;

import java.io.File;
import java.util.ArrayList;
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

    public void setTo(List<String> to) {
        this.to = to;
    }

    public List<String> getCc() {
        if(null == cc) cc = new ArrayList<>(1);
        return cc;
    }

    public void setCc(List<String> cc) {
        this.cc = cc;
    }

    public List<String> getBcc() {
        if(null == bcc) bcc = new ArrayList<>(1);
        return bcc;
    }

    public void setBcc(List<String> bcc) {
        this.bcc = bcc;
    }

    public String getSubject() {
        if(null == subject) subject= "";
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBodyText() {
        if(null == bodyText) bodyText= "";
        return bodyText;
    }

    public void setBodyText(String bodyText) {
        this.bodyText = bodyText;
    }

    public List<File> getAttachments() {
        if(null == attachments) attachments = new ArrayList<>(1);
        return attachments;
    }

    public void setAttachments(List<File> attachments) {
        this.attachments = attachments;
    }
    
    
}
