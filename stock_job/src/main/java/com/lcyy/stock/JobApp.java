package com.lcyy.stock;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author: dlwlrma
 * @data 2024年06月29日 14:57
 * @Description: TODO: Job工程启动类
 */
@SpringBootApplication
@MapperScan("com.lcyy.stock.mapper")
public class JobApp {
    public static void main(String[] args) {
        SpringApplication.run(JobApp.class,args);
    }
}
