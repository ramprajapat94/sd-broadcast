package com.sdigitizers.notification.sms.custom;

import com.sdigitizers.notification.Utils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import org.apache.logging.log4j.LogManager;

/**
 * @see 
 * @author Shriram Prajapat
 */
public class NeatDigitalSms implements SMS {

    private static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger(NeatDigitalSms.class);
    
    private final String USERNAME;
    private final String PASSWORD;
    private final String SENDER;
    private final String ROUTE;

    private String response = "";

    /**
     *
     * @param username
     * @param password
     * @param sender
     */
    public NeatDigitalSms(String username, String password, String sender, String route) {
        this.USERNAME = username;
        this.PASSWORD = password;
        this.SENDER = sender;
        this.ROUTE = route;
        LOGGER.info("NeatDigitalSms - " + sender);
    }
    @Override
    public String sendSms(String msg, String number) {
        if (!Utils.isAvailable()) {
            response = "No Internet Connection available!";
            return response;
        }
        try {
            //http://sms.neatdigital.in/http-api.php?username=TURSAP&password=TURSAP@2019&senderid=TURSAP&route=6&number=7399780500&message=hello%20there
            String mainUrl="http://sms.neatdigital.in/http-api.php?";
            //Prepare parameter string 
            StringBuilder sbPostData= new StringBuilder(mainUrl);
            sbPostData.append("username=").append(USERNAME); 
            sbPostData.append("&password=").append(PASSWORD); 
            sbPostData.append("&senderid=").append(SENDER);
            sbPostData.append("&route=").append(ROUTE);
            sbPostData.append("&number=").append(number);
            sbPostData.append("&message=").append(URLEncoder.encode(msg, "UTF-8"));
            
            ;
            //final string
            mainUrl = sbPostData.toString();
            URL url = new URL(mainUrl);
            URLConnection connection = url.openConnection();
            final StringBuffer stringBuffer;
            try (BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                stringBuffer = new StringBuffer();
                String line;
                while ((line = rd.readLine()) != null) {
                    stringBuffer.append(line);
                }
            }
            response = stringBuffer.toString();
            LOGGER.info(response);
            System.out.println(response);
            return response;
        } catch (IOException e) {
            LOGGER.error("Error sending sms : ", e);
            return "Error " + e;
        }
    }

    @Override
    public String getErrorInfo(int code) {
        switch (code) {
            case 301 : return "Authentication Error";
            case 204 : return "Invalid mobile number(s)";
            default: return "No such error code is defined";
        }
    }

    @Override
    public String getResponseMessage() {
        return response;
    }

    @Override
    public boolean isSuccessfull() {
        System.out.println(response);
        
        return (response.contains("Message Submitted Successfully"));
    }

//    public static void main(String[] args) {
//        SMS s = new NeatDigitalSms("TURSAP", "TURSAP@2019", "TURSAP", "6");
//        String res = s.sendSms("test", "7399780500");
//        System.out.println(res);
//        System.out.println(s.isSuccessfull());
//    }
}
