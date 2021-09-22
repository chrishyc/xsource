package log.b_slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * https://www.cnblogs.com/waterystone/p/11329645.html
 */
public class Slf4jLogging {
    private static Logger logger = LoggerFactory.getLogger(Slf4jLogging.class);
    public static void main(String[] args) {
        logger.info("hello world");
    }
}
