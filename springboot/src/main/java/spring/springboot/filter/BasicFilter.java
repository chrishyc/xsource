package spring.springboot.filter;

import sun.misc.BASE64Decoder;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

//@Component
public class BasicFilter implements Filter {
    
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        int resultStatusCode = checkHTTPBasicAuthorize(request);
//        if (resultStatusCode != 0) {
//        } else {
//            chain.doFilter(request, response);
//        }
    }
    
    @Override
    public void destroy() {
    
    }
    
    private int checkHTTPBasicAuthorize(ServletRequest request) {
        try {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            String auth = httpRequest.getHeader("Authorization");
            if ((auth != null) && (auth.length() > 6)) {
                String HeadStr = auth.substring(0, 5).toLowerCase();
                if (HeadStr.compareTo("basic") == 0) {
                    auth = auth.substring(6, auth.length());
                    String decodedAuth = getFromBASE64(auth);
                    if (decodedAuth != null) {
                        String[] UserArray = decodedAuth.split(":");

                        if (UserArray != null && UserArray.length == 2) {
                            if (UserArray[0].compareTo("chris") == 0
                                    && UserArray[1].compareTo("123") == 0) {
                                return 0;
                            }
                        }
                    }
                }
            }
            return -1;
        } catch (Exception ex) {
            return -2;
        }

    }

    private String getFromBASE64(String s) {
        if (s == null)
            return null;
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            byte[] b = decoder.decodeBuffer(s);
            return new String(b);
        } catch (Exception e) {
            return null;
        }
    }
}
