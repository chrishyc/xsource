package spring.springboot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chris
 */
@RestController
public class DemoController {
    @RequestMapping(value = "/springboot",produces = "application/json; charset=utf-8")
    public String sayHello() {
        return "hello spring Boot,---111222221--qqq==jjj-11111-";
    }
}
