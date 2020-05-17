package spring.springboot.redissession.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("demo")
public class DemoController {
    @RequestMapping("/result")
    public String result(){
        return "result";
    }
}
