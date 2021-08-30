package web.servlet.container.tomcat.mine.servlet;


import web.servlet.container.tomcat.mine.utils.HttpProtocolUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author chris
 */
public class StaticResourceUtil {

    /**
     * 获取静态资源文件的绝对路径
     * @param path
     * @return
     */
    public static String getAbsolutePath(String path) {
        String absolutePath = StaticResourceUtil.class.getResource("/").getPath();
        return absolutePath.replaceAll("\\\\","/") + path;
    }


    /**
     * 读取静态资源文件输入流，通过输出流输出
     */
    public static void outputStaticResource(InputStream inputStream, OutputStream outputStream) throws IOException {

        int count = 0;
        while(count == 0) {
            count = inputStream.available();
        }

        int resourceSize = count;
        // 输出http请求头,然后再输出具体内容
        outputStream.write(HttpProtocolUtil.getHttpHeader200(resourceSize).getBytes());

        // 读取内容输出, 已经读取的内容长度
        long written = 0 ;
        int byteSize = 1024;
        byte[] bytes = new byte[byteSize];

        while(written < resourceSize) {
            if(written  + byteSize > resourceSize) {
                byteSize = (int) (resourceSize - written);
                bytes = new byte[byteSize];
            }

            inputStream.read(bytes);
            outputStream.write(bytes);

            outputStream.flush();
            written+=byteSize;
        }
    }
}
