
package com.sdigitizers.notification.utils;

/**
 *
 * @author Shriram Prajapat
 */
public class Phone {
    
    private String countryCode;
    private long number;
    private boolean verified;

    public Phone() {
    }
    
    public Phone(String countryCode, long number, boolean verified) {
        this.countryCode = countryCode;
        this.number = number;
        this.verified = verified;
    }
    
    public Phone(String countryCode, long number) {
        this.countryCode = countryCode;
        this.number = number;
    }
    
    public Phone(long number, boolean verified) {
        this("91", number, verified);
    }
    
    public Phone(long number) {
        this("91", number);
    }
    
    
    public String getCountryCode() {
        return countryCode;
    }

    public long getNumber() {
        return number;
    }

    public boolean isVerified() {
        return verified;
    }

    public Phone setVerified(boolean verified) {
        this.verified = verified;
        return this;
    }
    
//    public boolean isValid(){
//        return 
//    }
    
    /**
     * @return eg: 7002592263
     */
    public String print(){
        return String.format("%010d", number);
    }
    
    private String[] breakUp(){
        String pr = print();
        String[] s = new String[3];
        s[0] = pr.substring(0, 3);
        s[1] = pr.substring(3, 7);
        s[2] = pr.substring(7);
        return s;
    }
    
    public String print1(){
        String[] bk = breakUp();
        return bk[0] + " " + bk[1] + " " + bk[2];
    }
    
    public String printWithCC(){
        return countryCode+"  "+print1();
    }
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + (int) (this.number ^ (this.number >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Phone other = (Phone) obj;
        return this.number == other.number;
    }

    @Override
    public String toString() {
        return print1();
    }
    
}
