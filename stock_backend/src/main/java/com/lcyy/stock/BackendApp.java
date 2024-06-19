package com.lcyy.stock;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author: dlwlrma
 * @data 2024年06月18日 22:06
 * @Description: TODO: springboot启动类
 */
//扫描持久层mapper接口，生成代理对象，并维护到springIOC容器中
@MapperScan("com.lcyy.stock.mapper")
@SpringBootApplication
public class BackendApp {
    public static void main(String[] args) {
        SpringApplication.run(BackendApp.class,args);
    }
}
