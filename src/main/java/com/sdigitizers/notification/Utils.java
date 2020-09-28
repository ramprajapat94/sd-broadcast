
package com.sdigitizers.notification;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import org.apache.logging.log4j.LogManager;

/**
 *
 * @author Shriram Prajapat
 */
public class Utils {
    private static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger(Utils.class);
    
    /**
     * Check if internet is available/working on this device
     * @return true if internet found in working condition else false
     */
    public static boolean isAvailable() {
      try{
          URL url = new URL("http://www.google.com");
          URLConnection con = url.openConnection();
          con.connect();
          LOGGER.info("Internet available");
          return true;
      }catch(IOException ex){
          LOGGER.error("Internet Not Available - Error reaching host GOOGLE ",ex);
          return false;
      }
  }
   
}
