package log.c_log4j2;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * https://logging.apache.org/log4j/2.x/manual/architecture.html
 * 使用指南:https://logging.apache.org/log4j/2.x/articles.html
 */
public class Logging {
    private static final Logger root = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
    
    public static void main(String[] args) {
        root.info("Hello, World!");
    }
    
}
