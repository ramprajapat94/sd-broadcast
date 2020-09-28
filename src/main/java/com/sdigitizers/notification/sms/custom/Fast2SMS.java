

package com.sdigitizers.notification.sms.custom;

//import com.mashape.unirest.http.HttpResponse;
//import com.mashape.unirest.http.Unirest;
//import com.mashape.unirest.http.exceptions.UnirestException;
import com.sdigitizers.notification.Utils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.apache.logging.log4j.LogManager;

/**
 * https://docs.fast2sms.com/?java#overview
 * @author Shriram Prajapat
 */

  public class Fast2SMS implements SMS{
      
    private static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger(Fast2SMS.class);
    /** Default SenderId = FSTSMS */
    public static final String SENDER_ID = "FSTSMS";
    
    private final String API_KEY;
    private final String SENDER;
    private String response = "";
    //private HttpResponse<String> httpResponse;

    /**
     * @param apiKey
     * @param sender (Default = FSTSMS)
     */
    public Fast2SMS(String apiKey, String sender) {
        this.API_KEY = apiKey;
        this.SENDER = sender;
        LOGGER.info("Fast2SMS - "+apiKey+"  "+sender);
    }
    
    public Fast2SMS(String apiKey) {
        this(apiKey, SENDER_ID);
    }
    
    /**
     * https://docs.fast2sms.com/?java#overview
     * @param msg
     * @param number
     * @return response
     */
    @Override
    public String sendSms(String msg, String number) {
        if(!Utils.isAvailable()){
             response = "No Internet Connection available!";
             return response;
        }
            try {
                    // Construct data
                    String sender = "sender_id=" + SENDER;
                    String message = "&message=" + msg;
                    String language = "&language=" + "english"; //unicode
                    String route = "&route=" + "p";
                    String numbers = "&numbers=" + number;
                    String flash = "&flash=" + "0";

//            "sender_id="+sender+"&message=This%20is%20a%20test%20message&language=english&route=p&numbers=8962239013%2C7773854335&flash=1"
              //Send data


                HttpURLConnection conn = (HttpURLConnection) new URL("https://www.fast2sms.com/dev/bulk").openConnection();
                String data = sender+message+language+route+numbers+flash;
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                conn.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
                conn.setRequestProperty("authorization", API_KEY);
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
                
            //this.httpResponse = httpRes;
            //this.response = httpRes.getStatusText();
            //return response;
            } catch (IOException ex) {
                LOGGER.error(ex);
            }
                return "";
    	}
      
    
    /**
     * https://docs.fast2sms.com/?java#errors
     * @param code
     * @return Error Message
     */
    @Override
    public String getErrorInfo(int code){
        switch(code){
            case 401 : return "Invalid Login Details";
            case 403 : return "No recipients specified.";
            case 404 : return "No message content.";
            case 405 : return "Message too long.";
            case 406 : return "Insufficient credits.";
            case 407 : return "Invalid schedule date.";
            case 408 : return "Schedule date is in the past.";
            case 409 : return "Invalid group ID.";
            case 410 : return "Selected group is empty.";
            case 411 : return "Invalid number format.";
            case 412 : return "You have supplied too many numbers.";
            case 413 : return "You have supplied both a group ID and a set of numbers.";
            case 414 : return "Invalid sender name.";
            case 415 : return "No sender name specified.";
            case 416 : return "No valid numbers specified.";
            case 417 : return "Schedule time is outside that allowed.";
            case 418 : return "You cannot send message at this time.";
            case 419 : return "You cannot send message at this time.";
            case 420 : return "You cannot send message at this time.";
            case 421 : return "You cannot send message at this time.";
            case 422 : return "You cannot send message at this time.";
            case 423 : return "You cannot send message at this time.";
            default : return "Undefined Error Code";
        }
    }
    

    /**
     * @return Eg. "return":true,"message":["SMS scheduled successfully at 9AM (in morning)"],"request_id":"d67nhy3i1q9bslv"}<br>
     * or {"return":true,"request_id":"4l9h8jodwb2vpgn","message":["Message has been sent successfully."]}
     */
    @Override
    public String getResponseMessage() {
        return response;//isSuccessfull()?httpResponse.getBody().substring(httpResponse.getBody().indexOf("[")+2, httpResponse.getBody().indexOf("]")-1):response;
    }

    @Override
    public boolean isSuccessfull() {
        return response.contains("true");//httpResponse.getBody().contains("true");
    }
    
//      public static void main(String[] args) {
//          String sms  = new Fast2SMS("XDuJeMUNyhPACfStz524BlL1vdwTYkspOx8WK7HabIjgR9omFibQmXeLrEPoOjTfVtRJ5vDqgzCM4Y3Z").sendSms("Hello", "7399780500");
//          System.out.println(sms);
//      }
  }
    
    
