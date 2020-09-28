
package com.sdigitizers.notification.sms;


import com.sdigitizers.notification.Response;
import com.sdigitizers.notification.Utils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 *
 * @author Shriram Prajapat
 */
public class SmsSender {
    
    private final SmsConfig smsConfig;

    public SmsSender(SmsConfig smsConfig) {
        this.smsConfig = smsConfig;
    }
    
    public Response send(SmsMessage sm) {
        if (!Utils.isAvailable()) {
            return new Response(false, "No Internet Connection available!");
        }
        if (smsConfig == null) {
            return new Response(false, "Configuration Object is null");
        }
        if (smsConfig.getSmsProvider() == null) {
            return new Response(false, "Invalid SMS Provider");
        }
        if (smsConfig.getSmsProvider().getSendApiUrl() == null || smsConfig.getSmsProvider().getSendApiUrl().isEmpty() || !smsConfig.getSmsProvider().getSendApiUrl().startsWith("http")) {
            return new Response(false, "Invalid base url: "+smsConfig.getSmsProvider().getApiBaseUrl());
        }
        if (smsConfig.getAddParams() == null || smsConfig.getAddParams().isEmpty()){
            return new Response(false, "Invalid parameters set: "+smsConfig.getAddParams());
        }
        
        try {
            //Prepare parameter string 
            StringBuilder paramData = new StringBuilder();
            for(String param : smsConfig.getAddParams().keySet()){
                paramData.append("&").append(param).append("=").append(smsConfig.getAddParams().get(param)); 
            }
            paramData.append("&").append(smsConfig.getSmsProvider().getParamMessage()).append("=").append(URLEncoder.encode(sm.getMessage(), "UTF-8"));
            paramData.append("&").append(smsConfig.getSmsProvider().getParamNumber()).append("=").append(sm.getNumbersAsString());
            paramData.replace(0, 1, "?");

            //final string
            URL url = null;
            HttpURLConnection conn = null;
            if(smsConfig.getSmsProvider().getHttpRequestType().equalsIgnoreCase("GET")){
                final String finalUrl = smsConfig.getSmsProvider().getSendApiUrl() + paramData.toString();
                System.out.println(finalUrl);
                url = new URL(finalUrl);
                conn = (HttpURLConnection)url.openConnection();
            }else{
                url = new URL(smsConfig.getSmsProvider().getSendApiUrl());
                conn = (HttpURLConnection)url.openConnection();
                String data = paramData.toString().substring(1);
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                
                if(smsConfig.getSmsProvider() == SmsProvider.FAST_2_SMS){
                      conn.setRequestProperty("authorization", smsConfig.getApiKey());
                    //conn.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:67.0) Gecko/20100101 Firefox/67.0");
                }
                //conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
                conn.getOutputStream().write(data.getBytes("UTF-8"));
            }
            final StringBuffer stringBuffer;
            try (BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                stringBuffer = new StringBuffer();
                String line;
                while ((line = rd.readLine()) != null) {
                    stringBuffer.append(line);
                }
            }
            String res = stringBuffer.toString();
            return new Response(smsConfig.getSmsProvider().isSuccessful(res), "Provider Res: "+res);
        } catch (IOException e) {
            return new Response(false, "Exception: "+e.toString());
        }
    }
    
    public static Response send(SmsConfig config, SmsMessage smg){
        return new SmsSender(config).send(smg);
    }
    
    public static void main(String[] args) {
        //Map<String, String> map = new HashMap<>();
//        Response r = new SmsSender(new SmsConfig(SmsProvider.FAST_2_SMS, null, null, "XDuJeMUNyhPACfStz524BlL1vdwTYkspOx8WK7HabIjgR9omFibQmXeLrEPoOjTfVtRJ5vDqgzCM4Y3Z", "FSTSMS", "p", "english")).sendSms("Hello", "7399780500");
//        System.out.println(r.isSuccessful()+ "  "+r.getMessage());

//        SmsConfig smsConfig1 = new SmsConfig(SmsProvider.MESSAGE_INDIA, null, null, "MqGJRzc8XkOWEpBXGQB2Nw", "KEYMAN", "30", "0");
//        smsConfig1.getAddParams().put("channel", "Trans");
//        smsConfig1.getAddParams().put("flashsms", "0");
//        Response r1 = new SmsSender(smsConfig1).sendSms("Hello", "7399780500");
//        System.out.println(r1.isSuccessful()+ "  "+r1.getMessage());

//        SmsConfig smsConfig1 = new SmsConfig(SmsProvider.NEAT_DIGITAL_SMS, "TURSAP", "TURSAP@2019", null, "TURSAP", "6", null);
//        Response r1 = new SmsSender(smsConfig1).sendSms("Hello", "7399780500");
//        System.out.println(r1.isSuccessful()+ "  "+r1.getMessage());

//        SmsConfig smsConfig1 = new SmsConfig(SmsProvider.HSP_SMS, "keyman", null, "1acf69f6-9c9a-4fe9-9626-7ee8e713ca18", "KEYMAN", "TRANS", null);
//        Response r1 = new SmsSender(smsConfig1).sendSms("Hello", "7399780500");
//        System.out.println(r1.isSuccessful()+ "  "+r1.getMessage());

//        SmsConfig smsConfig1 = new SmsConfig(SmsProvider.DEAL_SMS, null, null, "NWUxZjZjZGzMNlk", "NECCON", "2", "1");
//        Response r1 = new SmsSender(smsConfig1).sendSms("Hello", "7399780500");
//        System.out.println(r1.isSuccessful()+ "  "+r1.getMessage());

        SmsConfig smsConfig1 = new SmsConfig(SmsProvider.SPRING_EDGE);
        smsConfig1.setApiKey("812jjedz773w49w****************************");
        smsConfig1.setSenderId("FAVNFH");
        SmsMessage sm = new SmsMessage(SmsType.TRANS); 
        sm.setMessage("TEST MESSAG");
        sm.setNumbers("7399780500");
        Response r1 = new SmsSender(smsConfig1).send(sm);
        System.out.println(r1.isSuccessful()+ "  "+r1.getMessage());
    }
            
    
//            HttpResponse<String> httpRes = Unirest.post("https://www.fast2sms.com/dev/bulk")
//                .header("authorization", API_KEY)
//                .header("Content-Type", "application/x-www-form-urlencoded")
//                .body(sender+message+language+route+numbers+flash)
//                .asString();
}
