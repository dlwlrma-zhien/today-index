package com.lcyy.stock.config;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author: dlwlrma
 * @data 2024年07月02日 18:42
 * @Description: TODO: RabbitMq的相关配置
 */
@Configuration
public class MqConfig {
   /**
    * 重新定义消息序列化的方式，改为基于json格式序列化和反序列化
    * @author dlwlrma
    * @date 2024/7/2 18:57
    * @return org.springframework.amqp.support.converter.MessageConverter
    */
    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }


}
