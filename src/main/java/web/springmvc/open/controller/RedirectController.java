package web.springmvc.open.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class RedirectController {
    
    @ExceptionHandler(ArithmeticException.class)
    public void handleException(ArithmeticException exception, HttpServletResponse response) {
        // 异常处理逻辑
        try {
            response.getWriter().write(exception.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @RequestMapping("/handleRedirect")
    public String handleRedirect(String name, RedirectAttributes
            redirectAttributes) {
        //return "redirect:handle01?name=" + name; // 拼接参数安全性、参数⻓度都局限
        // addFlashAttribute方法设置了一个flash类型属性，该属性会被暂存到session中，在 跳转到⻚面之后该属性销毁
        redirectAttributes.addFlashAttribute("id", 2);
        return "redirect:/param/mapping/primitiveType";
    }
}
