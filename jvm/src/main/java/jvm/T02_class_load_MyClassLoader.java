package jvm;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

public class T02_class_load_MyClassLoader extends ClassLoader{
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        File f = new File("/Users/chris/workspace/xsource/jvm/target/classes", name.replace(".", "/").concat(".class"));
        try {
            FileInputStream fis = new FileInputStream(f);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int b = 0;
            
            while ((b=fis.read()) !=0) {
                baos.write(b);
            }
            
            byte[] bytes = baos.toByteArray();
            baos.close();
            fis.close();//可以写的更加严谨
            
            return defineClass(name, bytes, 0, bytes.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.findClass(name); //throws ClassNotFoundException
    }
    
    public static void main(String[] args) throws Exception {
        ClassLoader l = new T02_class_load_MyClassLoader();
        Class clazz = l.loadClass("jvm.T05_gc_collector");
        Class clazz1 = l.loadClass("jvm.T05_gc_collector");
        
        System.out.println(clazz == clazz1);
    
        T05_gc_collector h = (T05_gc_collector)clazz.newInstance();
        
        System.out.println(l.getClass().getClassLoader());
        System.out.println(l.getParent());
        
        System.out.println(getSystemClassLoader());
    }
}
