package web.servlet.container.tomcat.mine.servlet;


public interface Servlet {
    
    void init() throws Exception;
    
    void destory() throws Exception;
    
    void service(Request request, Response response) throws Exception;
}
