package com.lcyy.stock.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author: dlwlrma
 * @data 2024年06月29日 15:01
 * @Description: TODO: 定义http客户端bean
 */
@Configuration
public class HttpClientConfig {


    /**
     * 定义http客户端bean
     * @author dlwlrma
     * @date 2024/6/29 15:02
     * @return org.springframework.web.client.RestTemplate
     */
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
