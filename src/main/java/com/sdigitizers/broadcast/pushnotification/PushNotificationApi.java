package com.sdigitizers.broadcast.pushnotification;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 *
 * @author Manish Mundra
 */
public class PushNotificationApi {

    public static void sendNotification(String to, String message) {

        try {
            String jsonResponse;

            URL url = new URL("https://onesignal.com/api/v1/notifications");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setUseCaches(false);
            con.setDoOutput(true);
            con.setDoInput(true);
            

            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setRequestProperty("Authorization", "Basic NzQ1YTk5YTktOWJlNi00ZWYxLTg4MzEtZWU4ZjhiMWZiNTlm");
            con.setRequestMethod("POST");

            String strJsonBody = "{"
                    + "\"app_id\": \"f5083c4d-8aec-4d19-9588-15fb4bc7dfd0\","
                    + "\"filters\": [{\"field\": \"tag\", \"key\": \"emailId\", \"relation\": \"=\", \"value\": \"" + to + "\"}],"
                    + "\"contents\": {\"en\": \"" + message + "\"}"
                    + "}";

            System.out.println("strJsonBody:\n" + strJsonBody);

            byte[] sendBytes = strJsonBody.getBytes("UTF-8");
            con.setFixedLengthStreamingMode(sendBytes.length);

            OutputStream outputStream = con.getOutputStream();
            outputStream.write(sendBytes);

            int httpResponse = con.getResponseCode();
            System.out.println("httpResponse: " + httpResponse);

            if (httpResponse >= HttpURLConnection.HTTP_OK
                    && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
                try (Scanner scanner = new Scanner(con.getInputStream(), "UTF-8")) {
                    jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                }
            } else {
                try (Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8")) {
                    jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                }
            }
            System.out.println("jsonResponse:\n" + jsonResponse);

        } catch (IOException ex) {
            System.err.println(ex);
        }

    }
}
