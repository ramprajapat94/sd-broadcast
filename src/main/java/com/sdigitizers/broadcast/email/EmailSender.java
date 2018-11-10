package com.sdigitizers.broadcast.email;
import com.sdigitizers.broadcast.Internet;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Date;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;

/**
 * Create an object of this class for sending email
 * @author Shriram Prajapat
 */
public class EmailSender {

    private static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger(EmailSender.class);
    
    private final Properties props;
    private final String from;
    private final String pass;
    private String response;
    
    /**
     * Instantiate object of this class with default GMAIL configured server for specified username and password
     * <br><br>
     * <b>Note:</b> Gmail does not allow sending email from third-party/less secured apps by default. In order to 
     * allow sending email from your GMAIL account, you must follow these two steps:
     * <br>
     * <li>
     *   <ul>Allow less-secure apps <a href='https://myaccount.google.com/lesssecureapps'a>https://myaccount.google.com/lesssecureapps</a></ul>
     *   <ul>Disable Two step verification from settings <a href='https://myaccount.google.com/security'>https://myaccount.google.com/security</a></ul>
     * </li>
     * @param from <b>Email-Id</b> of the <b>sender's</b> account
     * @param pass <b>Password</b> of the <b>sender's</b> account
     */
    public EmailSender(String from, String pass) {
        this.from = from;
        this.pass = pass;
        
        // Creates a Session with the following properties.
        props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.connectiontimeout", "10000");
        props.put("mail.smtp.timeout", "10000");
        
    }
    
    /**
     * Instantiate object of this class of configured email server with configuration specified in properties file for specified username and password
     * @param from <b>Email-Id</b> of the <b>sender's</b> account
     * @param pass <b>Password</b> of the <b>sender's</b> account
     * @param props Properties configured for the custom mail-server
     */
    public EmailSender(String from, String pass, Properties props) {
        this.from = from;
        this.pass = pass;
        // Creates a Session with the user-defined properties.
        this.props = props;
    }
    
    /**
     * Trigger email-send
     * @param to Email -id of the receiver
     * @param subject Subject of the Email
     * @param bodyText Body/Message/Content of the Email
     * @param attachmentFiles Attachment files (if any, can be passed null) 
     * @return Response from server
     */
    public String sendMail(String to, String subject, String bodyText, String[] attachmentFiles){
         if(!Internet.isAvailable()){
             return "No Internet Connection available!";
         }
         if(to==null || to.isEmpty()){
             return "Please specify a valid recepient";
         }
         if(subject==null)subject="";
         if(bodyText==null)bodyText="";
         if(null==props){
             return "No Server Configurations Specified. Please sepcify a properties file";
         }
//        from = "munnamanish01@gmail.com";
//        pass = "manish01";
        
        //props.put("mail.smtp.auth", "true"); 
        
//        Session session = Session.getInstance(props,
//              new javax.mail.Authenticator() {
//                protected PasswordAuthentication getPasswordAuthentication() {
//                    return new PasswordAuthentication(username, password);
//                }
//              });

        Session session = Session.getDefaultInstance(props);
        try {
            InternetAddress fromAddress = new InternetAddress(from);
            InternetAddress toAddress = new InternetAddress(to);

            // Create an Internet mail msg.
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(fromAddress);
            //msg.setRecipients(Message.RecipientType.TO, addresses);
            msg.setRecipient(Message.RecipientType.TO, toAddress);
            msg.setSubject(subject);
            msg.setSentDate(new Date());

            // Set the email msg text.
            MimeBodyPart messagePart = new MimeBodyPart();
            messagePart.setText(bodyText);

            
            // Create Multipart E-Mail.
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messagePart);
            
            // Set the email attachment files
            if(null != attachmentFiles && attachmentFiles.length > 0){
                for(String attachmentFile : attachmentFiles){
                    FileDataSource fileDataSource = new FileDataSource(attachmentFile);
                    MimeBodyPart attachmentPart = new MimeBodyPart();
                    attachmentPart.setDataHandler(new DataHandler(fileDataSource));
                    attachmentPart.setFileName(fileDataSource.getName());
//                    try { attachPart.attachFile(filePath);
//                    } catch (IOException ex) { ex.printStackTrace(); }
                    multipart.addBodyPart(attachmentPart);
                }
            }

            msg.setContent(multipart);

            Transport.send(msg, from, pass);
            
            LOGGER.info("Email sent to "+to);
            return (response="Email sent");
            
        } catch (MessagingException e) {
            LOGGER.error("Error sending email", e);
            return (response=e.getMessage());
        }
    }
       
    /**
     * Trigger email-send
     * @param em
     * @return Response from server
     */
    public String sendMail(EmailMessage em){
         if(!Internet.isAvailable()){
             return "No Internet Connection available!";
         }
         if(null==em){
             return "Invalid email message (Cannot be null)";
         }
         if(em.getTo().isEmpty()){
             return "Please specify atleast one recepient (to)";
         }
         if(null==props){
             return "No Server Configurations Specified. Please sepcify a properties file";
         }
//        from = "munnamanish01@gmail.com";
//        pass = "manish01";
        
        //props.put("mail.smtp.auth", "true"); 
        
//        Session session = Session.getInstance(props,
//              new javax.mail.Authenticator() {
//                protected PasswordAuthentication getPasswordAuthentication() {
//                    return new PasswordAuthentication(username, password);
//                }
//              });

        Session session = Session.getDefaultInstance(props);
        try {
            InternetAddress fromAddress = new InternetAddress(from);
            
            InternetAddress toAddresses[] = new InternetAddress[em.getTo().size()];
            for(int i=0; i<toAddresses.length; i++){
                toAddresses[i] = new InternetAddress(em.getTo().get(i));
            }
            
            InternetAddress ccAddresses[] = new InternetAddress[em.getCc().size()];
            for(int i=0; i<ccAddresses.length; i++){
                ccAddresses[i] = new InternetAddress(em.getCc().get(i));
            }
            
            InternetAddress bccAddresses[] = new InternetAddress[em.getBcc().size()];
            for(int i=0; i<bccAddresses.length; i++){
                bccAddresses[i] = new InternetAddress(em.getBcc().get(i));
            }

            // Create an Internet mail msg.
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(fromAddress);
            msg.setRecipients(Message.RecipientType.TO, toAddresses);
            if(ccAddresses.length != 0)msg.setRecipients(Message.RecipientType.CC, ccAddresses);
            if(bccAddresses.length != 0)msg.setRecipients(Message.RecipientType.BCC, bccAddresses);
            msg.setSubject(em.getSubject());
            msg.setSentDate(new Date());

            // Set the email msg text.
            MimeBodyPart messagePart = new MimeBodyPart();
            messagePart.setText(em.getBodyText());

            
            // Create Multipart E-Mail.
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messagePart);
            
            // Set the email attachment files
            String attachmentFiles[] = new String[em.getAttachments().size()];
            for(int i=0; i<attachmentFiles.length; i++){
                attachmentFiles[i] = em.getAttachments().get(i).getAbsolutePath();
            }
            
            if(attachmentFiles.length > 0){
                for(String attachmentFile : attachmentFiles){
                    FileDataSource fileDataSource = new FileDataSource(attachmentFile);
                    MimeBodyPart attachmentPart = new MimeBodyPart();
                    attachmentPart.setDataHandler(new DataHandler(fileDataSource));
                    attachmentPart.setFileName(fileDataSource.getName());
//                    try { attachPart.attachFile(filePath);
//                    } catch (IOException ex) { ex.printStackTrace(); }
                    multipart.addBodyPart(attachmentPart);
                }
            }

            msg.setContent(multipart);

            Transport.send(msg, from, pass);
            
            LOGGER.info("Email sent");
            return (response="Email sent");
            
        } catch (MessagingException e) {
            LOGGER.error("Error sending email", e);
            return (response=e.getMessage());
        }
    }
    
    /**
     * Status of the mail sent
     * @return true for successful, else false
     */
    public boolean isSuccessfull(){
        return response.contains("Email sent");
    }
    
    /**
     * Response sent from server on mail-sent
     * @return Response from server
     */
    public String getMessage(){
        return response;
    }
}
