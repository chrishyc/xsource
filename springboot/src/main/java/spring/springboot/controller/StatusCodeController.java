package spring.springboot.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.springboot.config.ShiroSpringWebConfig;
import spring.springboot.exception.ServerException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
public class StatusCodeController {
    @GetMapping("/list")
    public String getBooks(HttpServletRequest request, HttpServletResponse response) throws ServerException {
        HttpSession session = request.getSession();
        Subject subject = SecurityUtils.getSubject();
//        subject.login(new ShiroSpringWebConfig.MyToken());
        return "hello";
    }
}
