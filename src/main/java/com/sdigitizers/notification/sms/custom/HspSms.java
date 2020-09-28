package com.sdigitizers.notification.sms.custom;

import com.sdigitizers.notification.Utils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.apache.logging.log4j.LogManager;

/**
 * @see http://sms.hspsms.com
 * @author Shriram Prajapat
 */
public class HspSms implements SMS {

    private static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger(HspSms.class);
    
    private final String APIKEY;
    private final String USERNAME;
    private final String SENDER;
    private final String ROUTE;// = "TRANS";    

    private String response = "";

    /**
     *
     * @param userName
     * @param APIKey
     * @param sender (Default = WEBSMS)
     * @param route
     */
    public HspSms(String userName, String APIKey, String sender, String route) {
        this.USERNAME = userName;
        this.APIKEY = APIKey;
        this.SENDER = sender;
        this.ROUTE = route;
        LOGGER.info("MessageIndia - " + userName + " " + sender);
    }

    //http://sms.hspsms.com/sendSMS?username=keyman&message=XXXXXXXXXX&sendername=XYZ&smstype=TRANS&numbers=<mobile_numbers>&apikey=5eb118e4-b3c6-4260-a62a-eb6ee3909880 

    @Override
    public String sendSms(String msg, String number) {
        if (!Utils.isAvailable()) {
            response = "No Internet Connection available!";
            return response;
        }
        try {
            // Construct data
            String data = "/sendSMS?username="+USERNAME;
            data += "&" + "apikey="+APIKEY;
            data += "&" + "smstype="+ROUTE;
            data += "&" + "sendername="+SENDER;
            data += "&" + "numbers="+number;
            data += "&" + "message=" + URLEncoder.encode(msg, "UTF-8");
            
            String host = "http://sms.hspsms.com";
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

    public String scheduleSms(String msg, String number, LocalDateTime time) {
        if (!Utils.isAvailable()) {
            response = "No Internet Connection available!";
            return response;
        }
        try {
            // Construct data
            String data = "/sendSMS?username="+USERNAME;
            data += "&" + "apikey="+APIKEY;
            data += "&" + "smstype="+ROUTE;
            data += "&" + "sendername="+SENDER;
            data += "&" + "numbers="+number;
            data += "&" + "message=" + URLEncoder.encode(msg, "UTF-8");
            data += "&" + "scheduled=" + DateTimeFormatter.ofPattern("yyyyMMddhhmm").format(time);

            // Send data
            String host = "http://sms.hspsms.com";
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
            response = stringBuffer.toString();
            LOGGER.info(response);
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
            //http://sms.hspsms.com/getSMSCredit?username=keyman&apikey=5eb118e4-b3c6-4260-a62a-eb6ee3909880 


            String host = "http://sms.hspsms.com";
            URL url = new URL(host + "/getSMSCredit?username="+USERNAME+"&apikey="+APIKEY);
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
            case 0 : return "Done";
            case 1 : return "login details cannot be blank";
            case 3 : return "sender cannot be blank";
            case 4 : return "message text cannot be blank";
            case 5 : return "message data cannot be blank";
            case 6 : return "error: generic error description";
            case 7 : return "username or password is invalid";
            case 8 : return "account not active";
            case 9 : return "account locked, contact your account manager";
            case 10 : return "api restriction";
            case 11 : return "ip address restriction";
            case 12 : return "invalid length of message text";
            case 13 : return "mobile numbers not valid";
            case 14 : return "account locked due to spam message contact support";
            case 15 : return "senderid not valid";
            case 17 : return "groupid not valid";
            case 18 : return "multi message to group is not supported";
            case 19 : return "schedule date is not valid";
            case 20 : return "message or mobile number cannot be blank";
            case 21 : return "insufficient credits";
            case 22 : return "invalid jobid";
            case 23 : return "parameter missing";
            case 24 : return "invalid template or template mismatch";
            case 25 : return "{Field} can not be blank or empty";
            case 26 : return "invalid date range";
            case 27 : return "invalid optin user";
            default: return "No such error code is defined";
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
        return (response.contains("SuccessFully Submitted"));
    }

   

//    public static void main(String[] args) {
//        SMS s = new HspSms("keyman", "1acf69f6-9c9a-4fe9-9626-7ee8e713ca18", "KEYMAN", "TRANS");
//        String res = s.sendSms("test message", "7399780500");
//        System.out.println(res);
//        //System.out.println(s.isSuccessfull());
//    }
}
