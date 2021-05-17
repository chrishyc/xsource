package prometheus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import prometheus.service.ThreadPoolMonitor;

@Controller
public class ThreadPoolMonitorController {
    
    @Autowired
    private ThreadPoolMonitor threadPoolMonitor;
    
    @GetMapping(value = "/shortTimeWork")
    public ResponseEntity<String> shortTimeWork() {
        threadPoolMonitor.shortTimeWork();
        return ResponseEntity.ok("success");
    }
    
    @GetMapping(value = "/longTimeWork")
    public ResponseEntity<String> longTimeWork() {
        threadPoolMonitor.longTimeWork();
        return ResponseEntity.ok("success");
    }
    
    @GetMapping(value = "/clearTaskQueue")
    public ResponseEntity<String> clearTaskQueue() {
        threadPoolMonitor.clearTaskQueue();
        return ResponseEntity.ok("success");
    }
}
