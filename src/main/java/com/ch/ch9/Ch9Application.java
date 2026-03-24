package com.ch.ch9;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.ch.ch9.repository.before")
@SpringBootApplication
public class Ch9Application {
    public static void main(String[] args) {
        SpringApplication.run(Ch9Application.class, args);
    }
}