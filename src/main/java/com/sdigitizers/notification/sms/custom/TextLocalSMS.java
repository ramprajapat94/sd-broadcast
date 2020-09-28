
package com.sdigitizers.notification.sms.custom;

import com.sdigitizers.notification.Utils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.apache.logging.log4j.LogManager;

/**
 *
 * @author Shriram Prajapat
 */

  public class TextLocalSMS implements SMS{
      
    private static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger(TextLocalSMS.class);
    
    private final String API_KEY;
    private final String SENDER;
    private String response = "";

    /**
     * @param apiKey
     * @param sender (Default = TXTLCL)
     */
    public TextLocalSMS(String apiKey, String sender ) {
        this.API_KEY = apiKey;
        this.SENDER = sender;
        LOGGER.info("TextLocal - "+apiKey+"  "+sender);
    }
    public TextLocalSMS(String apiKey){
        this(apiKey, "TXTLCL");
    }
      
      
    @Override
    public String sendSms(String msg, String number) {
        if(!Utils.isAvailable()){
             response = "No Internet Connection available!";
             return response;
        }
            try {
                    // Construct data
                    String apiKey = "apikey=" + API_KEY;
                    String message = "&message=" + msg;
                    String sender = "&sender=" + SENDER;
                    String numbers = "&numbers=" + number;

                    // Send data
                    HttpURLConnection conn = (HttpURLConnection) new URL("https://api.textlocal.in/send/?").openConnection();
                    String data = apiKey + numbers + message + sender;
                    conn.setDoOutput(true);
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
                    conn.getOutputStream().write(data.getBytes("UTF-8"));
                    final StringBuffer stringBuffer;
                    try (BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
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
                    LOGGER.error("Error sending SMS : ",e);
                    return "Error "+e;
            }
    	}
    
    @Override
    public String getErrorInfo(int code){
        switch(code){
            case 3 : return "Invalid Login Details";
            case 4 : return "No recipients specified.";
            case 5 : return "No message content.";
            case 6 : return "Message too long.";
            case 7 : return "Insufficient credits.";
            case 8 : return "Invalid schedule date.";
            case 9 : return "Schedule date is in the past.";
            case 10 : return "Invalid group ID.";
            case 11 : return "Selected group is empty.";
            case 32 : return "Invalid number format.";
            case 33 : return "You have supplied too many numbers.";
            case 34 : return "You have supplied both a group ID and a set of numbers.";
            case 43 : return "Invalid sender name.";
            case 44 : return "No sender name specified.";
            case 51 : return "No valid numbers specified.";
            case 191 : return "Schedule time is outside that allowed.";
            case 192 : return "You cannot send message at this time.";
            default : return "Undefined Error Code";
        }
    }
    
    @Override
    public String getResponseMessage() {
        return response.substring(response.indexOf("m")+10, response.indexOf("}")-1);
    }

    @Override
    public boolean isSuccessfull() {
        return response.contains("success");
    }
    
  }
    
    
