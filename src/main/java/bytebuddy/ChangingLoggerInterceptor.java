package bytebuddy;

import net.bytebuddy.implementation.bind.annotation.Super;

import java.util.List;

public class ChangingLoggerInterceptor {
    public static List<String> log(String info, @Super MemoryDatabase zuper) {
        System.out.println("Calling database");
        try {
            return zuper.load(info + " (logged access)");
        } finally {
            System.out.println("Returned from database");
        }
    }
}
