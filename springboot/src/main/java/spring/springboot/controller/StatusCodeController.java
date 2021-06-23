package spring.springboot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.springboot.exception.ServerException;

@RestController
public class StatusCodeController {
    @GetMapping("/list")
    public String getBooks() throws ServerException {
        return "hello";
    }
}
