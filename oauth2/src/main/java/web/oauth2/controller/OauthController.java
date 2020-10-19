package web.oauth2.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 参考:
 * https://www.cnblogs.com/0201zcr/p/5131602.html
 * https://mp.weixin.qq.com/debug/cgi-bin/sandboxinfo?action=showinfo&t=sandbox/index
 *
 *
 */
@RestController
public class OauthController {
    
    @RequestMapping(value = "/wx", produces = {"application/json;charset=utf-8"})
    public String hello(HttpServletRequest request, String echostr) {
        System.out.println(echostr);
        return echostr;
    }
}
