package web.springmvc.open.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import web.springmvc.open.pojo.User;

@Controller
public class JsonController {
    
    /**
     * curl -H "Content-Type:application/json" -X POST
     * -d '{"id": "1", "name":"chris"}' http://localhost:8080/json
     * @param user
     * @return
     */
    @RequestMapping("/json")
    @ResponseBody
    public User handle07(User user) {
        // 添加@ResponseBody之后，不再走视图解析器那个流程，而是等同于response直接输出数据
        // 业务逻辑处理，修改name为张三丰
        user.setName("张三丰");
        return user;
    }
}
