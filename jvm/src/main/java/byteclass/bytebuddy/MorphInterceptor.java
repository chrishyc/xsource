package byteclass.bytebuddy;

import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Morph;

public class MorphInterceptor {
    public static String getNum(@Morph Morphing<String> m, @AllArguments Object[] args) {
        // This will work!
        return m.invoke(args);
    }
}
