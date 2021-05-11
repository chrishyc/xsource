package spring.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import spring.springboot.exception.ServerException;

@Controller
public class StatusCodeController {
    @GetMapping("/list")
    public String getBooks() throws ServerException {
        throw new ServerException();
    }
}
