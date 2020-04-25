package spring.springboot;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import spring.springboot.controller.DemoController;
import spring.springboot.pojo.Person;

@RunWith(SpringRunner.class)
@SpringBootTest
class SpringbootApplicationTests {
	
	@Autowired
	private DemoController demoController;
	
	@Autowired
	private Person person;

	@Test
	void contextLoads() {
		demoController.sayHello();
	}
	
	@Test
	public void testConfigurationProperties(){
		System.out.println(person);
		System.out.println(person);
	}
	

}
