package spring.springboot.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import spring.springboot.exception.ServerException;

@RestController
public class StatusCodeController {

  @RequestMapping(value = "/list", method = RequestMethod.GET)
  public String getBooks(HttpServletRequest request, HttpServletResponse response)
      throws ServerException {
    HttpSession session = request.getSession();
    Subject subject = SecurityUtils.getSubject();
//        subject.login(new ShiroSpringWebConfig.MyToken());
    return "hello";
  }

  @RequestMapping(value = "/json", method = RequestMethod.POST)
  @ResponseBody
  public String jsonObject(@RequestBody JSONObject object) {
    System.out.println(object.toString());
    return object.toString();
  }
}
