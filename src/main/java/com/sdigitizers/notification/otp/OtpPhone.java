
package com.sdigitizers.notification.otp;

import com.sdigitizers.notification.utils.Phone;
import java.time.LocalDateTime;

/**
 *
 * @author Shriram Prajapat
 */
public class OtpPhone {
    
    private Phone phone;
    private String otp;
    private LocalDateTime generateTime;
    private LocalDateTime expiryTime;

    public OtpPhone() {
    }

    
    public OtpPhone(Phone phone, String otp, int expiryAfterMinutes) {
        this.phone = phone;
        this.otp = otp;
        this.expiryTime = LocalDateTime.now().plusMinutes(expiryAfterMinutes);
    }

    public OtpPhone(long phone, String otp, int expiryAfterMinutes) {
        this.phone = new Phone(phone);
        this.otp = otp;
        this.expiryTime = LocalDateTime.now().plusMinutes(expiryAfterMinutes);
    }
    
    public OtpPhone(long phone, String otp, LocalDateTime expiryTime) {
        this.phone = new Phone(phone);
        this.otp = otp;
        this.expiryTime = expiryTime;
    }
    
    
    public Phone getPhone() {
        return phone;
    }

    public void setPhone(Phone phone) {
        this.phone = phone;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public LocalDateTime getGenerateTime() {
        return generateTime;
    }

    public void setGenerateTime(LocalDateTime generateTime) {
        this.generateTime = generateTime;
    }

    public LocalDateTime getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(LocalDateTime expiryTime) {
        this.expiryTime = expiryTime;
    }
    
    
}
