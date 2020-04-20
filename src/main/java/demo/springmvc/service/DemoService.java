package demo.springmvc.service;

import demo.springmvc.annotation.MVCService;
import mvc.pojo.User;

@MVCService
public class DemoService {
    public String getUserByName(String name) {
        User user = new User();
        user.setId(199);
        user.setName("张四");
        System.out.println("service 实现类中的name参数：" + name) ;
        return name;
    }
}
