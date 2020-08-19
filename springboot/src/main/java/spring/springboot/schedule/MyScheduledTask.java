package spring.springboot.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author chris
 */
@Component
public class MyScheduledTask {
    @PostConstruct()
    @Scheduled(cron = "*/5 * * * * ?")
    public void dailyRefreshMacKey() {
        System.out.println("MyScheduledTask:" + System.currentTimeMillis());
    }
}
