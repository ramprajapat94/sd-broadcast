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
 * @see http://sms.dealsms.in/user/api.php
 * @author Shriram Prajapat
 */
public class DealSms implements SMS {

    private static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger(DealSms.class);
    
    private final String APIKEY;
    private final String TYPE; //=1 (1=Normal / 2=Unicode)
    private final String SENDER;
    private final String ROUTE;// = "1-Promo, 2-Trans";

    private String response = "";

    /**
     *
     * @param type
     * @param APIKey
     * @param sender
     * @param route
     */
    public DealSms(String APIKey, String sender, String type, String route) {
        this.TYPE = type;
        this.APIKEY = APIKey;
        this.SENDER = sender;
        this.ROUTE = route;
        LOGGER.info("Deal Sms - " + APIKey + " " + sender);
    }

    //http://sms.dealsms.in/api/sendhttp.php?authkey=NWUxZjZjZGzMNlk&mobiles=7002592263&message=HelloRam&sender=NECCON&type=1&route=2

    @Override
    public String sendSms(String msg, String number) {
        if (!Utils.isAvailable()) {
            response = "No Internet Connection available!";
            return response;
        }
        try {
            // Construct data
            String data = "/sendhttp.php?authkey="+APIKEY;
            data += "&" + "mobiles="+number;
            data += "&" + "message="+URLEncoder.encode(msg, "UTF-8");
            data += "&" + "sender="+SENDER;
            data += "&" + "type="+TYPE;
            data += "&" + "route=" + ROUTE;
            
            String host = "http://sms.dealsms.in/api";
            URL url = new URL(host + data);
            URLConnection connection = url.openConnection();
            final StringBuffer stringBuffer;
            try (BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                stringBuffer = new StringBuffer();
                String line;
                while ((line = rd.readLine()) != null) {
                    stringBuffer.append(line);
                }
            }
//            String url = "http://sms.messageindia.in/api/mt/SendSMS?"+data;
            response = stringBuffer.toString();
            LOGGER.info(response);
            System.out.println(response);
            return response;
        } catch (IOException e) {
            LOGGER.error("Error sending sms : ", e);
            return "Error " + e;
        }
    }

    
    public String checkBalance() {
        if (!Utils.isAvailable()) {
            response = "No Internet Connection available!";
            return response;
        }
        try {
            //http://sms.dealsms.in/api/checkcredits.php?authkey=NWUxZjZjZGMzNzk

            String host = "http://sms.dealsms.in/api";
            URL url = new URL(host + "/checkcredits.php?authkey="+APIKEY);
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
            return response;
        } catch (IOException e) {
            LOGGER.error("Error Checking Balance sms : ", e);
            return "Error " + e;
        }
    }

    @Override
    public String getErrorInfo(int code) {
        switch (code) {
            default: return "No error code(s) are defined";
        }
    }

    @Override
    public String getResponseMessage() {
        return response;
    }

//    [{"responseCode":"Message SuccessFully Submitted"},{"msgid":"427132937"}]
   @Override
    public boolean isSuccessfull() {
        System.out.println(response);
        return (response.contains("\"error\":null"));
    }

   

//    public static void main(String[] args) {
//        SMS s = new DealSms("NWUxZjZjZGMzNzk", "NECCON", "1", "2");
//        String res = s.sendSms("test message ram", "7002592263");
//        System.out.println(res);
//        System.out.println(s.isSuccessfull());
//    }
}
