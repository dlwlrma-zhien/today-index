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

    void getStockRtIndex();
}
