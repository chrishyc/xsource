package spring.springboot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chris
 */
@RestController
public class DemoController {
    @RequestMapping("/springboot")
    public String sayHello() {
        return "hello spring Boot";
    }
}
