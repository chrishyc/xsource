package spring.springboot.redissession.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import org.springframework.session.data.redis.config.annotation.web.http.*;
import org.springframework.session.data.redis.*;
import org.springframework.data.redis.core.*;
import org.springframework.session.config.annotation.web.http.*;
import org.springframework.session.web.http.*;
/**
 * 请求权限验证
 */
public class RequestInterceptor implements HandlerInterceptor {
    
    /**
     * 之前执行（进入Handler处理之前）
     * 可以进行权限验证
     *
     * {@link EnableRedisHttpSession}redis缓存session
     * 自动配置{@link RedisHttpSessionConfiguration}中
     * 注入{@link RedisIndexedSessionRepository}
     * {@link RedisTemplate}
     *
     * 他的父类{@link SpringHttpSessionConfiguration}注入{@link SessionRepositoryFilter}
     * 核心逻辑就在{@link SessionRepositoryFilter#doFilterInternal}
     * 会生成两个装饰类{@link SessionRepositoryFilter.SessionRepositoryRequestWrapper}
     * {@link SessionRepositoryFilter.SessionRepositoryResponseWrapper}
     *
     * {@link SessionRepositoryFilter.SessionRepositoryRequestWrapper#getSession()}类
     * 使用注入的{@link RedisIndexedSessionRepository}来存储session
     * @param request
     * @param response
     * @param handler
     * @return true放行，false中止程序
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        System.out.println("==========>>>>sessionId:" + session.getId());
        System.out.println("======SESSION::::" + session);
        
        Enumeration<String> attrs = session.getAttributeNames();
        // 遍历attrs中的
        while (attrs.hasMoreElements()) {
            // 获取session键值
            String name = attrs.nextElement().toString();
            // 根据键值取session中的值
            Object value = session.getAttribute(name);
            // 打印结果
            System.out.println("------" + name + ":" + value + "--------\n");
            
        }
        
        System.out.println("===============>>>>>>当前uri：" + request.getRequestURI());
        Object username = session.getAttribute("username");
        System.out.println("=================>>>>>username:" + username);
        if (username == null) {
            // 没有登录,重定向到登录页
            System.out.println("未登录，请登录");
            return false;
        } else {
            System.out.println("已登录，放行请求");
            // 已登录，放行
            return true;
        }
    }
    
    /**
     * 之中执行（Handler处理完毕但尚未跳转页面）
     *
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }
    
    /**
     * 之后执行（Handler处理完毕而且已经跳转页面）
     *
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
