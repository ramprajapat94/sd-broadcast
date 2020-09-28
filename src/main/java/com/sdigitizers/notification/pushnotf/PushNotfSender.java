package com.sdigitizers.notification.pushnotf;

import com.currencyfair.onesignal.OneSignal;
import com.currencyfair.onesignal.model.notification.CreateNotificationResponse;
import com.currencyfair.onesignal.model.notification.Filter;
import com.currencyfair.onesignal.model.notification.NotificationRequest;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Manish Mundra
 */
public class PushNotfSender {

    private final PushNotfConfig config;

    public PushNotfSender(PushNotfConfig config) {
        this.config = config;
    }    
    
//    ACTION BUTTON
//    https://documentation.onesignal.com/docs/customize-action-buttons
    
    public CreateNotificationResponse send(PushNotfMessage msg){
        
        NotificationRequest notf = new NotificationRequest();
        notf.setAppId(config.getAppId());
        notf.setAndroid(config.isAndroid());
        //notf.setIos(config.isIos());
        
        if(null != msg.getHeadings() && !msg.getHeadings().isEmpty()){
            notf.setHeadings(msg.getHeadings());
        }
        
        if(null != msg.getContents() && !msg.getContents().isEmpty()){
            notf.setContents(msg.getContents());
        }
        
        if(null != msg.getData() && !msg.getData().isEmpty()){
            notf.setData(msg.getData());
        }
        
        if(null != msg.getButtons() && !msg.getButtons().isEmpty()){
            notf.setButtons(msg.getButtons());
        }
        
        List<Filter> filters = new ArrayList<>();
        filters.add(msg.getFilter());
        notf.setFilters(filters);
        
        notf.setBigPicture(msg.getBigPictureUrl());
        
        return OneSignal.createNotification(config.getRestApiKey(), notf);
    }
    
    
    public static CreateNotificationResponse send(PushNotfConfig config, PushNotfMessage msg){
        return new PushNotfSender(config).send(msg);
    }
    
    
    
    
//    public static void sendNotification(String to, String message) {
//        
//        try {
//            String jsonResponse;
//
//            URL url = new URL("https://onesignal.com/api/v1/notifications");
//            HttpURLConnection con = (HttpURLConnection) url.openConnection();
//            con.setUseCaches(false);
//            con.setDoOutput(true);
//            con.setDoInput(true);
//            
//
//            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
//            con.setRequestProperty("Authorization", "Basic ZjIyNzU0MTEtMTcxNC00NmRkLTk0YTUtZjY0ZTBlNTY1NzIz");
//            con.setRequestMethod("POST");
//
//            String strJsonBody = "{"
//                    + "\"app_id\": \"2df16d7a-6a2c-4c7a-89a7-6d6bce1170b1\","
//                    + "\"filters\": [{\"field\": \"tag\", \"key\": \"emailId\", \"relation\": \"=\", \"value\": \"" + to + "\"}],"
//                    + "\"contents\": {\"en\": \"" + message + "\"}"
//                    + "}";
//
//            System.out.println("strJsonBody:\n" + strJsonBody);
//
//            byte[] sendBytes = strJsonBody.getBytes("UTF-8");
//            con.setFixedLengthStreamingMode(sendBytes.length);
//
//            OutputStream outputStream = con.getOutputStream();
//            outputStream.write(sendBytes);
//
//            int httpResponse = con.getResponseCode();
//            System.out.println("httpResponse: " + httpResponse);
//
//            if (httpResponse >= HttpURLConnection.HTTP_OK
//                    && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
//                try (Scanner scanner = new Scanner(con.getInputStream(), "UTF-8")) {
//                    jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
//                }
//            } else {
//                try (Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8")) {
//                    jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
//                }
//            }
//            System.out.println("jsonResponse:\n" + jsonResponse);
//
//        } catch (IOException ex) {
//            System.err.println(ex);
//        }
//
//    }
}
