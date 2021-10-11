package jvm;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class T02_ClassReloading {
    public static void main(String[] args) throws ClassNotFoundException {
        T02_MyClassLoader msbClassLoader = new T02_MyClassLoader();
        Class clazz = msbClassLoader.loadClass("jvm.T01_class_instruction_i_add_add");
        System.out.println(clazz.getClassLoader());
        msbClassLoader = null;
        System.out.println(clazz.hashCode());
        
        msbClassLoader = null;
        
        msbClassLoader = new T02_MyClassLoader();
        Class clazz1 = msbClassLoader.loadClass("jvm.T01_class_instruction_i_add_add");
        System.out.println(clazz1.hashCode());
        
        System.out.println(clazz == clazz1);
    }
    
    private static class MyLoader extends ClassLoader {
        @Override
        public Class<?> loadClass(String name) throws ClassNotFoundException {

            File f = new File("/Users/chris/workspace/xsource/jvm/target/classes/"+name.replace(".", "/").concat(".class"));

            if (!f.exists()) return super.loadClass(name);

            try {

                InputStream is = new FileInputStream(f);

                byte[] b = new byte[is.available()];
                is.read(b);
                return defineClass(name, b, 0, b.length);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return super.loadClass(name);
        }
    }
    
    @Test
    public void testMyLoader() throws ClassNotFoundException {
        MyLoader m = new MyLoader();
        Class clazz = m.loadClass("jvm.T02_classloaderLevel");
        
        m = new MyLoader();
        Class clazzNew = m.loadClass("jvm.T02_classloaderLevel");
        
        System.out.println(clazz == clazzNew);
    }
}
