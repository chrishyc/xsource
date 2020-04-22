package mvc.controller;

import jpa.dao.ResumeDao;
import jpa.pojo.Resume;
import lombok.extern.slf4j.Slf4j;
import mvc.pojo.Account;
import mvc.pojo.User;
import mvc.service.AccountService;
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
