package leetcode;


import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Test {
    public static void main(String[] args) throws IOException {
        File file = new File("/Users/chris/workspace/xsource/java/src/main/java/leetcode");
        File[] files = file.listFiles();
        for (File f : files) {
            StringBuffer sb = new StringBuffer();
            Test.readToBuffer(sb, f);
            String s = sb.toString().replaceAll("", "");
    
            FileWriter writer = new FileWriter(f);
            writer.write(s);
            writer.flush();
            writer.close();
        }
    }
    
    public static void readToBuffer(StringBuffer buffer, File file) throws IOException {
        InputStream is = new FileInputStream(file);
        String line; // 用来保存每行读取的内容
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        line = reader.readLine(); // 读取第一行
        while (line != null) { // 如果 line 为空说明读完了
            buffer.append(line); // 将读到的内容添加到 buffer 中
            line = reader.readLine(); // 读取下一行
        }
        reader.close();
        is.close();
    }
    
}
