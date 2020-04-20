package demo.springmvc.controller;

import demo.springmvc.annotation.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

/**
 * @author chris
 */
public class MVCDispatchServlet extends HttpServlet {
    private Properties properties = new Properties();
    private List<String> classesName = new ArrayList<>();
    private Map<String, Object> objectMap = new HashMap<>();
    private Map<String, Method> handlerMapping = new HashMap<>();
    private Map<String, String> securityMapping = new HashMap<>();
    
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        // 获取要扫描包名
        parseProperties(config);
        // 扫描获取class类名
        scanPackage();
        // 实例化class并放入bean容器中
        // 扫描注解MVCRequestMapping的方法，放入handlerMapping中{url:Method}
        // 对MVCAutowired注解的属性注入
        initClass();
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
        // 解析url
        try {
            invoke(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void parseProperties(ServletConfig config) {
        try {
            String packagePath = config.getInitParameter("contextConfigLocation");
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(packagePath.replace("classpath:", ""));
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void scanPackage() {
        String packagePath = properties.getProperty("componentScan");
        scanClassFile(packagePath);
    }
    
    private void scanClassFile(String packagePath) {
        String filePath = getClass().getClassLoader().getResource("").getPath() + packagePath.replaceAll("\\.", "/");
        File file = new File(filePath);
        if (file.exists()) {
            File[] files = file.listFiles();
            for (File f : files) {
                if (f.isDirectory()) {
                    scanClassFile(packagePath + "." + f.getName());
                } else if (f.getName().endsWith(".class")) {
                    String className = packagePath + "." + f.getName().replaceAll(".class", "");
                    classesName.add(className);
                }
            }
        }
    }
    
    private void initClass() {
        for (String className : classesName) {
            try {
                Class clazz = getClass().getClassLoader().loadClass(className);
                Annotation[] annotations = clazz.getAnnotations();
                for (Annotation annotation : annotations) {
                    if (annotation instanceof MVCController || annotation instanceof MVCService) {
                        if (!objectMap.containsKey(clazz.getName())) {
                            Object o = clazz.newInstance();
                            objectMap.put(clazz.getName(), o);
                        }
                        
                        
                        Method[] methods = clazz.getDeclaredMethods();
                        for (Method method : methods) {
                            Annotation[] methodAnnotations = method.getDeclaredAnnotations();
                            for (Annotation annotation1 : methodAnnotations) {
                                if (annotation1 instanceof MVCRequestMapping) {
                                    handlerMapping.put(((MVCRequestMapping) annotation1).value(), method);
                                }
                                if (annotation1 instanceof MVCSecurity) {
                                    securityMapping.put(method.getName(), ((MVCSecurity) annotation1).value());
                                }
                            }
                        }
                        
                        Field[] fields = clazz.getDeclaredFields();
                        for (Field field : fields) {
                            Annotation[] fieldAnnotations = field.getDeclaredAnnotations();
                            for (Annotation annotation2 : fieldAnnotations) {
                                if (annotation2 instanceof MVCAutowired) {
                                    field.setAccessible(true);
                                    Object object = objectMap.get(field.getType().getName());
                                    if (object == null) {
                                        objectMap.put(field.getType().getName(), field.getType().newInstance());
                                        field.set(objectMap.get(clazz.getName()), objectMap.get(field.getType().getName()));
                                    }
                                }
                            }
                        }
                    }
                }
                
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    private Object invoke(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String url = req.getRequestURL().toString();
        for (String key : handlerMapping.keySet()) {
            if (url.contains(key)) {
                Map<String, String[]> parameterMap = req.getParameterMap();
                String[] users = parameterMap.get("name");
                String iauthUsers = securityMapping.get(handlerMapping.get(key).getName());
                String[] userarr = iauthUsers.split(",");
                boolean iauth = false;
                for (int i = 0; i < userarr.length; i++) {
                    if (userarr[i].equals(users[0])) {
                        iauth = true;
                        break;
                    }
                }
                if (!iauth) {
                    System.out.println("the use of " + users[0] + " can not access the path:" + key);
                    return null;
                }
                
                Method method = handlerMapping.get(key);
                Parameter[] parameters = method.getParameters();
                Object[] params = new Object[method.getParameterTypes().length];
                Map<String, Integer> paramsIndexMap = new HashMap<>();
                int i = 0;
                for (Parameter parameter : parameters) {
                    paramsIndexMap.put(parameter.getName(), i++);
                }
                for (Parameter parameter : parameters) {
                    if (parameterMap.containsKey(parameter.getName())) {
                        String[] values = parameterMap.get(parameter.getName());
                        params[paramsIndexMap.get(parameter.getName())] = values[0];
                    }
                }
                
                Object ret = method.invoke(objectMap.get(method.getDeclaringClass().getName()), params);
                resp.getWriter().write(ret.toString());
                return ret;
            }
        }
        return null;
    }
}
