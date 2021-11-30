package spring.springboot.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;
import spring.springboot.exception.ServerException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
public class StatusCodeController {
    
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String getBooks(HttpServletRequest request, HttpServletResponse response)
            throws ServerException {
        HttpSession session = request.getSession();
        Subject subject = SecurityUtils.getSubject();
//        subject.login(new ShiroSpringWebConfig.MyToken());
        return "hello";
    }
    
    
    @GetMapping(value = "/boolean")
    @ResponseBody
    public String jsonObject(@RequestParam(required = false) boolean hi, @RequestParam("type") Integer type) {
        System.out.println(hi);
        return "success";
    }
}
