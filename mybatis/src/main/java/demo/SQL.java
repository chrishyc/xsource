package demo;

/**
 * @author chris
 */

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE,ElementType.TYPE})
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface SQL {
    String value() default "d";
}
