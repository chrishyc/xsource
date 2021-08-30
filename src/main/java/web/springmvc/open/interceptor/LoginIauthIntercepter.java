package web.springmvc.open.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import web.springmvc.open.controller.AccountController;
import web.springmvc.open.pojo.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
public class LoginIauthIntercepter implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("SESSION");
        log.info("LoginIauthIntercepter.preHandle,param:{}", user);
        if (user == null || !AccountController.ADMIN_NAME.equals(user.getName()) || !AccountController.ADMIN_PASSWORD.equals(user.getPassword())) {
            response.sendRedirect(request.getContextPath() + "/account/login");
            return false;
        }
        return true;
    }
}
