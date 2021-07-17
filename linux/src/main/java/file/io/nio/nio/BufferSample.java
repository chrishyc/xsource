package file.io.nio.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class BufferSample {
    public static void main(String[] args) throws IOException {
        FileInputStream inputStream = new FileInputStream(new File("java/src/main/java/nio/Client.java"));
        FileChannel channel = inputStream.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(64);
        int ret = channel.read(byteBuffer);
        while (ret != -1) {
            byteBuffer.flip();
            while (byteBuffer.hasRemaining()) {
                System.out.println(byteBuffer.get());
            }
            byteBuffer.clear();
            ret = channel.read(byteBuffer);
        }
        inputStream.close();
    }
}
