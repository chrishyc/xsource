package com.kie.pojo;

import org.junit.jupiter.api.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class Demo {
    @Test
    public void test() {
        KieServices kieServices = KieServices.Factory.get();
        //默认自动加载 META-INF/kmodule.xml
        KieContainer kieContainer = kieServices.getKieClasspathContainer();
        //kmodule.xml 中定义的 ksession name
        KieSession kieSession = kieContainer.newKieSession("all-rules");
        
        Person p1 = new Person();
        p1.setAge(20);
        Car car = new Car();
        car.setPerson(p1);
        
        kieSession.insert(car);
        
        int count = kieSession.fireAllRules();
        
        System.out.println(count);
        System.out.println(car.getDiscount());
        
        kieSession.dispose();
    }
}
