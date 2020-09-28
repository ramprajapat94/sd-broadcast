
package com.sdigitizers.notification;

/**
 *
 * @author Shriram Prajapat
 */
public class Response {
    
    private final boolean successful;
    private final String message;

    public Response(boolean successful, String message) {
        this.successful = successful;
        this.message = message;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "Response{" + "successful=" + successful + ", message=" + message + '}';
    }
    
    
}
