package web.springmvc.mine.annotation;

import java.lang.annotation.*;

/**
 * @author chris
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MVCService {
}
