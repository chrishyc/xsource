package log.a_jdk;

import org.junit.Test;

import java.io.OutputStream;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Logging {
    
    /**
     * logger树层次&向上输出
     */
    @Test
    public void testRoot() {
        Logger logger = Logger.getGlobal();
        logger.addHandler(new ConsoleHandler() {
            @Override
            protected synchronized void setOutputStream(OutputStream out) throws SecurityException {
                super.setOutputStream(System.out);
            }
        });
        logger.setLevel(Level.FINE);
        logger.info("start process...");
        logger.warning("memory is running out...");
        logger.fine("ignored.");
        logger.severe("process will be terminated...");
    }
}
