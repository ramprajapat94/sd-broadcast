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

public class EmailSender {

    private static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger(EmailSender.class);
    
    private final String from;
    private final String pass;
    private String response;
    
    public EmailSender(String from, String pass) {
        this.from = from;
        this.pass = pass;
    }
    
    
       public String sendMail(String to, String subject, String bodyText, String[] attachmentFiles){
         if(!Internet.isAvailable()){
             return "No Internet Connection available!";
         }
//        from = "munnamanish01@gmail.com";
//        pass = "manish01";
        // Creates a Session with the following properties.
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.connectiontimeout", "10000");
        props.put("mail.smtp.timeout", "10000");
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
            return (response="success");
            
        } catch (MessagingException e) {
            LOGGER.error("Error sending email", e);
            return (response=e.getMessage());
        }
    }
       
    public boolean isSuccessfull(){
        return response.contains("success");
    }
    
    public String getMessage(){
        return response;
    }
}
