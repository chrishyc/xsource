package resttemplate;

import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;

public class Sample {
    @Test
    public void testInterceptor() throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setInterceptors(Collections.singletonList(new TrialInterceptor()));
        String ret = restTemplate.getForObject(new URI("https://www.google.com/"), String.class);
        System.out.println(ret);
    }
}
