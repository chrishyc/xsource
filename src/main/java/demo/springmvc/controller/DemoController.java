package demo.springmvc.controller;

import demo.springmvc.annotation.MVCAutowired;
import demo.springmvc.annotation.MVCController;
import demo.springmvc.annotation.MVCRequestMapping;
import demo.springmvc.service.DemoService;
import mvc.pojo.User;

@MVCController
public class DemoController {
    
    @MVCAutowired
    private DemoService demoService;
    
    @MVCRequestMapping("/chris/hello")
    public User sayHello(String name) {
        return demoService.getUserByName(name);
    }
}
