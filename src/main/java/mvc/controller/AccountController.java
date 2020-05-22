package mvc.controller;

import jpa.dao.ResumeDao;
import jpa.pojo.Resume;
import lombok.extern.slf4j.Slf4j;
import mvc.pojo.Account;
import mvc.pojo.User;
import mvc.service.AccountService;
import org.apache.coyote.ProtocolHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.apache.catalina.connector.*;
import org.apache.coyote.http11.*;
import org.apache.coyote.Request;
import org.apache.coyote.Response;
import javax.servlet.http.*;
import org.apache.catalina.session.*;
import org.apache.catalina.core.*;
/**
 * @author chris
 */
@Controller
@RequestMapping("/account")
@Slf4j
public class AccountController {
    @Autowired
    private AccountService accountService;
    
    @Autowired
    private ResumeDao resumeDao;
    public static final String ADMIN_NAME = "admin";
    public static final String ADMIN_PASSWORD = "admin";
    
    @RequestMapping("/queryAll")
    @ResponseBody
    public List<Account> queryAll() throws Exception {
        return accountService.queryAccountList();
    }
    
    /**
     * tomcat session请求:
     *
     * {@link Connector}是{@link ProtocolHandler}的facade外观类
     * http1.1对应的是{@link Http11NioProtocol}
     * http1.1对应的协议处理器{@link Http11NioProcessor}
     * 底层请求实体{@link Request},底层响应实体{@link Response}
     *
     * {@link Request}的上层wrapper类{@link org.apache.catalina.connector.Request}
     * 上层wrapper类{@link Response}
     *
     * {@link CoyoteAdapter}是{@link Connector}的适配器
     * 核心逻辑入口{@link CoyoteAdapter#service(Request, Response)}
     * {@link CoyoteAdapter#postParseRequest}包括解析底层request中session，map请求到对应的servlet实体
     *
     * 创建session逻辑:调用{@link HttpServletRequest#getSession()}时创建session
     * 最终创建逻辑{@link ManagerBase#createSession},创建完后返回外观类{@link StandardSessionFacade}
     * 并将{@link StandardSession}放入内存hashmap中
     *
     * 设置response的cookie{@link ApplicationSessionCookieConfig#createSessionCookie}
     * {@link org.apache.catalina.connector.Response#addSessionCookieInternal}
     * @param session
     * @param user
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(HttpSession session, User user) {
        if (ADMIN_NAME.equals(user.getName()) && ADMIN_PASSWORD.equals(user.getPassword())) {
            session.setAttribute("SESSION", user);
            log.info("AccountController.login,param:{}", user);
            return "redirect:/account/list";
        }
        return "login";
    }
    
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Map<String, Object> map) {
        List<Resume> list = resumeDao.findAll();
        map.put("res", list);
        return "list";
    }
    
    @RequestMapping(value = "/resource/{id}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable("id") Long id) {
        Optional result = resumeDao.findById(id);
        ModelAndView model = new ModelAndView();
        model.setViewName("input");
        model.addObject("res", result.get());
        return model;
    }
    
    @RequestMapping(value = "/deleting/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable("id") Long id,Map<String, Object> map) {
        resumeDao.deleteById(id);
        List<Resume> list = resumeDao.findAll();
        map.put("res", list);
        return "list";
    }
    
    @RequestMapping(value = "/resource", method = RequestMethod.GET)
    public String input(Map<String, Object> map) {
        map.put("res", new Resume());
        return "input";
    }
    
    @RequestMapping(value = "/resource", method = RequestMethod.POST)
    public String save(Resume resume) {
        resumeDao.save(resume);
        return "redirect:/account/list";
    }
}
