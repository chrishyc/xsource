package spring.springboot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import spring.springboot.controller.DemoController;

@SpringBootTest
class SpringbootApplicationTests {
	
	@Autowired
	private DemoController demoController;

	@Test
	void contextLoads() {
		demoController.sayHello();
	}

}
