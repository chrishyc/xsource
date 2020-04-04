package bytebuddy;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.*;
import net.bytebuddy.matcher.ElementMatchers;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;

import static net.bytebuddy.matcher.ElementMatchers.*;

public class Methods {
    public void method() throws IllegalAccessException, InstantiationException {
        String toString = new ByteBuddy()
                .subclass(Object.class)
                .name("example.Type")
                .make()
                .load(getClass().getClassLoader())
                .getLoaded()
                .newInstance() // Java reflection API
                .toString();
        System.out.println(toString);
    }

    public void method$DSL() throws IllegalAccessException, InstantiationException {
        String toString = new ByteBuddy()
                .subclass(Object.class)
                .name("example.Type")
                .method(named("toString").and(returns(String.class)).and(takesArguments(0))).intercept(FixedValue.value("Hello World!"))
                .make()
                .load(getClass().getClassLoader())
                .getLoaded()
                .newInstance()
                .toString();
        System.out.println(toString);
    }

    public void method$MatcherStack() throws IllegalAccessException, InstantiationException {
        Foo dynamicFoo = new ByteBuddy()
                .subclass(Foo.class)
                .method(isDeclaredBy(Foo.class)).intercept(FixedValue.value("One!"))
                .method(named("foo")).intercept(FixedValue.value("Two!"))
                .method(named("foo").and(takesArguments(1))).intercept(FixedValue.value("Three!"))
                .make()
                .load(getClass().getClassLoader())
                .getLoaded()
                .newInstance();
    }

    public void method$Fixed() {
        new ByteBuddy()
                .subclass(Foo.class)
                .method(isDeclaredBy(Foo.class)).intercept(FixedValue.value(0))
                .make();
    }

    class Foo {
        public String bar() {
            return null;
        }

        public String foo() {
            return null;
        }

        public String foo(Object o) {
            return null;
        }
    }

    public static class Source {
        public String hello(String name) {
            return null;
        }
    }

    public static class Target {
        public static String intercept(String name) {
            return "Hello " + name + "!";
        }

        public static String intercept(int i) {
            return Integer.toString(i);
        }

        public static String intercept(Object o) {
            return o.toString();
        }

        public void foo$0(Object o1, Object o2) {

        }

        public void foo$1(@Argument(0) Object o1, @Argument(1) Object o2) {

        }

        public void foo$2(@AllArguments Object[] array) {

        }

        public void foo$3(@This Object o) {

        }

    }


    class LoggingMemoryDatabase extends MemoryDatabase {

        private class LoadMethodSuperCall implements Callable {

            private final String info;

            private LoadMethodSuperCall(String info) {
                this.info = info;
            }

            @Override
            public Object call() throws Exception {
                return LoggingMemoryDatabase.super.load(info);
            }
        }

        @Override
        public List<String> load(String info) {
            try {
                return LoggerInterceptor.log(new LoadMethodSuperCall(info));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public void superCall() throws IOException {
        new ByteBuddy()
                .subclass(MemoryDatabase.class)
                .method(named("load").or(ElementMatchers.named("unload")))
                .intercept(MethodDelegation.to(LoggerInterceptor.class))
                .make()
                .saveIn(new File("/Users/chris/byteclass/bytebuddy"));

//                .load(getClass().getClassLoader())
//                .getLoaded()
//                .newInstance()
//                .load("hello");
    }

    public void superS() throws IOException {
        new ByteBuddy()
                .subclass(MemoryDatabase.class)
                .method(named("load").or(ElementMatchers.named("unload")))
                .intercept(MethodDelegation.to(ChangingLoggerInterceptor.class))
                .make()
                .saveIn(new File("/Users/chris/byteclass/bytebuddy"));

//                .load(getClass().getClassLoader())
//                .getLoaded()
//                .newInstance()
//                .load("hello");
    }

    public void morph() throws IOException {
        new ByteBuddy()
                .subclass(MemoryDatabase.class)
                .method(named("load").or(ElementMatchers.named("unload")))
                .intercept(MethodDelegation
                        .withDefaultConfiguration()
                        .withBinders(Morph.Binder.install(Morphing.class))
                        .to(MorphInterceptor.class))
                .make()
                .saveIn(new File("/Users/chris/byteclass/bytebuddy"));

//                .load(getClass().getClassLoader())
//                .getLoaded()
//                .newInstance()
//                .load("hello");
    }


    public void methodDelegation() throws IllegalAccessException, InstantiationException {
        String helloWorld = new ByteBuddy()
                .subclass(Source.class)
                .method(named("hello")).intercept(MethodDelegation.to(Target.class))
                .make()
                .load(getClass().getClassLoader())
                .getLoaded()
                .newInstance()
                .hello("World");
        System.out.println(helloWorld);
    }


    class Loop {
        public String loop(String value) {
            return value;
        }

        public int loop(int value) {
            return value;
        }
    }

    static class Interceptor {
        @RuntimeType
        public static Object intercept(@RuntimeType Object value) {
            System.out.println("Invoked method with: " + value);
            return value;
        }
    }

    public static void main(String[] args) {
        try {
            new Methods().superS();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
