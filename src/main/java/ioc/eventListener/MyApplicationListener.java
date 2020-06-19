package ioc.eventListener;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

public class MyApplicationListener implements ApplicationListener {
    
    public MyApplicationListener() {
        System.out.println("MyApplicationListener start------------------------------");
    }
    
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        System.out.println(event == null ? "event" : event.toString());
    }
}
