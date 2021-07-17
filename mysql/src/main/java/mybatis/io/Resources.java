package mybatis.io;

import java.io.InputStream;

public class Resources {
    public static InputStream getInputStream(String path) {
        return Resources.class.getClassLoader().getResourceAsStream(path);
    }
}
