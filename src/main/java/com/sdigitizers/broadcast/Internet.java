
package com.sdigitizers.broadcast;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import org.apache.logging.log4j.LogManager;

/**
 *
 * @author Shriram Prajapat
 */
public class Internet {
    private static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger(Internet.class);
    
    public static boolean isAvailable() {
      try{
          URL url = new URL("http://www.google.com");
          URLConnection con = url.openConnection();
          con.connect();
          LOGGER.info("Internet available");
          return true;
      }catch(IOException ex){
          LOGGER.error("Internet Not Available - Error reaching host : ",ex);
          return false;
      }
  }
}
