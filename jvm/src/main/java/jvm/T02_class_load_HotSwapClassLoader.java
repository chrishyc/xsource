package jvm;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.HashSet;

public class T02_class_load_HotSwapClassLoader extends ClassLoader {
    private String basedir; // 需要该类加载器直接加载的类文件的基目录
    private HashSet dynaclazns; // 需要由该类加载器直接加载的类名
    
    /**
     * 构造方法，加载器被创建时就根据传去的参数加载了所有需要由该加载器加载的类
     *
     * @param basedir 需要被热加载类的class文件地址
     * @param clazns  需要被热加载类的class文件名称数组
     * @throws IOException
     */
    public T02_class_load_HotSwapClassLoader(String basedir, String[] clazns) throws IOException {
        super(null); // 指定父类加载器为 null
        this.basedir = basedir;
        dynaclazns = new HashSet();
        loadClassByMe(clazns);
    }
    
    private void loadClassByMe(String[] clazns) throws IOException {
        for (int i = 0; i < clazns.length; i++) {
            loadDirectly(clazns[i]);
            dynaclazns.add(clazns[i]);
        }
    }
    
    private Class loadDirectly(String name) throws IOException {
        Class cls = null;
        StringBuffer sb = new StringBuffer(basedir);
        String classname = name.replace('.', File.separatorChar) + ".class";
        sb.append(File.separator + classname);
        File classF = new File(sb.toString());
        cls = instantiateClass(name, new FileInputStream(classF), classF.length());
        return cls;
    }
    
    private Class instantiateClass(String name, InputStream fin, long len) throws IOException {
        byte[] raw = new byte[(int) len];
        fin.read(raw);
        fin.close();
        return defineClass("jvm.T02_classloaderLevel", raw, 0, raw.length);//调用父类的defineClass方法，进行类的装载
    }
    
    protected Class loadClass(String name, boolean resolve)
            throws ClassNotFoundException {
        Class cls = null;
        cls = findLoadedClass(name);//调用父类的findLoadedClass方法，获取已经被该加载器加载的类
        if (!this.dynaclazns.contains(name) && cls == null)
            cls = getSystemClassLoader().loadClass(name);//不需要由该类加载器直接加载的类切未被加载过，调用系统系统的类加载器进行加载
        if (cls == null)
            throw new ClassNotFoundException(name);
        if (resolve)
            resolveClass(cls);
        return cls;
    }
    
    public static void main(String[] args) {
        while (true) {
            try {
                // 每次都创建出一个新的类加载器
                T02_class_load_HotSwapClassLoader cl = new T02_class_load_HotSwapClassLoader("/Users/chris/workspace/xsource/jvm/target/classes/jvm", new String[]{"T02_classloaderLevel"});
                Class cls = cl.loadClass("jvm.T02_classloaderLevel");
                Object helloWorld = cls.newInstance();
                
                Method m = helloWorld.getClass().getMethod("test", new Class[]{});
                m.invoke(helloWorld, new Object[]{});
                
                Thread.sleep(3000L); // 1秒执行一次
                System.out.println();
                
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    
    
}
