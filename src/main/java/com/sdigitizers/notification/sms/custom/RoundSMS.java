
package com.sdigitizers.notification.sms.custom;

import com.sdigitizers.notification.Utils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.apache.logging.log4j.LogManager;

/**
 * @see http://roundsms.in/easy-api.php
 * @author Shriram Prajapat
 */
 public class RoundSMS implements SMS{
      
    private static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger(RoundSMS.class);
    /** Default SenderId = RNDSMS */
    public static final String SENDER_ID = "RNDSMS";
     
    private final String AUTH_KEY;
    private final String SENDER;
    private final DCS type;
    private final RoundSmsRoute route;
    private String response = "";

    /**
     * @param authKey
     * @param sender (Default = RNDSMS)
     * @param type
     * @param route
     */
    public RoundSMS(String authKey, String sender, DCS type, RoundSmsRoute route) {
        this.AUTH_KEY = authKey;
        this.SENDER = sender;
        this.type = type;
        this.route = route;
        LOGGER.info("RoundSMS - "+authKey+" "+sender);
    }
    /**
     * @param authKey
     * @param type
     * @param route
     */
    public RoundSMS(String authKey, DCS type, RoundSmsRoute route){
        this(authKey, "RNDSMS", type, route);
    }
    /**
     * http://roundsms.in/easy-api.php
     * @param msg
     * @param numbers
     * @return 
     */
    @Override
    public String sendSms(String msg, String numbers) {
        if(!Utils.isAvailable()){
             response = "No Internet Connection available!";
             return response;
        }
        
//        StringBuilder numbers = new StringBuilder("");
//        for(long l : mobileNumbers){
//            numbers.append(",").append(String.valueOf(l));
//        }
//        numbers.deleteCharAt(0);
        
        try {
                // Construct data
                String authKey = "authKey=" + this.AUTH_KEY;
                String mobiles = "&mobiles=" + numbers;
                String message = "&message=" + msg;
                String sender = "&sender=" + this.SENDER;
                String msgType = "&type=" + this.type.getCode();
                String msgRoute = "&route=" + this.route.getCode();
                
                // Send data
                HttpURLConnection conn = (HttpURLConnection) new URL("http://roundsms.in/api/sendhttp.php?").openConnection();
                String data = authKey + mobiles + message + sender + msgType + msgRoute;
                conn.setDoOutput(true);
                conn.setRequestMethod("GET");
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
                LOGGER.error("Error sending sms : ", e);
                return "Error "+e;
        }
   }

    @Override
    public String getErrorInfo(int code){
        switch(code){
            default: return "No error code(s) are defined";
        }
    }
    

    @Override
    public String getResponseMessage() {
        return response;
    }

    @Override
    public boolean isSuccessfull() {
        return response.startsWith("S.");
    }
    
    /**
     * Data Coding Style (Normal=0 / Unicode=2)
     */
    public static enum DCS{
        NORMAL(1), UNICODE(2);
        private final int code;
        private DCS(int code){
            this.code = code;
        }
        public int getCode(){
            return code;
        }
    }
    
    public static enum RoundSmsRoute{
        Promotional(1), Transactional(2), PromoSenderId(3);
        private final int code;
        private RoundSmsRoute(int code){
            this.code = code;
        }
        public int getCode(){
            return code;
        }
    }

  }
    
    
