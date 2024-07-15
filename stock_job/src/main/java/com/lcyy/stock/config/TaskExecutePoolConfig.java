package com.lcyy.stock.config;

import com.lcyy.stock.pojo.vo.TaskThreadPoolInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Map;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

@Configuration
@Slf4j
public class TaskExecutePoolConfig {
    @Autowired
    private TaskThreadPoolInfo info;

    /**
     * 定义任务执行器
     *
     * @return
     */
    @Bean(name = "threadPoolTaskExecutor", destroyMethod = "shutdown")
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        //构建线程池对象
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        //核心线程数：核心线程数（获取硬件）：线程池创建时候初始化的线程数
        taskExecutor.setCorePoolSize(info.getCorePoolSize());
        //最大线程数：只有在缓冲队列满了之后才会申请超过核心线程数的线程
        taskExecutor.setMaxPoolSize(info.getMaxPoolSize());
        //缓冲队列：用来缓冲执行任务的队列
        taskExecutor.setQueueCapacity(info.getQueueCapacity());
        //允许线程的空闲时间：当超过了核心线程出之外的线程在空闲时间到达之后会被销毁
        taskExecutor.setKeepAliveSeconds(info.getKeepAliveSeconds());
        //线程名称前缀
        taskExecutor.setThreadNamePrefix("StockThread-");
        //设置拒绝策略
        taskExecutor.setRejectedExecutionHandler(rejectedExecutionHandler());
        //参数初始化
        taskExecutor.initialize();
        return taskExecutor;
    }
    /**
     * 自定义线程拒绝策略
     * @return
     */
    @Bean
    public RejectedExecutionHandler rejectedExecutionHandler() {
        RejectedExecutionHandler errorHandler = new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable runnable, ThreadPoolExecutor executor) {
                if (runnable instanceof StockTaskRunable) {
                    StockTaskRunable r2= ((StockTaskRunable) runnable);
                    Map<String, Object> infos = r2.getInfos();
                    log.info("出现的异常的任务信息：{}",infos);
                }
            }
        };
        return errorHandler;
    }
}
