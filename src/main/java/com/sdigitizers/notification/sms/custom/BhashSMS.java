
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
 public class BhashSMS implements SMS{
      
    private static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger(BhashSMS.class);
     
    private final String USERNAME;
    private final String PASSWORD;
    private final String SENDER;
    private String response = "";

    /**
     * 
     * @param userName
     * @param password
     * @param sender (Default = TESTNG)
     */
    public BhashSMS(String userName, String password, String sender) {
        this.USERNAME = userName;
        this.PASSWORD = password;
        this.SENDER = sender;
        LOGGER.info("BhashSMS - "+userName+" "+password+" "+sender);
    }
    public BhashSMS(String userName, String password){
        this(userName, password, "TESTNG");
    }
      
      //user=success&pass=123456&sender=TESTNG&phone=8638210083&text=API%20Test%20SMS&priority=ndnd&stype=normal
    @Override
    public String sendSms(String msg, String number) {
        if(!Utils.isAvailable()){
             response = "No Internet Connection available!";
             return response;
        }
        try {
                // Construct data
                String user = "user=" + USERNAME;
                String pass = "&pass=" + PASSWORD;
                String sender = "&sender=" + SENDER;
                String message = "&text=" + msg;
                String phone = "&phone=" + number;
                String priority = "&priority=" + "ndnd";
                String stype = "&stype=" + "normal";

                // Send data
                HttpURLConnection conn = (HttpURLConnection) new URL("http://bhashsms.com/api/sendmsg.php?").openConnection();
                String data = user + pass + sender + phone + message + priority + stype;
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

  }
    
    
