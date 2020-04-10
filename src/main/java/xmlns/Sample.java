package xmlns;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Sample {
    @Test
    public void testXMLNS() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("myns.xml");
    }
}
