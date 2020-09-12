package spring.cloud.consumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@RestController
@RequestMapping("/autodeliver")
public class AutodeliverController {
    
    @Autowired
    private RestTemplate restTemplate;
    
    @Autowired
    private DiscoveryClient discoveryClient;
    
    @GetMapping("/checkState/{userId}")
    public Integer findResumeOpenState(@PathVariable Long userId) {
        List<ServiceInstance> instances = discoveryClient.getInstances("eureka-provider");
        // 2、如果有多个实例，选择一个使用(负载均衡的过程)
        ServiceInstance serviceInstance = instances.get(0);
        // 3、从元数据信息获取host port
        String host = serviceInstance.getHost();
        int port = serviceInstance.getPort();
        String url = "http://" + host + ":" + port + "/resume/openstate/" + userId;
        System.out.println("===============>>>从EurekaServer集群获取服务实例拼接的url：" + url);
        Integer forObject = restTemplate.getForObject(url, Integer.class);
        return forObject;
    }
    
    @GetMapping("/checkState1/{userId}")
    public Integer findResumeOpenState1(@PathVariable Long userId) {
        // 使用ribbon不需要我们自己获取服务实例然后选择一个那么去访问了（自己的负载均衡）
        String url = "http://eureka-provider/resume/openstate/" + userId;  // 指定服务名
        Integer forObject = restTemplate.getForObject(url, Integer.class);
        return forObject;
    }
}
