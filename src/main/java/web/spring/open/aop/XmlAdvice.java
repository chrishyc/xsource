package web.spring.open.aop;

public class XmlAdvice {
    public void before() {
        System.out.println("before:当前时间:" + System.currentTimeMillis());
    }
    public void afterReturn(){
        System.out.println("afterReturn:当前时间:" + System.currentTimeMillis());

    }
    public void afterThrowing(RuntimeException ex){
        System.out.println("afterThrowing:当前时间:" + System.currentTimeMillis());

    }
    public void after(){
        System.out.println("after:当前时间:" + System.currentTimeMillis());

    }

    public void round(){
        System.out.println("round:当前时间:" + System.currentTimeMillis());
    }
}
