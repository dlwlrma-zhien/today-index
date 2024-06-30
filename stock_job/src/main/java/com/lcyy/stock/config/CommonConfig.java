package com.lcyy.stock.config;

import com.lcyy.stock.pojo.vo.StockInfoConfig;
import com.lcyy.stock.utils.IdWorker;
import com.lcyy.stock.utils.ParserStockInfoUtil;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: dlwlrma
 * @data 2024年06月19日 18:35
 * @Description: TODO: 定义密码匹配器
 */
@Configuration
@EnableConfigurationProperties(StockInfoConfig.class)//像开关一样需要时候再开启
public class CommonConfig {
    /**
     * TODO： 基于雪花算法的工具类 保证 session 的唯一性
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

    /**
     * 定义解析股票大盘，外盘，个股 板块的相关信息的bean
     * @author dlwlrma
     * @date 2024/6/30 17:27
     * @param idWorker
     * @return com.lcyy.stock.utils.ParserStockInfoUtil
     */
    @Bean
    public ParserStockInfoUtil parserStockInfoUtil(IdWorker idWorker){
        return new ParserStockInfoUtil(idWorker);
    }
}
