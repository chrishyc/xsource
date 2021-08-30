package web.springmvc.open.resttemplate;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.support.HttpRequestWrapper;

import java.net.URI;

public class MyServiceRequestWrapper<U, V> extends HttpRequestWrapper {
    private final U instance;
    
    private final V loadBalancer;
    
    public MyServiceRequestWrapper(HttpRequest request, U instance,
                                   V loadBalancer) {
        super(request);
        this.instance = instance;
        this.loadBalancer = loadBalancer;
    }
    
    @Override
    public URI getURI() {
        System.out.println("instance:" + instance + ",loadBalancer:" + loadBalancer);
        return getRequest().getURI();
    }
}
