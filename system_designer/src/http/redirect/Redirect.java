package http.redirect;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * 301 302区别:http://calfgz.cn/blog/2018/05/http-redirect-java-node.html
 * 301 Moved Permanently (from disk cache)
 */
public class Redirect {
    
    public void test301() {
        HttpServletResponse response = new HttpServletResponseWrapper(null);
        response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
        response.setHeader("Location", "http://www.xxxxx.com/"); // 要重定向到网站
        response.setHeader("Connection", "close");
    }
}
