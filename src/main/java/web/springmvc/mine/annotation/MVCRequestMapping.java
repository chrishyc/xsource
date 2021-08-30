package web.springmvc.mine.annotation;

import java.lang.annotation.*;

/**
 * @author chris
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MVCRequestMapping {
    String value() default "";
}
