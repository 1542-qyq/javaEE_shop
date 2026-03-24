package com.ch.ch9.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@MapperScan(
        basePackages = "com.ch.ch9.mapper",
        nameGenerator = PackageBasedBeanNameGenerator.class
)
public class MyBatisConfig {


}