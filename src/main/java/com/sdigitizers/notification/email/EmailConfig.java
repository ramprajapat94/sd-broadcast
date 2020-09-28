
package com.sdigitizers.notification.email;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 *
 * @author Shriram Prajapat
 */
public class EmailConfig {
    
    private final String emailId;
    private final String password;
    private final String senderName;
    private Properties properties = getGmailConfiguration();
    private List<String> acceptableFileExtenstions = new ArrayList<>();
    private int fileSizeLimitInBytes = 5242480;
    private int attachmentsLimit = 5;

    /**
     * {".docx", ".pptx", ".xlsx", ".pdf", ".txt", ".java",
        ".zip" ,".rar", ".7z", ".mp3", ".wav", ".mp4"}
     */
    /**
     * Instantiate object of this class of configured email server with configuration specified in properties file for specified username and password
     * @param emailId <b>Email-Id</b> of the <b>sender's</b> account
     * @param pass <b>Password</b> of the <b>sender's</b> account
     */
    public EmailConfig(String emailId, String pass, String senderName) {
        this.emailId = emailId;
        this.password = pass;
        this.senderName = senderName;
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
    
    public EmailConfig(String emailId, String pass, String senderName, Properties customProperties) {
        this.emailId = emailId;
        this.password = pass;
        this.senderName = senderName;
        this.properties = customProperties;
    }

    public String getEmailId() {
        return emailId;
    }

    public String getPassword() {
        return password;
    }
    
    public String getSenderName() {
        return senderName;
    }

    
    public List<String> getAcceptableFileExtenstions() {
        return acceptableFileExtenstions;
    }

    public EmailConfig setAcceptableFileExtenstions(List<String> acceptableFileExtenstions) {
        this.acceptableFileExtenstions = acceptableFileExtenstions;
        return this;
    }

    /**
     * 5MB (1024 * 1024 * 5)
     */
    public int getFileSizeLimitInBytes() {
        return fileSizeLimitInBytes;
    }

    public EmailConfig setFileSizeLimitInBytes(int fileSizeLimitInBytes) {
        this.fileSizeLimitInBytes = fileSizeLimitInBytes;
        return this;
    }

     /**
     * Default: 5
     */
    public int getAttachmentsLimit() {
        return attachmentsLimit;
    }

    public EmailConfig setAttachmentsLimit(int attachmentsLimit) {
        this.attachmentsLimit = attachmentsLimit;
        return this;
    }
    
    public Properties getProperties() {
        return properties;
    }

    public EmailConfig setProperties(Properties properties) {
        this.properties = properties;
        return this;
    }
    
    
    /**
     * <br><br>
     * <b>Note:</b> Gmail does not allow sending email from third-party/less secured apps by default. In order to 
     * allow sending email from your GMAIL account, you must follow these two steps:
     * <br>
     * <li>
     *   <ul>Allow less-secure apps <a href='https://myaccount.google.com/lesssecureapps'a>https://myaccount.google.com/lesssecureapps</a></ul>
     *   <ul>Disable Two step verification from settings <a href='https://myaccount.google.com/security'>https://myaccount.google.com/security</a></ul>
     * </li>
     * @return GMAIL Mail Session configurations
     */
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

    @Override
    public String toString() {
        return "EmailConfig{" + "emailId=" + emailId + ", password=" + password + ", senderName=" + senderName + ", properties=" + properties + ", acceptableFileExtenstions=" + acceptableFileExtenstions + ", fileSizeLimitInBytes=" + fileSizeLimitInBytes + ", attachmentsLimit=" + attachmentsLimit + '}';
    }

    
}
