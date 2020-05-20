package spring.springboot.redissession.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("demo")
public class SessionController {
    @RequestMapping("/result")
    public String result(){
        return "result_session";
    }
}
