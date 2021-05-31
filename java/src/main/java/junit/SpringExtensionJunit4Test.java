package junit;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 */
@RunWith(SpringRunner.class)
@ContextConfiguration({"/book.xml"})
public class SpringExtensionJunit4Test {
}
