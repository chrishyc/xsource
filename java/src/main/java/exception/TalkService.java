package exception;

public class TalkService {
    public void say() throws Exception {
        try {
            new HelloService().sayHi();
        } catch (Exception e) {
            System.out.println("hello,catch you");
        }
    }
}
