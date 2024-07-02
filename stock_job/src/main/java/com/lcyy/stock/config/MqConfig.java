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

  /**
   * 定义主题交互机
   * 第一个true为是否持久化操作，第二个false数没有持久化就会删除
   * @author dlwlrma
   * @date 2024/7/2 18:55
   * @return org.springframework.amqp.core.TopicExchange
   */
    @Bean
    public TopicExchange stockTopicExchange(){
        return new TopicExchange("stockExchange",true,false);
    }

    /**
     * 定义国内股票队列
     * @author dlwlrma
     * @date 2024/7/2 18:55
     * @return org.springframework.amqp.core.Queue
     */
    @Bean
    public Queue innerMarketQueue(){
        return new Queue("innerMarketQueue",true);
    }

    /**
     * 绑定国内股票队列到交换机
     * @author dlwlrma
     * @date 2024/7/2 18:54
     * @return org.springframework.amqp.core.Binding
     */
    @Bean
    public Binding bindInner2stock(){
       return BindingBuilder.bind(innerMarketQueue()).to(stockTopicExchange()).with("Inner.Market");
    }

}
