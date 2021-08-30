package web.springmvc.open.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author chris
 */
@Slf4j
public class AuthFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("AuthFilter.init,param:{}", filterConfig);
    }
    
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession(false);
        if (!request.isRequestedSessionIdValid()) {
            //session过期,转向session过期提示页,最终跳转至登录页面
            log.info("AuthFilter.init,msg:{}", "session过期了");
        } else {
            session.setMaxInactiveInterval(60);
            log.info("AuthFilter.init,msg:{}", "session有效");
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
    
    @Override
    public void destroy() {
        log.info("AuthFilter.destroy");
    }
}
