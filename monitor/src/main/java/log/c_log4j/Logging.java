package log.c_log4j;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 * https://logging.apache.org/log4j/2.x/manual/architecture.html
 */
public class Logging {
    private static final Logger root = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
    
    public static void main(String[] args) {
        root.debug("Hello, World!");
    }
    
    
}
