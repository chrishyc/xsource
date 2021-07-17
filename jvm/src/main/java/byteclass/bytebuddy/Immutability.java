package byteclass.bytebuddy;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.NamingStrategy;
import net.bytebuddy.dynamic.DynamicType;

public class Immutability {
    /**
     * the invocation of the withNamingStrategy method returns a customized ByteBuddy instance
     * which is however lost. As a result, the dynamic type is created using the default configuration
     * which was originally created.
     *
     * Byte Buddy's API is expressed by fully immutable
     *  * components and is therefore thread-safe.
     */
    public void Immute() {
        ByteBuddy byteBuddy = new ByteBuddy();
        byteBuddy.with(new NamingStrategy.SuffixingRandom("suffix"));
        DynamicType.Unloaded<?> dynamicType = byteBuddy.subclass(Object.class).make();
    }
}
