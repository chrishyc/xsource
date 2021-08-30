package web.springmvc.mine.controller;

import web.springmvc.mine.annotation.MVCAutowired;
import web.springmvc.mine.annotation.MVCController;
import web.springmvc.mine.annotation.MVCRequestMapping;
import web.springmvc.mine.annotation.MVCSecurity;
import web.springmvc.mine.service.DemoService;

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
