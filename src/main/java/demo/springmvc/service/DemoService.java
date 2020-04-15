package demo.springmvc.service;

import demo.springmvc.annotation.MVCService;
import mvc.pojo.User;

@MVCService
public class DemoService {
    public User getUserByName(String name) {
        User user = new User();
        user.setId(199);
        user.setName(name);
        return user;
    }
}
