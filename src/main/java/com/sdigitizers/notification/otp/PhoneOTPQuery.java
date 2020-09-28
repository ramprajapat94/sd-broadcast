
package com.sdigitizers.notification.otp;

import com.sdigitizers.notification.Response;
import com.sdigitizers.notification.utils.Phone;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Shriram Prajapat
 */
public class PhoneOTPQuery {
    
    private final Connection conn;
    private final String tableName;
    private final int defaultTimeLimit;
    
    private final String CREATE;
    private final String ADD;
    private final String UPDATE;
    private final String SELECT_ALL;
    
    public PhoneOTPQuery(Connection con, String tableName, int defaultTimeLimit){
        this.conn = con;
        this.tableName = tableName;
        this.defaultTimeLimit = defaultTimeLimit;
        
        CREATE = "CREATE TABLE `"+tableName+"` (`phone` bigint(13) unsigned NOT NULL, `otp` char(8) NOT NULL, " +
                "  `tolu` timestamp NULL DEFAULT CURRENT_TIMESTAMP,   `validity` timestamp NULL, " +
                "   PRIMARY KEY (`phone`) );";
        ADD = "INSERT INTO "+tableName+"(phone, otp, validity) VALUES(?,?,?)";
        UPDATE = "UPDATE "+tableName+" SET otp=?, validity=?, tolu=now() WHERE phone=?";
        SELECT_ALL = "SELECT * FROM "+tableName+"";
        
        createTable();
    }
    
    private final Logger LOGGER = LogManager.getLogger(PhoneOTPQuery.class);    
    private final Map<Long, OtpPhone> PHONE_OTP_LIST = new HashMap<>();
    public boolean refresh = true;
    
    
    
    public synchronized void record(OtpPhone otp){
        //System.out.println(JsonUtil.GSON.toJson(otp));
        if(selectAll().containsKey(otp.getPhone().getNumber())){
            update(otp);
        }else{
            add(otp);
        }
    }
    
    public synchronized OtpStatus verify(long phone, String otp){
        OtpPhone op = selectAll().get(phone);
        if(op.getOtp().equalsIgnoreCase(otp)){
            if(defaultTimeLimit < 1 || op.getExpiryTime().isAfter(LocalDateTime.now())){
                return OtpStatus.CORRECT;
            }else{
                return OtpStatus.EXPIRED;
            }
        }else{
            return OtpStatus.INVALID;
        }
    }
    
    /////////////////////////////////////////////////
    private Response createTable(){
        boolean success = false;
        //try(Connection con = Conn.getConnection()){
        try(PreparedStatement ps = conn.prepareStatement(CREATE)){
            success = ps.executeUpdate() == 1;
        //}
        }catch(SQLException ex){
            LOGGER.error(ex);
            return new Response(false, ex.toString());
        }
        return new Response(success, "SUCCESS");
    }
    
    private Response add(OtpPhone otp){
        boolean success = false;
        //try(Connection con = conn){
        try(PreparedStatement ps = conn.prepareStatement(ADD)){
            ps.setLong(1, otp.getPhone().getNumber());
            ps.setString(2, otp.getOtp());
            ps.setTimestamp(3, java.sql.Timestamp.valueOf(otp.getExpiryTime()));
            success = ps.executeUpdate() == 1;
            PHONE_OTP_LIST.put(otp.getPhone().getNumber(), otp);
        //}
        }catch(SQLException ex){
            LOGGER.error(ex);
            return new Response(false, ex.toString());
        }
        return new Response(success, "SUCCESS");
    }
    
    private Response update(OtpPhone otp){
        boolean success = false;
        //try(Connection con = Conn.getConnection()){
        try(PreparedStatement ps = conn.prepareStatement(UPDATE)){
            ps.setString(1, otp.getOtp());
            ps.setTimestamp(2, java.sql.Timestamp.valueOf(otp.getExpiryTime()));
            ps.setLong(3, otp.getPhone().getNumber());
            int i = ps.executeUpdate();
            success = i==1;
            PHONE_OTP_LIST.replace(otp.getPhone().getNumber(), otp);
        //}
        }catch(SQLException ex){
            LOGGER.error(ex);
            return new Response(false, ex.toString());
        }
        return new Response(success, "SUCCESS");
    }
    
    private  OtpPhone mapData(ResultSet rs) throws SQLException{
        OtpPhone op = new OtpPhone();
         op.setPhone(new Phone(rs.getLong(1)));
         op.setOtp(rs.getString(2));
         op.setGenerateTime(rs.getTimestamp(3).toLocalDateTime());
         op.setExpiryTime(rs.getTimestamp(4).toLocalDateTime());
         return op;
    }
    
    private Map<Long, OtpPhone> selectAll(){
        if(refresh){
            refresh = false;
            PHONE_OTP_LIST.clear();
            //try(Connection con = Conn.getConnection()){
            try(PreparedStatement ps = conn.prepareStatement(SELECT_ALL)){
            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    PHONE_OTP_LIST.put(rs.getLong(1), mapData(rs));
                }
            }
            //}
            }catch(SQLException ex){
                LOGGER.error(ex);
                if(ex.getErrorCode() == 1146){
                    createTable();
                }
            }
        }
        return PHONE_OTP_LIST;
   }
   
}

