package com.lcyy.stock.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * A股大盘数据采集工程
 * @author: dlwlrma
 * @data 2024年06月30日 13:40
 */

public interface StockTimerTaskService {
    /**
     * 大盘数据采集
     * @author dlwlrma
     * @date 2024/6/30 13:43
     */
    void getInnerMarketInfo();

    /**
     * 个股大盘数据
     * @author dlwlrma
     * @date 2024/7/10 21:57
     */
    void getStockRtIndex();

    /**
     * 采集国外大盘
     * @author dlwlrma
     * @date 2024/7/15 20:23
     */
    void getOuterMarketInfo();

    /**
     * 采集板块
     * @author dlwlrma
     * @date 2024/7/15 20:23
     */
    void getStockBlockInfo();
}
