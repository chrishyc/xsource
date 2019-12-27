package bytebuddy;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.NamingStrategy;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.ClassFileLocator;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.dynamic.loading.ClassReloadingStrategy;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.pool.TypePool;

import java.io.File;
import java.io.IOException;

public class BuddyClass {

    public void unloaded() throws IOException {
        new ByteBuddy()
                .subclass(Foo.class)
                .make()
                .saveIn(new File("/Users/chris/byteclass/bytebuddy"));
    }

    public void subClass() throws Exception {
        new ByteBuddy()
                .subclass(Foo.class)
                .method(ElementMatchers.returns(String.class))
                .intercept(FixedValue.value("Hello World!"))
                .make()
                .saveIn(new File("/Users/chris/byteclass/bytebuddy"));

    }

    public void redefine() throws Exception {
        new ByteBuddy()
                .redefine(Foo.class)
                .method(ElementMatchers.returns(String.class))
                .intercept(FixedValue.value("Hello World!"))
                .make()
                .saveIn(new File("/Users/chris/byteclass/bytebuddy"));

    }

    public void rebase() throws Exception {
        new ByteBuddy()
                .rebase(Foo.class)
//                .method(ElementMatchers.returns(String.class))
//                .intercept(FixedValue.value("Hello World!"))
                .make()
                .saveIn(new File("/Users/chris/byteclass/bytebuddy"));

    }

    public void namingStrategy() throws IOException {
        new ByteBuddy()
                .with(new NamingStrategy.AbstractBase() {
                    @Override
                    protected String name(TypeDescription superClass) {
                        return "Chris";
                    }
                })
                .subclass(Foo.class)
//                .name("HelloObject")
                .make()
                .saveIn(new File("/Users/chris/byteclass/bytebuddy"));
    }



    public void loading() {
        Class<?> type = new ByteBuddy()
                .subclass(Object.class)
                .make()
                .load(getClass().getClassLoader(), ClassLoadingStrategy.Default.WRAPPER)
                .getLoaded();
    }

    public void agent() {
        ByteBuddyAgent.install();
        Foo foo = new Foo();
        new ByteBuddy()
                .redefine(Bar.class)
                .name(Foo.class.getName())
                .make()
                .load(Foo.class.getClassLoader(), ClassReloadingStrategy.fromInstalledAgent());
        System.out.println(foo.method());
    }

    public void unload() throws NoSuchFieldException {
        TypePool typePool = TypePool.Default.ofSystemLoader();
        new ByteBuddy()
                .redefine(typePool.describe("bytebuddy.Bar").resolve(), // do not use 'Bar.class'
                        ClassFileLocator.ForClassLoader.ofSystemLoader())
                .defineField("qux", String.class) // we learn more about defining fields later
                .make()
                .load(ClassLoader.getSystemClassLoader());
    }


    public static void main(String[] args) {
        try {
            new BuddyClass().unloaded();
            System.out.println(Bar.class.getDeclaredField("qux"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
