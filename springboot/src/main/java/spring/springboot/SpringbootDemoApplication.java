package spring.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author chris
 */
@SpringBootApplication
public class SpringbootDemoApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		try {
			SpringApplication.run(SpringbootDemoApplication.class, args);
		}catch (Exception e){
			System.out.println(e);
		}
	}

}
