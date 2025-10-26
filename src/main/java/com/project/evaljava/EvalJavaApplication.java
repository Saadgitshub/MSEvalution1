package com.project.evaljava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.project.evaljava", "com.project.evaljava.dao"})
public class EvalJavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(EvalJavaApplication.class, args);
    }

}
