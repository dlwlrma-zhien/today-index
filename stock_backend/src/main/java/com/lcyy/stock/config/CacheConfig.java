package com.lcyy.stock.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author: dlwlrma
 * @data 2024年06月20日 15:12
 * @Description: TODO: 自定义redis序列化方式，避免使用默认的jdk序列化方式
 *                     jdk序列化方式问题： 1.阅读体验很差，会出现乱码问题
 *                                      2.序列化内容体积比较大，太占内存
 */
@Configuration
public class CacheConfig {

    /**
     * TODO: 自定义模板对象，要保证Bean的名称必须叫redisTemplate，否则场景依赖会自定装配，导致相同类型的bean出现多个
     * @author dlwlrma
     * @date 2024/6/20 15:20
     * @param factory
     * @return org.springframework.data.redis.core.RedisTemplate
     */
    @Bean
    public RedisTemplate redisTemplate(@Autowired RedisConnectionFactory factory){
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        //2.为不同的数据结构设置不同的序列化方案
        //设置key序列化方式
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //设置value序列化方式
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
        //设置hash中field字段序列化方式
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        //设置hash中value的序列化方式
        redisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
        //5.初始化参数设置
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    /**
     * 构建缓存bean
     * @return
     */
    @Bean
    public Cache<String,Object> caffeineCache(){
        Cache<String, Object> cache = Caffeine
                .newBuilder()
                //设置缓存数量上限
                .maximumSize(200)
//                .expireAfterAccess(1, TimeUnit.SECONDS)//访问1秒后删除
//                .expireAfterWrite(1,TimeUnit.SECONDS)//写入1秒后删除
                //初始的缓存空间大小
                .initialCapacity(100)
                .recordStats()//开启统计
                .build();
        return cache;
    }
}
