package com.mthree.bsm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {

    public static void main(String[] args) {
        
        Runtime r=Runtime.getRuntime();  
        
        r.addShutdownHook(new Thread(){
            public void run(){  
                System.out.println("shut down hook task completed..");  
             }  
        });  
        
        SpringApplication.run(App.class);
    }

}
