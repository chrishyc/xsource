package log.c_log4j;


import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * https://logging.apache.org/log4j/2.x/manual/architecture.html
 */
public class Logging {
    private static final Logger root = LogManager.getLogger(org.apache.logging.log4j.LogManager.ROOT_LOGGER_NAME);
    
    public static void main(String[] args) {
        root.debug("Hello, World!");
    }
    
}
