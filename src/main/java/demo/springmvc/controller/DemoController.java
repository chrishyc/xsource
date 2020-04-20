package demo.springmvc.controller;

import demo.springmvc.annotation.MVCAutowired;
import demo.springmvc.annotation.MVCController;
import demo.springmvc.annotation.MVCRequestMapping;
import demo.springmvc.annotation.MVCSecurity;
import demo.springmvc.service.DemoService;

@MVCController
public class DemoController {
    
    @MVCAutowired
    private DemoService demoService;
    
    @MVCSecurity(value = "chris,device,ishu")
    @MVCRequestMapping("/hello")
    public String sayHello(String name) {
        return demoService.getUserByName(name);
    }
}
