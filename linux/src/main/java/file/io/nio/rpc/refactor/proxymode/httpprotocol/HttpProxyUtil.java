package file.io.nio.rpc.refactor.proxymode.httpprotocol;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;

/**
 * @author: 马士兵教育
 * @create: 2020-07-18 20:23
 */
public class HttpProxyUtil {

    @Test
    public void testHttpClient(){
        CloseableHttpClient build = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet("http://www.baidu.com/ooxx?a=3&b=4");

        HttpPost post = new HttpPost("");
        String method = httpGet.getMethod();
        URI uri = httpGet.getURI();
        System.out.println(method);
        System.out.println(uri);
        System.out.println(uri.getQuery());
        System.out.println(uri.getPort());
        System.out.println(uri.getHost());
        System.out.println(uri.getPath());
        System.out.println("-----------");
        String query = uri.getQuery();


        try {
            CloseableHttpResponse response = build.execute(httpGet);
           response.getEntity().writeTo(System.out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
