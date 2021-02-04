package com.kie.pojo;

import org.junit.jupiter.api.Test;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.utils.KieHelper;

public class Demo {
    @Test
    public void testDrool() {
        KieServices kieServices = KieServices.Factory.get();
        //默认自动加载 META-INF/kmodule.xml
        KieContainer kieContainer = kieServices.getKieClasspathContainer();
        //kmodule.xml 中定义的 ksession name
        KieSession kieSession = kieContainer.newKieSession("ksession-rule");
        
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
    
    @Test
    public void testJBpm() {
        KieHelper kieHelper=new KieHelper();
        KieBase kieBase = kieHelper.addResource(ResourceFactory.newClassPathResource("bpmn/evaluation.bpmn")).build();
        KieSession ksession = kieBase.newKieSession();
        ProcessInstance processInstance = ksession.startProcess("org.jbpm.demo.evaluation");
    }
    
}
