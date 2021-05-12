package pattern;

/**
 * 1.解决对象初始化时的参数扩展问题
 * 2.使用final优化初始化
 */
public class BuilderPattern {
    private final int age;
    
    public BuilderPattern(Builder builder) {
        this.age = builder.age;
    }
    
    public static class Builder {
        private int age;
        
        public Builder age(int age) {
            this.age = age;
            return this;
        }
        
        public BuilderPattern build() {
            return new BuilderPattern(this);
        }
    }
}
