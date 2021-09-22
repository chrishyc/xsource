package jvm.mine;


import org.junit.Test;
import org.openjdk.jol.vm.VM;

import java.nio.charset.Charset;

/**
 * HSDB  字符串实战
 * sudo java -cp ,:/Library/Java/JavaVirtualMachines/jdk1.8.0_212.jdk/Contents/Home/lib/sa-jdi.jar sun.jvm.hotspot.HSDB
 * https://zzcoder.cn/2019/12/06/HSDB%E4%BB%8E%E5%85%A5%E9%97%A8%E5%88%B0%E5%AE%9E%E6%88%98/
 */
public class T_03_ClassConstantPool_RuntimeConstantPool_Stringpool {
    
    /**
     * 参考:https://zhuanlan.zhihu.com/p/110307661
     */
    @Test
    public void testStringCreate() {
        String charStr = new String(new char[]{'a', 'b', 'c'});
        String byteStr = new String(new byte[]{98, 99, 100});
        String intStr = new String(new int[]{0x1F602}, 0, 1);
        String objStr = new String("abc");
        String literalStr = "abc";
        String internStr = literalStr.intern();
    }
    
    /**
     * https://zhuanlan.zhihu.com/p/110307661
     */
    @Test
    public void testStringContact() {
        /**
         *  0 ldc #10 <ab>
         *  2 astore_1
         */
        String s = "a" + "b";
        
        /**
         *   3 ldc #11 <b>
         *  5 astore_2
         *  6 ldc #10 <ab>
         *  8 astore_3
         */
        final String x = "b";
        String sfinal = "a" + x;
        
        /**
         *  (new StringBuilder()).append("a").append(x1).toString();
         *
         *
         *  9 ldc #11 <b>
         * 11 astore 4
         * 13 new #12 <java/lang/StringBuilder>
         * 16 dup
         * 17 invokespecial #13 <java/lang/StringBuilder.<init>>
         * 20 ldc #14 <a>
         * 22 invokevirtual #15 <java/lang/StringBuilder.append>
         * 25 aload 4
         * 27 invokevirtual #15 <java/lang/StringBuilder.append>
         * 30 invokevirtual #16 <java/lang/StringBuilder.toString>
         * 33 astore 5
         */
        String x1 = "b";
        String sVariabl = "a" + x1;
        
        /**
         * 35 ldc #17 <a1>
         * 37 astore 6
         */
        String sConst = "a" + 1;
        
    }
    
    @Test
    public void testStringCharArrByteArr() {
    
    
    }
    
    /**
     * -XX:+PrintStringTableStatistics
     * https://stackoverflow.com/questions/49522422/why-intern-does-not-work-with-literal-java
     * https://tech.meituan.com/2014/03/06/in-depth-understanding-string-intern.html
     */
    @Test
    public void test_String_pool_table() {
        //intern与string pool
        String charStr = new String(new char[]{'a', 'b', 'c'});//+1,string pool中没有缓存
        System.out.println(VM.current().addressOf(charStr));
        String charStrIntern = charStr.intern();//+0,charStr.intern会将charStr的引用放入string pool中
        System.out.println(VM.current().addressOf(charStrIntern));
        
        // 字面量与string pool
        String objStr = new String("bcd");//+2,创建字符串bcd并放入string pool中,然后创建objStr
        String literalStr = "abc";//+0,string pool中有缓存abc
        String internStr = literalStr.intern();//+0,string pool中有缓存则引用string pool中缓存,无则在string pool中持有该引用,并返回该引用
    }
    
    /**
     * -XX:+PrintStringTableStatistics
     */
    @Test
    public void test_symbol_table() {
        String charStr = new String(new char[]{'t', 'e', 's', 't', '_', 's', 'y', 'm', 'b', 'o', 'l', '_', 't', 'a', 'b', 'l'});//+1,string pool中没有缓存
        String charStr_string_pool = "test_symbol_tabl";//+1,string pool中有缓存
        
        String charStr1 = new String(new char[]{'t', 'e', 's', 't', '_', 's', 'y', 'm', 'b', 'o', 'l', '_', 't', 'a', 'b', 'l', 'e'});//+1,string pool中没有缓存
        System.out.println(VM.current().addressOf(charStr1));
        
        String charStr_string_pool2 = charStr1.intern();
        System.out.println(VM.current().addressOf(charStr_string_pool2));
        //charStr_string_pool2地址和charStr1地址不一样,说明符号引用已经被string pool缓存
        
        String charStr_string_pool1 = "test_symbol_table";//从string pool中获取
        System.out.println(VM.current().addressOf(charStr_string_pool1));
        //+0,string pool中有缓存,缓存来源方法字面量test_symbol_table
    }
    
    /**
     * 1.GBK的文字编码是双字节,不论中、英文字符均使用双字节来表示，只不过为区分中文，将其最高位都定成1
     * <p>
     * 2.Unicode是统一编码，UTF－8是Unicode的一种存储、传输方式,在Java中，字符的数据类型是char，而char类型的编码是 Unicode 编码，
     * 因此每一个char类型数据2字节16位，对应在内存中的数据就是字符的 Unicode 的码值。中文对应2个字节
     * <p>
     * 3.UTF－8编码则是用以解决国际上字符的一种多字节编码，它对英文使用8位（即一个字节），中文使用24位（三个字节）来编码
     * <p>
     * <p>
     * https://www.jianshu.com/p/eff7dd957d93
     */
    @Test
    public void testStringEncodeDecode() {
        // 解码
        String charStr = new String(new char[]{'a', 'b', 'c'});
        // 编码
        byte[] utf8Bytes = charStr.getBytes(Charset.defaultCharset());
        byte[] uniCodeBytes = charStr.getBytes(Charset.forName("Unicode"));
        byte[] gbkBytes = charStr.getBytes(Charset.forName("gbk"));
        
        // 解码
        String gbkStr = new String(new byte[]{(byte) 0xD5, (byte) 0xC5}, Charset.forName("gbk"));
        // 编码
        byte[] utf8Bytes1 = gbkStr.getBytes(Charset.defaultCharset());
        byte[] uniCodeBytes1 = gbkStr.getBytes(Charset.forName("Unicode"));
        byte[] gbkBytes1 = gbkStr.getBytes(Charset.forName("gbk"));
        
        // 解码
        String utf8Str = new String(new byte[]{(byte) 0xE5, (byte) 0xBC, (byte) 0xA0}, Charset.forName("utf-8"));
        // 编码
        byte[] utf8Bytes2 = utf8Str.getBytes(Charset.defaultCharset());
        byte[] uniCodeBytes2 = utf8Str.getBytes(Charset.forName("Unicode"));
        byte[] gbkBytes2 = utf8Str.getBytes(Charset.forName("gbk"));
    }
    
}
