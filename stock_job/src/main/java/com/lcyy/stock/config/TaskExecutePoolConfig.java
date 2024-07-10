package com.lcyy.stock.config;

import com.lcyy.stock.pojo.vo.TaskThreadPoolInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author: dlwlrma
 * @data 2024年07月10日 22:46
 * @Description: TODO:定义线程池的配置类
 */
@Configuration
public class TaskExecutePoolConfig {

    @Autowired
    private TaskThreadPoolInfo info;

    /**
     * 构建线程池对象
     * @author dlwlrma
     * @date 2024/7/10 22:57
     * @return org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
     */
    //"shutdown" 优雅关闭
    @Bean(value = "threadPoolTaskExecutor",initMethod = "shutdown")
    public ThreadPoolTaskExecutor threadPoolTaskExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //设置核心线程数
        executor.setCorePoolSize(info.getCorePoolSize());
        //设置最大线程数
        executor.setMaxPoolSize(info.getMaxPoolSize());
        //设置空闲线程最大存活时间
        executor.setKeepAliveSeconds(info.getKeepAliveSeconds());
        //设置任务队列最大长度
        executor.setQueueCapacity(info.getQueueCapacity());
        //设置拒绝策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());

        //设置参数初始化
        executor.initialize();
        //返回线程池对象
        return executor;
    }}
