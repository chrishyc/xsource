package web.springmvc.open.resttemplate;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

public class TrialInterceptor implements ClientHttpRequestInterceptor {
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        System.out.println("==============TrialInterceptor=============");
        MyServiceRequestWrapper<Integer, Integer> wrapper = new MyServiceRequestWrapper<>(request, 1, 2);
        return execution.execute(wrapper, body);
    }
}
