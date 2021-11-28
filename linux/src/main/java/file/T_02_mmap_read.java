package file;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Random;

/**
 * https://blog.csdn.net/zhxdick/article/details/81130102
 */
public class T_02_mmap_read {
    public static void main(String[] args) throws Exception {
        //记录开始时间
        long start = System.currentTimeMillis();
        //通过RandomAccessFile的方式获取文件的Channel，这种方式针对随机读写的文件较为常用，我们用文件一般是随机读写
        RandomAccessFile randomAccessFile = new RandomAccessFile("/Users/chris/workspace/xsource/linux/src/main/java/file/FileMmapTest.txt", "rw");
        FileChannel channel = randomAccessFile.getChannel();
        System.out.println("FileChannel初始化时间：" + (System.currentTimeMillis() - start) + "ms");
        
        //内存映射文件，模式是READ_WRITE，如果文件不存在，就会被创建
        MappedByteBuffer mappedByteBuffer1 = channel.map(FileChannel.MapMode.READ_WRITE, 0, 1524 * 1024 * 1024);
        MappedByteBuffer mappedByteBuffer2 = channel.map(FileChannel.MapMode.READ_WRITE, 0, 1524 * 1024 * 1024);
        
        System.out.println("MMAPFile初始化时间：" + (System.currentTimeMillis() - start) + "ms");
        
        start = System.currentTimeMillis();
        testFileChannelSequentialRW(channel);
        System.out.println("FileChannel顺序读写时间：" + (System.currentTimeMillis() - start) + "ms");
        
        start = System.currentTimeMillis();
        testFileMMapSequentialRW(mappedByteBuffer1, mappedByteBuffer2);
        System.out.println("MMAPFile顺序读写时间：" + (System.currentTimeMillis() - start) + "ms");
        
//        start = System.currentTimeMillis();
//        try {
//            testFileChannelRandomRW(channel);
//            System.out.println("FileChannel随机读写时间：" + (System.currentTimeMillis() - start) + "ms");
//        } finally {
//            randomAccessFile.close();
//        }
//
//        //文件关闭不影响MMAP写入和读取
//        start = System.currentTimeMillis();
//        testFileMMapRandomRW(mappedByteBuffer1, mappedByteBuffer2);
//        System.out.println("MMAPFile随机读写时间：" + (System.currentTimeMillis() - start) + "ms");
    }
    
    
    public static void testFileChannelSequentialRW(FileChannel fileChannel) throws Exception {
        byte[] bytes = "测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2".getBytes();
        byte[] to = new byte[bytes.length];
        //分配直接内存，减少复制
        ByteBuffer byteBuffer = ByteBuffer.allocate(bytes.length);
        //顺序写入
        for (int i = 0; i < 100000; i++) {
            byteBuffer.put(bytes);
            byteBuffer.flip();
            fileChannel.write(byteBuffer);
            byteBuffer.flip();
        }
        
//        fileChannel.position(0);
//        //顺序读取
//        for (int i = 0; i < 1; i++) {
//            fileChannel.read(byteBuffer);
//            byteBuffer.flip();
//            byteBuffer.get(to);
//            byteBuffer.flip();
//        }
    }
    
    public static void testFileMMapSequentialRW(MappedByteBuffer mappedByteBuffer1, MappedByteBuffer mappedByteBuffer2) throws Exception {
        byte[] bytes = "测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2".getBytes();
        byte[] to = new byte[bytes.length];
        
        //顺序写入
        for (int i = 0; i < 100000; i++) {
            mappedByteBuffer1.put(bytes);
        }
//        //顺序读取
//        for (int i = 0; i < 1; i++) {
//            mappedByteBuffer2.get(to);
//        }
    }
    
    public static void testFileChannelRandomRW(FileChannel fileChannel) throws Exception {
        try {
            byte[] bytes = "测试字符串1测试字符串1测试字符串1测试字符串1测试字符串1测试字符串1测试字符串1测试字符串1测试字符串1测试字符串1测试字符串1".getBytes();
            byte[] to = new byte[bytes.length];
            //分配直接内存，减少复制
            ByteBuffer byteBuffer = ByteBuffer.allocate(bytes.length);
            //随机写入
            for (int i = 0; i < 100000; i++) {
                byteBuffer.put(bytes);
                byteBuffer.flip();
                fileChannel.position(new Random(i).nextInt(bytes.length*100000));
                fileChannel.write(byteBuffer);
                byteBuffer.flip();
            }
            //随机读取
            for (int i = 0; i < 100000; i++) {
                fileChannel.position(new Random(i).nextInt(bytes.length*100000));
                fileChannel.read(byteBuffer);
                byteBuffer.flip();
                byteBuffer.get(to);
                byteBuffer.flip();
            }
        } finally {
            fileChannel.close();
        }
    }
    
    public static void testFileMMapRandomRW(MappedByteBuffer mappedByteBuffer1, MappedByteBuffer mappedByteBuffer2) throws Exception {
        byte[] bytes = "测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2测试字符串2".getBytes();
        byte[] to = new byte[bytes.length];
        
        //随机写入
        for (int i = 0; i < 100000; i++) {
            mappedByteBuffer1.position(new Random(i).nextInt(bytes.length*100000));
            mappedByteBuffer1.put(bytes);
        }
        //随机读取
        for (int i = 0; i < 100000; i++) {
            mappedByteBuffer2.position(new Random(i).nextInt(bytes.length*100000));
            mappedByteBuffer2.get(to);
        }
    }

}
