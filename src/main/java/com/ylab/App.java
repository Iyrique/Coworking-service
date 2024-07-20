package com.ylab;


import com.ylab.configAspect.EnableLogging;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableLogging
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
