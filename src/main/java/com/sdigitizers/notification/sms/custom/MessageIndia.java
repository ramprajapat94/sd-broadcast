package com.sdigitizers.notification.sms.custom;

import com.sdigitizers.notification.Utils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.apache.logging.log4j.LogManager;

/**
 * @see http://xpressdndsms.com/
 * @see http://sms.messageindia.in/Web/WebAPI/WEBAPIHTTP.aspx
 * @author Shriram Prajapat
 */
public class MessageIndia implements SMS {

    private static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger(MessageIndia.class);
    /** Default SenderId = WEBSMS */
    public static final String SENDER_ID = "WEBSMS";
    
    private final String APIKEY;
    private final String USERNAME;
    private final String PASSWORD;
    private final String SENDER;
    private final String ROUTE;// = "35";
    private Channel channel = Channel.Trans;
    private DCS dcs = DCS.Normal;
    private FlashSms flash = FlashSms.Normal;
    

    private String response = "";

    /**
     *
     * @param userName
     * @param password
     * @param APIKey
     * @param sender (Default = WEBSMS)
     * @param route
     */
    public MessageIndia(String userName, String password, String APIKey, String sender, String route) {
        this.USERNAME = userName;
        this.PASSWORD = password;
        this.APIKEY = APIKey;
        this.SENDER = sender;
        this.ROUTE = route;
        LOGGER.info("MessageIndia - " + userName + " " + password + " " + sender);
    }

    public void setChannel(Channel ch) {
        this.channel = Channel.Trans;
    }

    public void setDCS(DCS dcs) {
        this.dcs = dcs;
    }

    public void setFlashSms(FlashSms fs) {
        this.flash = fs;
    }
    //http://sms.messageindia.in/api/mt/SendSMS?user=Atikur&password=Atikur&senderid=WEBSMS&channel=Trans&DCS=0&flashsms=0&number=917399780500&text=test message&route=35

    @Override
    public String sendSms(String msg, String number) {
        if (!Utils.isAvailable()) {
            response = "No Internet Connection available!";
            return response;
        }
        try {
            // Construct data

            String data = URLEncoder.encode("APIKey", "UTF-8") + "=" + URLEncoder.encode(APIKEY, "UTF-8");
            data += "&" + URLEncoder.encode("senderid", "UTF-8") + "=" + URLEncoder.encode(SENDER, "UTF-8");
            data += "&" + URLEncoder.encode("channel", "UTF-8") + "=" + URLEncoder.encode(channel.toString(), "UTF-8");
            data += "&" + URLEncoder.encode("DCS", "UTF-8") + "=" + URLEncoder.encode("0", "UTF-8");
            data += "&" + URLEncoder.encode("flashsms", "UTF-8") + "=" + URLEncoder.encode(flash.getCode()+"", "UTF-8");
            data += "&" + URLEncoder.encode("number", "UTF-8") + "=" + URLEncoder.encode(number, "UTF-8");
            data += "&" + URLEncoder.encode("text", "UTF-8") + "=" + URLEncoder.encode(msg, "UTF-8");
            data += "&" + URLEncoder.encode("route", "UTF-8") + "=" + URLEncoder.encode(ROUTE, "UTF-8");
            String url1 = "http://sms.indiatext.in/api/mt/SendSMS";
            url1 += "?" + data;
            URL url = new URL(url1);
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
            String user = "user=" + USERNAME;
            String pass = "&password=" + PASSWORD;
            String sender = "&senderid=" + SENDER;
            String channelName = "&channel=" + channel.toString();
            String dcsValue = "&dcs=" + dcs.getCode();
            String flashsms = "&flashsms=" + flash.getCode();
            String phone = "&number=" + number;
            String message = "&text=" + msg;
            String route = "&route=" + ROUTE;
            String schdTime = "&schedtime=" + DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm:ss a").format(time);

            // Send data
            String data = user + pass + sender + channelName + dcsValue + flashsms + phone + message + schdTime + route;
            HttpURLConnection conn = (HttpURLConnection) new URL("http://sms.messageindia.in/api/mt/SendSMS?"+data).openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("GET");
            //conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
            //conn.getOutputStream().write(data.getBytes("UTF-8"));
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
            return "Error " + e;
        }
    }

    public String checkDelivery(String jobId) {
        if (!Utils.isAvailable()) {
            response = "No Internet Connection available!";
            return response;
        }
        try {
            // Construct data
            String user = "user=" + USERNAME;
            String pass = "&password=" + PASSWORD;
            String job = "&jobid=" + jobId;

            // Send data
            String data = user + pass + job;
            HttpURLConnection conn = (HttpURLConnection) new URL("http://sms.messageindia.in/api/mt/GetDelivery?"+data).openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("GET");
            //conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
            //conn.getOutputStream().write(data.getBytes("UTF-8"));
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
            LOGGER.error("Error Checking delivery report sms : ", e);
            return "Error " + e;
        }
    }

    public String checkBalance() {
        if (!Utils.isAvailable()) {
            response = "No Internet Connection available!";
            return response;
        }
        try {
            // Construct data
            String user = "user=" + USERNAME;
            String pass = "&password=" + PASSWORD;

            // Send data
            String data = user + pass;
            HttpURLConnection conn = (HttpURLConnection) new URL("http://sms.messageindia.in/api/mt/GetBalance?"+URLEncoder.encode(data, "UTF-8")).openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("GET");
            //conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
            //conn.getOutputStream().write(data.getBytes("UTF-8"));
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

//    {"ErrorCode":"000","ErrorMessage":"Done","JobId":"3906134","MessageData":[{"Number":"917399780500","MessageId":"855efzOFmk64Pf6sGO8WXQ"}]}
    @Override
    public boolean isSuccessfull() {
        System.out.println(response);
        LOGGER.info(response);
        return (response.substring(14,17).equalsIgnoreCase("000"));
    }

    /**
     * Message channel (promotional or transactional)
     */
    public static enum Channel {
        Promo, Trans;
    }

    /**
     * Data coding value (Default is 0 for normal message, Set 8 for unicode
     * sms)
     */
    public static enum DCS {
        Normal(0), Unicode(8);
        private final int code;

        DCS(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }

    /**
     * Flash message immediate display (Default is 0 for normal sms, Set 1 for
     * immediate display)
     */
    public static enum FlashSms {
        Normal(0), ImmediateDisplay(1);
        private final int code;

        FlashSms(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }

//    public static void main(String[] args) {
//        SMS s = new MessageIndia("Atikur", "Atikur");
//        String res = s.sendSms("test", "7399780500");
//        System.out.println(res);
//        System.out.println(s.isSuccessfull());
//    }
}
