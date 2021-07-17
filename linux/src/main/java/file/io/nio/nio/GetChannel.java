package file.io.nio.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 1、mark：初始值为-1，用于备份当前的position;
 *
 * 2、position：初始值为0，position表示当前可以写入或读取数据的位置，当写入或读取一个数据后，position向前移动到下一个位置；
 *
 * 3、limit：写模式下，limit表示最多能往Buffer里写多少数据，等于capacity值；读模式下，limit表示最多可以读取多少数据。
 *
 * 4、capacity：缓存数组大小
 *
 * 5.flip()：Buffer有两种模式，写模式和读模式，flip后Buffer从写模式变成读模式
 *
 * https://mp.weixin.qq.com/s?__biz=MzIwMzY1OTU1NQ==&mid=2247483792&idx=1&sn=bf48352a05d4727b69b9e3dd02663b91&chksm=96cd41dca1bac8caafb4f4a90fd1a6887c9e49fd221225080f657037ebe57c2e8cd438198c08&scene=21#wechat_redirect
 */
public class GetChannel {
    private static String name = "java/src/main/java/nio/data.txt";
    private static final int BSIZE = 1024;
    
    public static void main(String[] args) {
        // 写入一个文件:
        try (
                FileChannel fc = new FileOutputStream(name)
                        .getChannel()
        ) {
            fc.write(ByteBuffer
                    .wrap("Some text ".getBytes()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // 在文件尾添加：
        try (
                FileChannel fc = new RandomAccessFile(
                        name, "rw").getChannel()
        ) {
            fc.position(fc.size()); // 移动到结尾
            fc.write(ByteBuffer
                    .wrap("Some more".getBytes()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // 读取文件e:
        try (
                FileChannel fc = new FileInputStream(name)
                        .getChannel()
        ) {
            ByteBuffer buff = ByteBuffer.allocate(BSIZE);
            fc.read(buff);
            buff.flip();
            while (buff.hasRemaining())
                System.out.write(buff.get());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.flush();
    }
}
