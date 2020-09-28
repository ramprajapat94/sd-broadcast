
package com.sdigitizers.notification.sms;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Shriram Prajapat
 */
public enum SmsProvider implements SmsProviderInt{
    
    BHASH_SMS{
        @Override
        public String getCompanyName() {
            return "BHASH SMS";
        }

        @Override
        public String getWebsite() {
            return "http://bhashsms.com/";
        }

        @Override
        public String getPortalUrl() {
            return "http://bhashsms.com/loginlanding.php";
        }

        @Override
        public boolean isSuccessful(String response) {
            return response.startsWith("S.");
        }

        @Override
        public Map<String, String> getErrorCodes() {
            return new HashMap<>(0);
        }

        @Override
        public String getHttpRequestType() {
            return "POST";
        }
        
        @Override
        public String getApiBaseUrl() {
            return "http://bhashsms.com/api/";
        }

        @Override
        public String getSendApiUrl() {
            return getApiBaseUrl()+"sendmsg.php";
        }
    
        //
        @Override
        public String getCheckBalanceUrl() {
            return getApiBaseUrl()+"checkbalance.php"; //?user=?&pass=?
        }
        
        @Override
        public String getParamMessage() {
            return "text";
        }

        @Override
        public String getParamNumber() {
            return "phone";
        }

        @Override
        public String getParamUser() {
            return "user";
        }

        @Override
        public String getParamPassword() {
            return "pass";
        }

        @Override
        public String getParamApiKey() {
            return null;
        }

        @Override
        public String getParamSenderId() {
            return "sender";
        }

        @Override
        public String getParamRoute() {
            return "priority";
        }

        @Override
        public String getParamLanguage() {
            return "stype";
        }
    }, 
    
    
    TEXTLOCAL{
        @Override
        public String getCompanyName() {
            return "TEXT LOCAL";
        }

        @Override
        public String getWebsite() {
            return "https://www.textlocal.in";
        }

        @Override
        public String getPortalUrl() {
            return "https://control.textlocal.in/";
        }

        @Override
        public boolean isSuccessful(String response) {
            return response.contains("success");
        }
        
        public String getResponseMessage(String response) {
            return response.substring(response.indexOf("m")+10, response.indexOf("}")-1);
        }

        @Override
        public Map<String, String> getErrorCodes() {
            Map<String, String> codes = new HashMap<>(17);
                codes.put("3", "Invalid Login Details");
                codes.put("4", "No recipients specified");
                codes.put("5", "No message content");
                codes.put("6", "Message too long");
                codes.put("7", "Insufficient credits");
                codes.put("8", "Invalid schedule date");
                codes.put("9", "Schedule date is in the past");
                codes.put("10", "Invalid group ID");
                codes.put("11", "Selected group is empty");
                codes.put("32", "Invalid number format");
                codes.put("33", "You have supplied too many numbers");
                codes.put("34", "You have supplied both a group ID and a set of numbers");
                codes.put("43", "Invalid sender name");
                codes.put("44", "No sender name specified");
                codes.put("51", "INo valid numbers specified");
                codes.put("191", "Schedule time is outside that allowed");
                codes.put("192", "You cannot send message at this time");
            return codes;
        }

        @Override
        public String getHttpRequestType() {
            return "POST";
        }

        @Override
        public String getApiBaseUrl() {
            return "https://api.textlocal.in/";
        }

        /**
         * https://control.textlocal.in/docs/api/code/post/
         */
        @Override
        public String getSendApiUrl() {
            return "http://api.textlocal.in/send/?";
        }

        /**
         * https://control.textlocal.in/docs/api/code/credits/
         */
        @Override
        public String getCheckBalanceUrl() {
            return "http://api.textlocal.in/balance/?";
        }
        
        @Override
        public String getParamUser() {
            return "username";
        }

        @Override
        public String getParamPassword() {
            return null;
        }

        @Override
        public String getParamApiKey() {
            return "hash";
        }

        @Override
        public String getParamSenderId() {
            return "sender";
        }

        @Override
        public String getParamRoute() {
            return null;
        }

        @Override
        public String getParamLanguage() {
            return null;
        }

        @Override
        public String getParamMessage() {
            return "message";
        }

        @Override
        public String getParamNumber() {
            return "numbers";
        }
    },
    
    
    FAST_2_SMS{
        @Override
        public String getCompanyName() {
            return "FAST 2 SMS";
        }

        @Override
        public String getWebsite() {
            return "https://www.fast2sms.com/";
        }

        @Override
        public String getPortalUrl() {
            return "https://www.fast2sms.com/";
        }

        @Override
        public boolean isSuccessful(String response) {
            return response.contains("true");
        }
        /**
         * @see https://docs.fast2sms.com/#error-codes
         * @return 
         */
        @Override
        public Map<String, String> getErrorCodes() {
            Map<String, String> codes = new HashMap<>(23);
                codes.put("401", "Sender ID Missing");
                codes.put("402", "Message Text Missing");
                codes.put("403", "Route Missing");
                codes.put("404", "Language Missing");
                codes.put("405", "Numbers Missing");
                codes.put("406", "Invalid Sender ID");
                codes.put("407", "Invalid words used in message");
                codes.put("408", "Invalid Route");
                codes.put("409", "Invalid Route Authentication");
                codes.put("410", "Invalid Language");
                codes.put("411", "Invalid Numbers");
                codes.put("412", "Invalid Authentication, Check Authorization Key");
                codes.put("413", "Invalid Authentication, Authorization Key Disabled");
                codes.put("414", "IP is blacklisted from Dev API section");
                codes.put("415", "Account Disabled");
                codes.put("416", "You don't have sufficient wallet balance");
                codes.put("417", "Use english letters or change language to unicode");
                codes.put("418", "Invalid Quick Transactional Template ID");
                codes.put("419", "Quick Transactional Template Variables Missing");
                codes.put("420", "Invalid Quick Transactional Variables");
                codes.put("421", "Quick Transactional Template Variable Values Missing");
                codes.put("422", "Invalid Variable Values or Some Values Missing");
                codes.put("423", "Transactional Route is active, Use Bulk SMS API");
            return codes;
        }

        @Override
        public String getHttpRequestType() {
            return "POST";
        }

        /**
         * https://docs.fast2sms.com/
         */
        @Override
        public String getApiBaseUrl() {
            return "https://www.fast2sms.com/dev/";
        }

        @Override
        public String getSendApiUrl() {
            return "https://www.fast2sms.com/dev/bulk";
        }

        /**
         * https://www.fast2sms.com/dev/wallet?authorization=YOUR_API_KEY
         */
        @Override
        public String getCheckBalanceUrl() {
            return "https://www.fast2sms.com/dev/wallet";
        }
        
        @Override
        public String getParamMessage() {
            return "message";
        }

        @Override
        public String getParamNumber() {
            return "numbers";
        }
        
        @Override
        public String getParamUser() {
            return null;
        }

        @Override
        public String getParamPassword() {
            return null;
        }

        @Override
        public String getParamApiKey() {
            return null;
        }

        @Override
        public String getParamSenderId() {
            return "sender_id";
        }

        @Override
        public String getParamRoute() {
            return "route";
        }

        @Override
        public String getParamLanguage() {
            return "language";
        }

        
    },
    
    
    ROUND_SMS{
        @Override
        public String getCompanyName() {
            return "ROUND SMS";
        }

        @Override
        public String getWebsite() {
            return "http://roundsms.in/";
        }

        @Override
        public String getPortalUrl() {
            return "http://roundsms.in/login.php";
        }

        @Override
        public boolean isSuccessful(String response) {
            return (response.contains("\"error\":\"null\""));
        }

        @Override
        public Map<String, String> getErrorCodes() {
            return new HashMap<>(0);
        }

        @Override
        public String getHttpRequestType() {
            return "GET";
        }

        /**
         * http://roundsms.in/easy-api.php
         */
        @Override
        public String getApiBaseUrl() {
            return "http://roundsms.in/api/";
        }

        /**
         * http://roundsms.in/api/checkcredits.php?authkey=ZTBmZjFmNzUwNjB
         */
        @Override
        public String getCheckBalanceUrl() {
            return "http://roundsms.in/api/checkcredits.php";
        }
        
        @Override
        public String getSendApiUrl() {
            return "http://roundsms.in/api/sendhttp.php";
        }

        @Override
        public String getParamUser() {
            return null;
        }

        @Override
        public String getParamPassword() {
            return null;
        }

        @Override
        public String getParamApiKey() {
            return "authkey";
        }
        
        /**
         * default RNDSMS
         */
        @Override
        public String getParamSenderId() {
            return "sender";
        }

        /**
         * Promotional(1), Transactional(2), PromoSenderId(3)
         */
        @Override
        public String getParamRoute() {
            return "route";
        }

        /**
        * Data Coding Style (Normal=1 / Unicode=2)
        */
        @Override
        public String getParamLanguage() {
            return "type";
        }

        @Override
        public String getParamMessage() {
            return "message";
        }

        @Override
        public String getParamNumber() {
            return "mobiles";
        }
        
    }, 
    
    
    MESSAGE_INDIA{
        @Override
        public String getCompanyName() {
            return "MESSAGE INDIA";
        }

        @Override
        public String getWebsite() {
            return "http://xpressdndsms.com/";
        }

        @Override
        public String getPortalUrl() {
            return "http://sms.messageindia.in/Account/Login";
        }

        //{"ErrorCode":"000","ErrorMessage":"Done","JobId":"3906134","MessageData":[{"Number":"917399780500","MessageId":"855efzOFmk64Pf6sGO8WXQ"}]}
        @Override
        public boolean isSuccessful(String response) {
            return (response.substring(14,17).equalsIgnoreCase("000"));
        }

        @Override
        public Map<String, String> getErrorCodes() {
            Map<String, String> map = new HashMap<>();
                map.put("000", "Done");
                map.put("001", "login details cannot be blank");
                map.put("003", "sender cannot be blank");
                map.put("004", "message text cannot be blank");
                map.put("005", "message data cannot be blank");
                map.put("006", "error: generic error description");
                map.put("007", "username or password is invalid");
                map.put("008", "account not active");
                map.put("009", "account locked, contact your account manager");
                map.put("010", "api restriction");
                map.put("011", "ip address restriction");
                map.put("012", "invalid length of message text");
                map.put("013", "mobile numbers not valid");
                map.put("014", "account locked due to spam message contact support");
                map.put("015", "senderid not valid");
                map.put("017", "groupid not valid");
                map.put("018", "multi message to group is not supported");
                map.put("019", "schedule date is not valid");
                map.put("020", "message or mobile number cannot be blank");
                map.put("021", "insufficient credits");
                map.put("022", "invalid jobid");
                map.put("023", "parameter missing");
                map.put("024", "invalid template or template mismatch");
                map.put("025", "{Field} can not be blank or empty");
                map.put("026", "invalid date range");
                map.put("027", "invalid optin user");
            return map;
        }

        @Override
        public String getHttpRequestType() {
            return "GET";
        }

        @Override
        public String getApiBaseUrl() {
            return "http://sms.messageindia.in/api/mt/";
        }

        @Override
        public String getSendApiUrl() {
            return "http://sms.indiatext.in/api/mt/SendSMS";
        }

        @Override
        public String getParamUser() {
            return null;
            //return "user";
        }

        @Override
        public String getParamPassword() {
            return null;
            //return "password";
        }

        @Override
        public String getParamApiKey() {
            return "APIKey";
        }

        @Override
        public String getParamSenderId() {
            return "senderid";
        }

        @Override
        public String getParamRoute() {
            return "route";
        }

        /**
         * Normal=0, Unicode=8
         */
        @Override
        public String getParamLanguage() {
            return "dcs";
        }

        @Override
        public String getParamMessage() {
            return "text";
        }

        @Override
        public String getParamNumber() {
            return "number";
        }
        
        /**
         * Promo / Trans
         */
        public String getParamChannel() {
            return "channel";
        }
        /**
         * Normal(0), ImmediateDisplay(1)
         */
        public String getParamFlashSms() {
            return "flashsms";
        }
        /**
         * DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm:ss a").format(time);
         */
        public String getParamScheduleTime(){
            return "schedtime";
        }
        
        @Override
        public String getCheckBalanceUrl() {
            return "http://sms.messageindia.in/api/mt/GetBalance"; //?user=user&password=pass
        }
    },
    
    
    FREE_BEE_SMS{
        @Override
        public String getCompanyName() {
            return "FREE BEE SMS";
        }

        @Override
        public String getWebsite() {
            return "http://neat.freebeesms.com/";
        }

        @Override
        public String getPortalUrl() {
            return "http://neat.freebeesms.com/";
        }

        @Override
        public boolean isSuccessful(String response) {
            return !(response.contains("\"type\":\"error\""));
        }

        @Override
        public Map<String, String> getErrorCodes() {
            Map<String, String> map = new HashMap<>(2);
            //Missing parameters
            map.put("101", "Missing Authkey");
            map.put("102", "Missing Route");
            map.put("103", "Missing Current Password");
            map.put("104", "Missing New Password");
            //Invalid parameters
            map.put("201", "Invalid Authkey");
            map.put("202", "Invalid Route");
            map.put("204", "Invalid mobile number(s)");
            
            map.put("301", "Authentication Failed");
            map.put("302", "Expired User Account");
            map.put("303", "Banned User Account");
            map.put("304", "Wrong Current Password");
            //System Errors
            map.put("001", "Unable To Connect Database");
            map.put("002", "Unable To Select Database");
            return map;
        }

        @Override
        public String getHttpRequestType() {
            return "GET";
        }

        @Override
        public String getApiBaseUrl() {
            return "http://neat.freebeesms.com/api/";
        }

        /**
         * http://neat.freebeesms.com/api_docs/check_balance
         */
        @Override
        public String getCheckBalanceUrl() {
            return "http://neat.freebeesms.com/api/balance.php"; //?authkey=YourAuthKey&route=A
        }
        
        @Override
        public String getSendApiUrl() {
            return "http://neat.freebeesms.com/api/send_http.php";
        }

        @Override
        public String getParamUser() {
            return null;
        }

        @Override
        public String getParamPassword() {
            return null;
        }

        @Override
        public String getParamApiKey() {
            return "authkey";
        }

        @Override
        public String getParamSenderId() {
            return "sender";
        }

        @Override
        public String getParamRoute() {
            return "route";
        }

        @Override
        public String getParamLanguage() {
            return null;
        }

        @Override
        public String getParamMessage() {
            return "message";
        }

        @Override
        public String getParamNumber() {
            return "mobiles";
        }
       
    },
    
    
    NEAT_DIGITAL_SMS{
        @Override
        public String getCompanyName() {
            return "NEAT DIGITAL SMS";
        }

        @Override
        public String getWebsite() {
            return "http://sms.neatdigital.in/";
        }

        @Override
        public String getPortalUrl() {
            return "http://sms.neatdigital.in/";
        }

        @Override
        public boolean isSuccessful(String response) {
            return (response.contains("Message Submitted Successfully"));
        }

        @Override
        public Map<String, String> getErrorCodes() {
            Map<String, String> map = new HashMap<>(2);
            map.put("301", "Authentication Error");
            map.put("204", "Invalid mobile number(s)");
            return map;
        }

        @Override
        public String getHttpRequestType() {
            return "GET";
        }

        /**
         * http://sms.neatdigital.in/Dashboard.php?page=apicode
         */
        @Override
        public String getApiBaseUrl() {
            return "http://sms.neatdigital.in/";
        }

        /**
         * http://sms.neatdigital.in/http-credit.php?username=enterusername&password=enterpassword&route_id=route-id
         */
        @Override
        public String getCheckBalanceUrl() {
            return "http://sms.neatdigital.in/http-credit.php"; //?username=enterusername&password=enterpassword&route_id=route-id
        }
        
        @Override
        public String getSendApiUrl() {
            return "http://sms.neatdigital.in/http-api.php";
        }

        @Override
        public String getParamUser() {
            return "username";
        }

        @Override
        public String getParamPassword() {
            return "password";
        }

        @Override
        public String getParamApiKey() {
            return null;
        }

        @Override
        public String getParamSenderId() {
            return "senderid";
        }

        @Override
        public String getParamRoute() {
            return "route";
        }

        @Override
        public String getParamLanguage() {
            return null;
        }

        @Override
        public String getParamMessage() {
            return "message";
        }

        @Override
        public String getParamNumber() {
            return "number";
        }     
    },
    
    
    HSP_SMS{
        @Override
        public String getCompanyName() {
            return "HSP SMS";
        }

        @Override
        public String getWebsite() {
            return "http://sms.hspsms.com/";
        }

        @Override
        public String getPortalUrl() {
            return "http://sms.hspsms.com/login";
        }

        // [{"responseCode":"Message SuccessFully Submitted"},{"msgid":"427132937"}]
        @Override
        public boolean isSuccessful(String response) {
            return (response.contains("SuccessFully Submitted"));
        }

        @Override
        public Map<String, String> getErrorCodes() {
            Map<String, String> map = new HashMap<>(26);
                map.put("000", "Done");
                map.put("001", "login details cannot be blank");
                map.put("003", "sender cannot be blank");
                map.put("004", "message text cannot be blank");
                map.put("005", "message data cannot be blank");
                map.put("006", "error: generic error description");
                map.put("007", "username or password is invalid");
                map.put("008", "account not active");
                map.put("009", "account locked, contact your account manager");
                map.put("010", "api restriction");
                map.put("011", "ip address restriction");
                map.put("012", "invalid length of message text");
                map.put("013", "mobile numbers not valid");
                map.put("014", "account locked due to spam message contact support");
                map.put("015", "senderid not valid");
                map.put("017", "groupid not valid");
                map.put("018", "multi message to group is not supported");
                map.put("019", "schedule date is not valid");
                map.put("020", "message or mobile number cannot be blank");
                map.put("021", "insufficient credits");
                map.put("022", "invalid jobid");
                map.put("023", "parameter missing");
                map.put("024", "invalid template or template mismatch");
                map.put("025", "{Field} can not be blank or empty");
                map.put("026", "invalid date range");
                map.put("027", "invalid optin user");
            return map;
        }

        @Override
        public String getHttpRequestType() {
            return "GET";
        }

        @Override
        public String getApiBaseUrl() {
            return "http://sms.hspsms.com";
        }

        @Override
        public String getCheckBalanceUrl() {
            return "http://sms.hspsms.com/getSMSCredit"; //?username=keyman&apikey=1acf69f6-9c9a-4fe9-9626-7ee8e713ca18
        }
        
        @Override
        public String getSendApiUrl() {
            return "http://sms.hspsms.com/sendSMS";
        }

        @Override
        public String getParamUser() {
            return "username";
        }

        @Override
        public String getParamPassword() {
            return null;
        }

        @Override
        public String getParamApiKey() {
            return "apikey";
        }

        @Override
        public String getParamSenderId() {
            return "sendername";
        }

        @Override
        public String getParamRoute() {
            return "smstype";
        }

        @Override
        public String getParamLanguage() {
            return null;
        }

        @Override
        public String getParamMessage() {
            return "message";
        }

        @Override
        public String getParamNumber() {
            return "numbers";
        }        
    },
    
    
    DEAL_SMS{
        @Override
        public String getCompanyName() {
            return "DEALS SMS";
        }

        @Override
        public String getWebsite() {
            return "http://www.dealsms.in/";
        }

        @Override
        public String getPortalUrl() {
            return "http://sms.dealsms.in/login.php";
        }

        @Override
        public boolean isSuccessful(String response) {
            return (response.contains("\"error\":null"));
        }

        @Override
        public Map<String, String> getErrorCodes() {
            return new HashMap<>(1);
        }

        @Override
        public String getHttpRequestType() {
            return "GET";
        }

        /**
         * http://sms.dealsms.in/user/api.php 
         */
        @Override
        public String getApiBaseUrl() {
            return "http://sms.dealsms.in/api/";
        }
        
        /**
         * http://sms.dealsms.in/api/sendhttp.php?authkey=NWUxZjZjZGzMNlk&mobiles=7002592263&message=HelloRam&sender=NECCON&type=1&route=2
         */
        @Override
        public String getSendApiUrl() {
            return "http://sms.dealsms.in/api/sendhttp.php";
        }

        @Override
        public String getParamUser() {
            return null;
        }

        @Override
        public String getParamPassword() {
            return null;
        }

        @Override
        public String getParamApiKey() {
            return "authkey";
        }

        @Override
        public String getParamSenderId() {
            return "sender";
        }
        // (1-Promo, 2-Trans)
        @Override
        public String getParamRoute() {
            return "route";
        }
        // (1=Normal / 2=Unicode)
        @Override
        public String getParamLanguage() {
            return "type";
        }

        @Override
        public String getParamMessage() {
            return "message";
        }

        @Override
        public String getParamNumber() {
            return "mobiles";
        }
        
        //http://sms.dealsms.in/api/checkcredits.php?authkey=NWUxZjZjZGMzNzk
        @Override
        public String getCheckBalanceUrl() {
            return "http://sms.dealsms.in/api/checkcredits.php"; //?authkey=NWUxZjZjZGMzNzk
        }
        
        
    },
    
    
    SPRING_EDGE{
        @Override
        public String getCompanyName() {
            return "SPRING EDGE";
        }

        @Override
        public String getWebsite() {
            return "https://www.springedge.com/";
        }

        @Override
        public String getPortalUrl() {
            return "http://web.springedge.com/";
        }

        @Override
        public boolean isSuccessful(String response) {
            return (response.contains("\"error\":null"));
        }

        @Override
        public Map<String, String> getErrorCodes() {
            return new HashMap<>(1);
        }

        @Override
        public String getHttpRequestType() {
            return "GET";
        }

        /**
         * http://instantalerts.co/api/web/send/ 
         */
        @Override
        public String getApiBaseUrl() {
            return "http://instantalerts.co/api/web/";
        }
        
        /**
         * http://instantalerts.co/api/web/send/?apikey="+apikey
         */
        @Override
        public String getSendApiUrl() {
            return "http://instantalerts.co/api/web/send/";
        }

        @Override
        public String getParamUser() {
            return null;
        }

        @Override
        public String getParamPassword() {
            return null;
        }

        @Override
        public String getParamApiKey() {
            return "apikey";
        }

        @Override
        public String getParamSenderId() {
            return "sender";
        }
        
        @Override
        public String getParamRoute() {
            return null;
        }
        
        @Override
        public String getParamLanguage() {
            return null;
        }

        @Override
        public String getParamMessage() {
            return "message";
        }

        @Override
        public String getParamNumber() {
            return "to";
        }
        
        //http://<SMS_Service_URL>/api/status/credit?apikey=<API_KEY>
        @Override
        public String getCheckBalanceUrl() {
            return "http://instantalerts.co/api/status/credit"; //?apikey==NWUxZjZjZGMzNzk
        }
        
        
    },
    
}
