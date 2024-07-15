package com.lcyy.stock.job;

import com.lcyy.stock.service.StockTimerTaskService;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author: dlwlrma
 * @data 2024年07月10日 21:20
 * @Description: TODO:定义一个xxljob的执行器
 */
@Component
@Slf4j
public class Stockjob {

//    @Autowired
//    private StockTimerTaskService stockTimerTaskService;
//
//    /**
//     * 1、简单任务示例（Bean模式）
//     */
//    @XxlJob("MyJobHandler")
//    public void demoJobHandler() throws Exception {
//
//        //调度一次任务展示一次当前时间
//        System.out.println("当前时间"+ DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
//        // default success
//    }
//
//    /**
//     * 定时采集A股大盘数据
//     * 针对不同的股票数据，定义不同的采集任务，方便解耦
//     */
//    @XxlJob("getInnerMarketInfo")
//    public void getStockInfos(){
//        stockTimerTaskService.getInnerMarketInfo();
//    }
//
//    /**
//     * 定时采集个股大盘数据
//     */
//    @XxlJob("getStockRtIndex")
//    public void getStockRtIndex(){
//        stockTimerTaskService.getStockRtIndex();
//    }

    @Autowired
    private StockTimerTaskService stockTimerTaskService;
    /**
     * 1、简单任务示例（Bean模式）
     */
    @XxlJob("myJobHandler")
    public void demoJobHandler() throws Exception {
        log.info("当前时间：{}", DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
    }
    /**
     * 采集国内大盘
     */
    @XxlJob("InnerMarketInfo")
    public void getInnerMarketInfo() throws Exception {
        stockTimerTaskService.getInnerMarketInfo();
    }
    /**
     * 采集国外大盘
     */
    @XxlJob("OuterMarketInfo")
    public void getOuterMarketInfo() throws Exception {
        stockTimerTaskService.getOuterMarketInfo();
    }
    /**
     * 采集个股
     */
    @XxlJob("StockRtIndex")
    public void getStockRtIndex() throws Exception {
        stockTimerTaskService.getStockRtIndex();
    }
    /**
     * 采集板块
     */
    @XxlJob("StockBlockInfo")
    public void getStockBlockInfo() throws Exception {
        stockTimerTaskService.getStockBlockInfo();
    }
}
