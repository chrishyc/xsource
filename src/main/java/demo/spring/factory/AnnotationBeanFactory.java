package demo.spring.factory;

import com.alibaba.druid.util.StringUtils;
import demo.spring.annotation.Autowired;
import demo.spring.annotation.Component;
import demo.spring.annotation.Service;
import demo.spring.annotation.Transactional;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class AnnotationBeanFactory {
    private static Map<String, Class<?>> classMap = new HashMap<>();
    private static Map<String, Object> beanMap = new HashMap<>();
    private static Map<String, Object> proxyMap = new HashMap<>();

    static {
        try {
            loadBean();
            DIInject();
            generateProxy();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private static void loadBean() {
        String packageName = "demo.spring";
        String packageDirName = packageName.replace('.', '/');


        // 第一个class类的集合
        // 是否循环迭代
        boolean recursive = true;
        // 获取包的名字 并进行替换
        // 定义一个枚举的集合 并进行循环来处理这个目录下的things
        Enumeration<URL> dirs;
        try {
            dirs = AnnotationBeanFactory.class.getClassLoader().getResources(packageDirName);
            // 循环迭代下去
            while (dirs.hasMoreElements()) {
                // 获取下一个元素
                URL url = dirs.nextElement();
                // 得到协议的名称
                String protocol = url.getProtocol();
                // 如果是以文件的形式保存在服务器上
                if (StringUtils.equals("file", protocol)) {
                    // 获取包的物理路径
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                    // 以文件的方式扫描整个包下的文件 并添加到集合中
                    findClassesInPackageByFile(packageName, filePath, recursive, classMap);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void DIInject() throws IllegalAccessException {
        Iterator<Map.Entry<String, Class<?>>> iterator = classMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Class<?>> entry = iterator.next();
            String key = entry.getKey();
            Class<?> clazz = entry.getValue();
            if (clazz.isAnnotationPresent(Service.class)) {
                Service service = clazz.getAnnotation(Service.class);
                if (!StringUtils.isEmpty(service.value())) {
                    key = service.value();
                }
            } else if (clazz.isAnnotationPresent(Component.class)) {
                Component component = clazz.getAnnotation(Component.class);
                if (!StringUtils.isEmpty(component.value())) {
                    key = component.value();
                }
            }

            Field[] fields = clazz.getDeclaredFields();
            Object object = beanMap.get(key);
            for (Field field : fields) {
                if (field.isAnnotationPresent(Autowired.class)) {
                    Class target = field.getType();
                    Iterator<Map.Entry<String, Object>> iteratorBean = beanMap.entrySet().iterator();
                    while (iteratorBean.hasNext()) {
                        Object value = iteratorBean.next().getValue();
                        if (target.isAssignableFrom(value.getClass())) {
                            field.setAccessible(true);
                            if (value.getClass().isAnnotationPresent(Service.class)) {
                                Service service1 = value.getClass().getAnnotation(Service.class);
                                Object target1;
                                if (!StringUtils.isEmpty(service1.value())) {
                                    target1 = beanMap.get(service1.value());
                                } else {
                                    target1 = beanMap.get(value.getClass().getName());
                                }
                                field.set(object, target1);
                            } else if (value.getClass().isAnnotationPresent(Component.class)) {
                                Component component = value.getClass().getAnnotation(Component.class);
                                Object target1;
                                if (!StringUtils.isEmpty(component.value())) {
                                    target1 = beanMap.get(component.value());
                                } else {
                                    target1 = beanMap.get(value.getClass().getName());
                                }
                                field.set(object, target1);
                            }
                            System.out.println();
                        }
                    }
                }
            }
        }
    }

    private static void generateProxy() {
        Iterator<Map.Entry<String, Object>> iterator = beanMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> entry = iterator.next();
            String key = entry.getKey();
            Object object = entry.getValue();
            Method[] methods = object.getClass().getDeclaredMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(Transactional.class)) {
                    ProxyFactory proxyFactory = (ProxyFactory) beanMap.get(ProxyFactory.class.getName());
                    Object proxy = proxyFactory.getJdkProxy(beanMap.get(key));
                    beanMap.put(key, proxy);
                    System.out.println();
                }
            }
        }
    }

    private static void findClassesInPackageByFile(String packageName, String packagePath,
                                                   final boolean recursive, Map<String, Class<?>> classMap) {
        // 获取此包的目录 建立一个File
        File dir = new File(packagePath);
        // 如果不存在或者 也不是目录就直接返回
        if (!dir.exists() || !dir.isDirectory()) {
            // log.warn("用户定义包名 " + packageName + " 下没有任何文件");
            return;
        }
        // 如果存在 就获取包下的所有文件 包括目录
        File[] dirfiles = dir.listFiles(new FileFilter() {
            // 自定义过滤规则 如果可以循环(包含子目录) 或则是以.class结尾的文件(编译好的java类文件)
            @Override
            public boolean accept(File file) {
                return (recursive && file.isDirectory()) || (file.getName().endsWith(".class"));
            }
        });
        // 循环所有文件
        assert dirfiles != null;
        for (File file : dirfiles) {
            // 如果是目录 则继续扫描
            if (file.isDirectory()) {
                findClassesInPackageByFile(packageName + "." + file.getName(), file.getAbsolutePath(), recursive, classMap);
            } else {
                // 如果是java类文件 去掉后面的.class 只留下类名
                String className = file.getName().substring(0, file.getName().length() - 6);
                try {
                    // 添加到集合中去
                    // classes.add(Class.forName(packageName + '.' +
                    // className));
                    // 经过回复同学的提醒，这里用forName有一些不好，会触发static方法，没有使用classLoader的load干净
                    Class current = Thread.currentThread().getContextClassLoader().loadClass(packageName + '.' + className);
                    if (current.isAnnotationPresent(Service.class)) {
                        classMap.put(packageName + '.' + className, current);
                        Object object = current.newInstance();
                        Service annotation = object.getClass().getAnnotation(Service.class);
                        if (!StringUtils.isEmpty(annotation.value())) {
                            beanMap.put(annotation.value(), object);
                        } else {
                            beanMap.put(packageName + '.' + className, object);
                        }
                    } else if (current.isAnnotationPresent(Component.class)) {
                        classMap.put(packageName + '.' + className, current);
                        Object object = current.newInstance();
                        Component annotation = object.getClass().getAnnotation(Component.class);
                        if (!StringUtils.isEmpty(annotation.value())) {
                            beanMap.put(annotation.value(), object);
                        } else {
                            beanMap.put(packageName + '.' + className, object);
                        }
                    }


                } catch (Exception e) {
                    // log.error("添加用户自定义视图类错误 找不到此类的.class文件");
                    e.printStackTrace();
                }
            }
        }
    }

}
