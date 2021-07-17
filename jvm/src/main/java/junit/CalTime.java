package junit;

import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Target({ TYPE, METHOD, ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@ExtendWith(CalTestTimeExtension.class)
public @interface CalTime {
}
