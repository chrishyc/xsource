package mvc.controller;

import mvc.pojo.Order;
import mvc.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * @author chris
 */
@RequestMapping("/param/mapping")
@Controller
public class ParamMappingController {
    @RequestMapping("/hello")
    public ModelAndView sayHello() {
        Date date = new Date();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("date", date);
        modelAndView.setViewName("success");
        return modelAndView;
    }
    
    @RequestMapping("/servletApi")
    public ModelAndView servletApi(HttpServletRequest request,
                                   HttpServletResponse response, HttpSession session) {
        String id = request.getParameter("id");
        
        Date date = new Date();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("date", date);
        modelAndView.addObject("request",request.getRequestURL());
        modelAndView.setViewName("success");
        return modelAndView;
    }
    
    @RequestMapping("/primitiveType")
    public ModelAndView primitiveType(Integer id, Boolean flag) {
        Date date = new Date();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("date", date);
        modelAndView.addObject("id",id);
        modelAndView.addObject("flag",flag);
        modelAndView.setViewName("success");
        return modelAndView;
    }
    
    @RequestMapping("/primitiveTypeAnnotation")
    public ModelAndView primitiveTypeAnnotation(@RequestParam("ids") Integer id, @RequestParam("flag") Boolean flag) {
        Date date = new Date();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("date", date);
        modelAndView.addObject("ids",id);
        modelAndView.setViewName("success");
        return modelAndView;
    }
    
    @RequestMapping("/pojo")
    public ModelAndView pojo(User user) {
        Date date = new Date();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("date", date);
        modelAndView.addObject("user",user);
        modelAndView.setViewName("success");
        return modelAndView;
    }
    
    @RequestMapping("/pojoNested")
    public ModelAndView pojoNested(Order order) {
        Date date = new Date();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("date", date);
        modelAndView.addObject("order",order);
        modelAndView.setViewName("success");
        return modelAndView;
    }
    
    @RequestMapping("/convert")
    public ModelAndView convert(Date birthday) {
        Date date = new Date();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("date", date);
        modelAndView.addObject("birthday",birthday);
        modelAndView.setViewName("success");
        return modelAndView;
    }
}
