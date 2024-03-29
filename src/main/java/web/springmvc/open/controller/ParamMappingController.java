package web.springmvc.open.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.method.annotation.RequestParamMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandlerComposite;
import org.springframework.web.method.support.InvocableHandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.*;
import web.springmvc.open.pojo.Order;
import web.springmvc.open.pojo.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Valid;
import java.util.Date;
import java.util.Map;

/**
 * @author chris
 */
@RequestMapping("/param/mapping")
@Controller
public class ParamMappingController {
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
    public ModelAndView sayHello() {
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
     * {@link org.springframework.validation.support.BindingAwareModelMap}
     *
     * @param map
     * @param model
     * @param modelMap
     * @return
     */
    @RequestMapping("/map")
    public ModelAndView map(Map<String, Object> map, Model model, ModelMap modelMap) {
        Date date = new Date();
        model.addAttribute("name", "chris");
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
        modelAndView.addObject("request", request.getRequestURL());
        modelAndView.setViewName("success");
        return modelAndView;
    }
    
    /**
     * 1.请求开始触发方法invoke后，进入开始解析method每个参数支持的解析器
     * {@link InvocableHandlerMethod#getMethodArgumentValues}
     * 2.对于基本类型，解析器为{@link RequestParamMethodArgumentResolver#supportsParameter}
     * 此解析器位于参数解析器倒数第二个
     */
    @RequestMapping("/primitiveType")
    public ModelAndView primitiveType(Integer id, Boolean flag) {
        Date date = new Date();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("date", date);
        modelAndView.addObject("id", id);
        modelAndView.addObject("flag", flag);
        modelAndView.setViewName("success");
        return modelAndView;
    }
    
    /**
     * 1.请求开始触发方法invoke后，进入开始解析method每个参数支持的解析器
     * {@link InvocableHandlerMethod#getMethodArgumentValues}
     * 2.对于基本类型，解析器为{@link RequestParamMethodArgumentResolver#supportsParameter}
     * 此解析器位于参数解析器倒数第二个
     */
    @RequestMapping("/primitiveTypeAnnotation")
    public ModelAndView primitiveTypeAnnotation(@RequestParam("ids") Integer id, @RequestParam("flag") Boolean flag) {
        Date date = new Date();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("date", date);
        modelAndView.addObject("ids", id);
        modelAndView.setViewName("success");
        return modelAndView;
    }
    
    /**
     * 1.请求开始触发方法invoke后，进入开始解析method每个参数支持的解析器
     * {@link InvocableHandlerMethod#getMethodArgumentValues}
     * 2.对于pojo类型，解析器为{@link ServletModelAttributeMethodProcessor#supportsParameter}
     * 此解析器位于参数解析器末尾,毕竟所有类都是pojo，所以只有没有被前面参数解析器识别时，才使用这个解析
     * <p>
     * 对于pojo类，属性必须有set方法才能赋值成功,核心逻辑{@link java.beans.Introspector#getTargetPropertyInfo}
     * 参数绑定逻辑{@link org.springframework.validation.DataBinder#doBind}
     * {@link Valid}核心逻辑{@link org.springframework.validation.DataBinder#validate}
     * <p>
     * {@link javax.validation.ConstraintValidator#isValid(Object, ConstraintValidatorContext)}
     * {@link org.hibernate.validator.internal.engine.ValidatorImpl#validate(Object, Class[])}
     * 初始化所有{@link Valid}注解类型，{@link org.hibernate.validator.internal.metadata.core.ConstraintHelper#ConstraintHelper()}
     *
     * @param user
     * @return
     */
    @RequestMapping("/pojo")
    public ModelAndView pojo(@Valid User user) {
        Date date = new Date();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("date", date);
        modelAndView.addObject("user", user);
        modelAndView.setViewName("success");
        return modelAndView;
    }
    
    @RequestMapping("/pojoNested")
    public ModelAndView pojoNested(Order order) {
        Date date = new Date();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("date", date);
        modelAndView.addObject("order", order);
        modelAndView.setViewName("success");
        return modelAndView;
    }
    
    @RequestMapping("/convert")
    public ModelAndView convert(Date birthday) {
        Date date = new Date();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("date", date);
        modelAndView.addObject("birthday", birthday);
        modelAndView.setViewName("success");
        return modelAndView;
    }
}
