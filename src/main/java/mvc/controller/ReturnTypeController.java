package mvc.controller;

import mvc.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.support.HandlerMethodReturnValueHandlerComposite;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ModelAndViewMethodReturnValueHandler;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.ViewNameMethodReturnValueHandler;
import org.springframework.web.servlet.mvc.method.annotation.*;

import java.util.Date;

/**
 * @author chris
 */
@Controller
@RequestMapping("/returnType")
public class ReturnTypeController {
    /**
     * 1.是否有返回值{@link ServletInvocableHandlerMethod#invokeAndHandle}
     * 2.获取返回值处理器{@link HandlerMethodReturnValueHandlerComposite#selectHandler}
     * 无论是否有返回值都会获取返回值处理器
     * 3.如果返回类型为ModelAndView，对应返回值处理器为{@link ModelAndViewMethodReturnValueHandler#handleReturnValue}
     * 主要将返回的结果存入ModelAndViewContainer中
     * 4.重新生成一个ModelAndView作为返回结果，{@link RequestMappingHandlerAdapter#getModelAndView}
     *
     * @return
     */
    @RequestMapping("/hello")
    public ModelAndView returnModelAndView() {
        Date date = new Date();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("date", date);
        modelAndView.setViewName("success");
        return modelAndView;
    }
    
    /**
     * 3.返回值为void类型，对应返回值处理器为{@link ViewNameMethodReturnValueHandler#supportsReturnType}
     * 没有任何东西被放入ModelAndViewContainer中
     * 4.重新生成一个ModelAndView作为返回结果，但这个ModelAndView中biew,model,status都为null;
     */
    @RequestMapping("/returnVoid")
    public void returnVoid() {
        Date date = new Date();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("date", date);
        modelAndView.setViewName("success");
    }
    
    /**
     * 3.返回值为String类型(CharSequence),对应返回值处理器为{@link ViewNameMethodReturnValueHandler#supportsReturnType}
     * 将返回值放入ModelAndViewContainer.setViewName中，此过程还会判断字符串是否有重定向字符
     * {@link ViewNameMethodReturnValueHandler#isRedirectViewName}
     * 4.重新生成一个ModelAndView作为返回结果，这个ModelAndView中viewName=返回值，其他为null.
     */
    @RequestMapping("/returnString")
    public String returnString() {
        Date date = new Date();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("date", date);
        modelAndView.setViewName("success");
        return "success";
    }
    
    /**
     * 3.返回值有@ResponseBody注解，或方法有ResponseBody注解，对应返回处理器为
     * {@link RequestResponseBodyMethodProcessor#supportsReturnType}
     * {@link RequestResponseBodyMethodProcessor#handleReturnValue}
     * 4.{@link RequestMappingHandlerAdapter#getModelAndView}过程中直接返回null
     * @param user
     * @return
     */
    @RequestMapping("/returnResponseBody")
    @ResponseBody
    public User returnResponseBody(User user) {
        // 添加@ResponseBody之后，不再走视图解析器那个流程，而是等同于response直接输出数据
        // 业务逻辑处理，修改name为张三丰
        user.setName("张三丰");
        return user;
    }
}
