package spring.springboot.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;

/**
 * @author chris
 */
@RestController
public class DemoController {
    @RequestMapping(value = "/springboot", produces = "application/json; charset=utf-8")
    public String sayHello() {
        return "hello spring Boot,---111222221--qqq==jjj-11111-";
    }
    
    @RequestMapping("/toLoginPage")
    public String toLoginPage(Model model) {
        model.addAttribute("currentYear", Calendar.getInstance().get(Calendar.YEAR));
        return "login";
    }
}
