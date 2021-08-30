package web.springmvc.mine.annotation;

import java.lang.annotation.*;

/**
 * @author chris
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface MVCAutowired {
}
