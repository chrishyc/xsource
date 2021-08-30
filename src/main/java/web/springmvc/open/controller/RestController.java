package web.springmvc.open.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

@RequestMapping("/rest")
@Controller
public class RestController {
    
    @RequestMapping(value = "/{id}/{name}", method = {RequestMethod.GET})
    public ModelAndView restGet(@PathVariable("id") Integer id, @PathVariable("name") String username) {
        
        Date date = new Date();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("date", date);
        modelAndView.setViewName("success");
        return modelAndView;
    }
    
    @RequestMapping(value = "/{id}/{name}", method = {RequestMethod.PUT})
    public ModelAndView restPut(@PathVariable("id") Integer id, @PathVariable("name") String username) {
        
        Date date = new Date();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("date", date);
        modelAndView.setViewName("success");
        return modelAndView;
    }
    
    
    @RequestMapping(value = "/{id}", method = {RequestMethod.DELETE})
    public ModelAndView restDelete(@PathVariable("id") Integer id) {
        
        Date date = new Date();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("date", date);
        modelAndView.setViewName("success");
        return modelAndView;
    }
}
