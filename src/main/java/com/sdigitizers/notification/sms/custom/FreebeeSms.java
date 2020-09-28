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
public class FreebeeSms implements SMS {

    private static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger(FreebeeSms.class);
    
    private final String APIKEY;
    private final String SENDER;
    private final String ROUTE;

    private String response = "";

    /**
     *
     * @param APIKey
     * @param sender
     */
    public FreebeeSms(String APIKey, String sender, String route) {
        this.APIKEY = APIKey;
        this.SENDER = sender;
        this.ROUTE = route;
        LOGGER.info("FreebeeSms - " + sender);
    }

    @Override
    public String sendSms(String msg, String number) {
        if (!Utils.isAvailable()) {
            response = "No Internet Connection available!";
            return response;
        }
        try {
            //encoding message 
            String encoded_message=URLEncoder.encode(msg, "UTF-8");
            //Send SMS API
            String mainUrl="http://neat.freebeesms.com/api/send_http.php?";
            //Prepare parameter string 
            StringBuilder sbPostData= new StringBuilder(mainUrl);
            sbPostData.append("authkey=").append(APIKEY); 
            sbPostData.append("&mobiles=").append(number);
            sbPostData.append("&message=").append(encoded_message);
            sbPostData.append("&route=").append(ROUTE);
            sbPostData.append("&sender=").append(SENDER);
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
        
        return !(response.contains("\"type\":\"error\""));
    }

//    public static void main(String[] args) {
//        SMS s = new FreebeeSms("222cede84bc4c3c081d1ee29ebc0419a", "TURSAP");
//        String res = s.sendSms("test", "7399780500");
//        System.out.println(res);
//        System.out.println(s.isSuccessfull());
//    }
}
