package bytebuddy;

import jdk.nashorn.internal.ir.annotations.Ignore;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.implementation.FixedValue;

import java.lang.instrument.Instrumentation;

import static net.bytebuddy.matcher.ElementMatchers.isAnnotatedWith;
import static net.bytebuddy.matcher.ElementMatchers.named;

public class JavaAgent {
    public static void premain(String arguments, Instrumentation instrumentation) {
        new AgentBuilder.Default()
                .type(isAnnotatedWith(Ignore.class))
                .transform((builder, typeDescription, classLoader, module) -> builder.method(named("toString"))
                        .intercept(FixedValue.value("transformed"))).installOn(instrumentation);
    }
}
