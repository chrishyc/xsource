package spring.cloud.provider.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chris
 */
@RestController
@RequestMapping("/resume")
public class ResumeController {
    
    
    @Value("${server.port}")
    private Integer port;
    
    @GetMapping("/openstate/{userId}")
    public Integer findDefaultResumeState(@PathVariable Long userId) {
        System.out.println("====>>>>>>>>>>>>>>我是" + userId + "，访问到我这里了......");
        return port;
    }
}
