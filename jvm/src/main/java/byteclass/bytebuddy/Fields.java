package byteclass.bytebuddy;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.implementation.FieldAccessor;
import net.bytebuddy.implementation.MethodDelegation;

import static net.bytebuddy.matcher.ElementMatchers.isDeclaredBy;
import static net.bytebuddy.matcher.ElementMatchers.not;

public class Fields {
    class UserType {
        public String doSomething() {
            return null;
        }
    }

    interface Interceptor {
        String doSomethingElse();
    }

    interface InterceptionAccessor {
        Interceptor getInterceptor();

        void setInterceptor(Interceptor interceptor);
    }

    interface InstanceCreator {
        Object makeInstance();
    }

    public void fields() {
        Class<? extends UserType> dynamicUserType = new ByteBuddy()
                .subclass(UserType.class)
                .method(not(isDeclaredBy(Object.class)))
                .intercept(MethodDelegation.toField("interceptor"))
                .defineField("interceptor", Interceptor.class, Visibility.PRIVATE)
                .implement(InterceptionAccessor.class).intercept(FieldAccessor.ofBeanProperty())
                .make()
                .load(getClass().getClassLoader())
                .getLoaded();
    }
}
