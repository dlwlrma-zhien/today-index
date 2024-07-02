package com.lcyy.stock.mq;


import com.github.benmanes.caffeine.cache.Cache;
import com.lcyy.stock.service.StockService;
import lombok.extern.slf4j.Slf4j;

import org.joda.time.DateTime;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author: dlwlrma
 * @data 2024年07月02日 19:17
 * @Description: TODO:定义股票相关mq的监听
 */
@Component
@Slf4j
public class StockMQMsgListener {

    @Autowired
    private Cache<String,Object> caffeineCache;

    @Autowired
    private StockService stockService;

    @RabbitListener(queues = "innerMarketQueue")
    public void refreshInnerMarketInfo(Date startdate){
        //统计当前时间点与发送时间点之间的差值，如果超过1分钟，则同步超时
        //获取时间毫秒差值
        long diffTime= DateTime.now().getMillis()-new DateTime(startdate).getMillis();
        //超过一分钟告警
        if (diffTime> 60000L) {
            log.error("采集国内大盘时间点：{},同步超时：{}ms",new DateTime(startdate).toString("yyyy-MM-dd HH:mm:ss"),diffTime);
        }
        //刷新缓存
        //剔除旧的数据
        caffeineCache.invalidate("innerMarketKey");
        //调用服务方法，刷新缓存
        stockService.getInnerMarketInfo();
    }
}
