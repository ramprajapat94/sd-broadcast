package com.sdigitizers.notification.email;
import com.sdigitizers.notification.Response;
import com.sdigitizers.notification.Utils;
import java.io.UnsupportedEncodingException;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Date;
import java.util.List;

/**
 * @see www.simplejavamail.org/#/about  [For future work]
 * Create an object of this class for sending email
 * @author Shriram Prajapat
 */

public class EmailSender {

    //private static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger(EmailSender.class);
    
    private final EmailConfig emailConfig;

    public EmailSender(EmailConfig conf) {
        this.emailConfig = conf;
    }
    
    public EmailConfig getEmailConfig() {
        return emailConfig;
    }
    
    /**
     * Trigger email-send
     * @param to Email -id of the receiver
     * @param subject Subject of the Email
     * @param bodyText Body/Message/Content of the Email
     * @return Response from server
     */
    public Response sendMail(String to, String subject, String bodyText){
         return sendMail(to, subject, bodyText, null);
    }
    
    /**
     * Trigger email-send
     * @param to Email -id of the receiver
     * @param subject Subject of the Email
     * @param bodyText Body/Message/Content of the Email
     * @param attachmentFiles Attachment files (if not any, can be passed null) 
     * @return Response from server
     */
    public Response sendMail(String to, String subject, String bodyText, List<String> attachmentFiles){
         EmailMessage em = new EmailMessage();
         em.setTo(to);
         em.setSubject(subject);
         em.setBodyText(bodyText);
         em.setAttachments(attachmentFiles);
         return sendMail(em);
    }
       
    /**
     * Trigger email-send
     * @param em
     * @return Response from server
     */
    public Response sendMail(EmailMessage em){
         if(!Utils.isAvailable()){
             return new Response(false, "No Internet Connection available!");
         }
         if(null==em){
             return new Response(false, "Invalid email message (Cannot be null)");
         }
         if(em.getTo().isEmpty()){
             return new Response(false, "Please specify atleast one recepient (to)");
         }
         if(null==emailConfig.getProperties()){
             return new Response(false, "No Server Configurations Specified. Please sepcify a properties file");
         }
        if(null!=em.getAttachments() && em.getAttachments().size() > emailConfig.getAttachmentsLimit()){
            return new Response(false, "Attachments limit crossed");
         }
        
        Session session = Session.getInstance(emailConfig.getProperties(),
              new javax.mail.Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(emailConfig.getEmailId(), emailConfig.getPassword());
                }
              });

        //Session session = Session.getDefaultInstance(props);
        try {
            InternetAddress fromAddress = new InternetAddress(emailConfig.getEmailId(), emailConfig.getSenderName());
            
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

            // Create an Utils mail msg
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(fromAddress);
            msg.setRecipients(Message.RecipientType.TO, toAddresses);
            if(ccAddresses.length != 0)msg.setRecipients(Message.RecipientType.CC, ccAddresses);
            if(bccAddresses.length != 0)msg.setRecipients(Message.RecipientType.BCC, bccAddresses);
            msg.setSubject(em.getSubject());
            msg.setSentDate(new Date());

            // Set the email msg text.
            MimeBodyPart messagePart = new MimeBodyPart();
            messagePart.setText(em.getBodyText(), "UTF-8", "html");

            
            // Create Multipart E-Mail.
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messagePart);
            
            // Set the email attachment files
            if(em.getAttachments() != null){
                if(em.getAttachments().size() > 0){
                    for(String attachmentFile : em.getAttachments()){
                        FileDataSource fileDataSource = new FileDataSource(attachmentFile);
                        MimeBodyPart attachmentPart = new MimeBodyPart();
                        attachmentPart.setDataHandler(new DataHandler(fileDataSource));
                        attachmentPart.setFileName(fileDataSource.getName());
    //                    try { attachPart.attachFile(filePath);
    //                    } catch (IOException ex) { ex.printStackTrace(); }
                        multipart.addBodyPart(attachmentPart);
                    }
                }
            }
            
            msg.setContent(multipart);
            Transport.send(msg);
            
            return new Response(true, "Email Sent");
            
        } catch (MessagingException e) {
            //LOGGER.error("Error sending email", e);
            return new Response(false, e.toString());
        } catch (UnsupportedEncodingException ex) {
            //LOGGER.error("Error sending email", ex);
            return new Response(false, ex.toString());
        }
    }
    
    
    public static Response send(EmailConfig config, EmailMessage em){
        return new EmailSender(config).sendMail(em);
    }
    
//    public static void main(String[] args) {
//        //List<String> ss = new ArrayList<>();
//        //ss.add("https://www.w3schools.com/howto/img_forest.jpg");
//        String s = new EmailSender(new EmailConfig("munnamanish01@gmail.com", "manish007", "Ram")).sendMail("Ram Prajapat <solution.ramsofts@gmail.com>", "test", "message").getMessage();
//        System.out.println(s);
//    }
}
