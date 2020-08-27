package com.mthree.bsm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {

    public static void main(String[] args) {
        
        Runtime r = Runtime.getRuntime();
        
        r.addShutdownHook(new Thread(() -> System.out.println("Thanks for suffering with us!")));
        
        SpringApplication.run(App.class);
    }

}
