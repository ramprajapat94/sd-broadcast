
package com.sdigitizers.broadcast.email;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 *
 * @author Shriram Prajapat
 */
public class EmailConfig {
    
    private List<String> acceptableFileExtenstions = new ArrayList<>();
    private int fileSizeLimitInBytes = 5242480;
    private int attachmentsLimit = 5;

    /**
     * {".docx", ".pptx", ".xlsx", ".pdf", ".txt", ".java",
        ".zip" ,".rar", ".7z", ".mp3", ".wav", ".mp4"}
     */
    public EmailConfig() {
        acceptableFileExtenstions.add(".jpg");
        acceptableFileExtenstions.add(".jpeg");
        acceptableFileExtenstions.add(".png");
        acceptableFileExtenstions.add(".gif");
        acceptableFileExtenstions.add(".docx");
        acceptableFileExtenstions.add(".pptx");
        acceptableFileExtenstions.add(".xlsx");
        acceptableFileExtenstions.add(".pdf");
        acceptableFileExtenstions.add(".txt");
        acceptableFileExtenstions.add(".zip");
        acceptableFileExtenstions.add(".rar");
        acceptableFileExtenstions.add(".7z");
        acceptableFileExtenstions.add(".mp3");
        acceptableFileExtenstions.add(".wav");
        acceptableFileExtenstions.add(".mp4");
    }

    public List<String> getAcceptableFileExtenstions() {
        return acceptableFileExtenstions;
    }

    public void setAcceptableFileExtenstions(List<String> acceptableFileExtenstions) {
        this.acceptableFileExtenstions = acceptableFileExtenstions;
    }

    /**
     * 5MB (1024 * 1024 * 5)
     */
    public int getFileSizeLimitInBytes() {
        return fileSizeLimitInBytes;
    }

    public void setFileSizeLimitInBytes(int fileSizeLimitInBytes) {
        this.fileSizeLimitInBytes = fileSizeLimitInBytes;
    }

     /**
     * 5
     */
    public int getAttachmentsLimit() {
        return attachmentsLimit;
    }

    public void setAttachmentsLimit(int attachmentsLimit) {
        this.attachmentsLimit = attachmentsLimit;
    }
    
    
    public static Properties getGmailConfiguration(){
        Properties props = new Properties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.connectiontimeout", "10000");
        props.put("mail.smtp.timeout", "10000");
        
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	props.put("mail.smtp.auth", "true");
        
        return props;
    }
    
    public static Properties getYahooConfiguration(){
        Properties props = new Properties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.host", "smtp.mail.yahoo.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.connectiontimeout", "10000");
        props.put("mail.smtp.timeout", "10000");
        
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	props.put("mail.smtp.auth", "true");
        
        return props;
    }
}
