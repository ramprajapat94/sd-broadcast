package com.sdigitizers.notification.utils;

/**
 *
 * @author Shriram Prajapat
 */
public class EmailId {
    
    private String address;
    private boolean verified;

    public EmailId() {
    }

    
    public EmailId(String address) {
        this.address = address;
    }
    
    public EmailId(String address, boolean verified) {
        this.address = address;
        this.verified = verified;
    }
    
    public String getAddress() {
        return address;
    }
    
    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }
    
    public String getDomian(){
        if(null != address && address.contains("@")){
            return getAddress().substring(getAddress().lastIndexOf("@")+1);
        }else{
            return "Invalid Email Id";
        }
    }
    
    public String print(){
        return getAddress();
    }

    @Override
    public String toString() {
        return print();
    }
    
    
 
}
