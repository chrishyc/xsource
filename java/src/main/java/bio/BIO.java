package bio;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * openat(AT_FDCWD, "/chris/out.txt", O_WRONLY|O_CREAT|O_TRUNC, 0666) = 16
 * fstat(16, {st_mode=S_IFREG|0644, st_size=0, ...}) = 0
 * write(16, "hello world", 11)            = 11
 */
public class BIO {
    public static void main(String[] args) throws IOException {
        File file = new File("/Users/chris/workspace/xsource/java/src/main/java/bio/out.txt");
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));
        out.write("hello world".getBytes());
        out.flush();
    }
}
