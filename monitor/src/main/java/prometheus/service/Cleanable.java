package prometheus.service;

import java.util.concurrent.atomic.LongAdder;

public interface Cleanable {
    
    LongAdder counter = new LongAdder();
    
    static void mark() {
        counter.add(1);
    }
    
    static void clean() {
        if (counter.sum() > 20) {
            counter.sumThenReset();
            PrometheusMeter.registry.clear();
        }
    }
    
}
