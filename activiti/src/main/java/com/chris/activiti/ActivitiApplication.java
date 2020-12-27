package com.chris.activiti;

import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class ActivitiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ActivitiApplication.class, args);
    }
    public int solution(String S) {
        // write your code in Java SE 8
        if(S==null||S.length()==0){
            return 0;
        }
        int count=0;
        while(!allZeros(S)){
            char low=S.charAt(S.length()-1);
            S=S.substring(0,S.length()-2);
            if(low=='1'){
                S=S+"1";
            }
            count++;
        }
        return count;
    }
    
    public boolean allZeros(String S){
        if(S==null||S.length()==0){
            return true;
        }
        for(int i=0;i<S.length();i++){
            if(S.charAt(i)!='0'){
                return false;
            }
        }
        return true;
    }

}
