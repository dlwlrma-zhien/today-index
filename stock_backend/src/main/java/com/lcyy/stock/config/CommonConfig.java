package com.lcyy.stock.config;

import com.lcyy.stock.pojo.vo.StockInfoConfig;
import com.lcyy.stock.utils.IdWorker;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
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
@EnableConfigurationProperties({StockInfoConfig.class})//像开关一样需要时候再开启
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

    /**
     * TODO： 基于雪花算法的工具类 保证sessionid 的唯一性
     * @author dlwlrma
     * @date 2024/6/20 15:43
     * @return com.lcyy.stock.utils.IdWorker
     */
    @Bean
    public IdWorker idWorker(){
        /**
         * TODO: 参数一：机器id
         *       参数二：机房id
         *       在实际开发中机器和机房id都是由运维人员规划
         * @author dlwlrma
         * @date 2024/6/20 15:41
         * @return com.lcyy.stock.utils.IdWorker
         */
        return new IdWorker(1l,2l);
    }
}
