package com.lcyy.stock.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author: dlwlrma
 * @data 2024年06月19日 18:35
 * @Description: TODO: 定义密码匹配器
 */
@Configuration
public class CommonConfig {

    /**
     * TODO: 密码加密匹配器，密码在数据库中存储的的是密文，而现实中是明文，因此使用密码匹配机制
     * @author dlwlrma
     * @date 2024/6/19 18:37
     * @return org.springframework.security.crypto.password.PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
