package com.lcyy.stock.face;

import com.lcyy.stock.pojo.entity.StockBusiness;

import java.util.List;

/**
 * @author: dlwlrma
 * @data 2024年07月25日 15:53
 */
public interface StockCacheFace {
    /**
     * 获取所有股票编码，并添加上证或者深证的股票前缀编号：sh sz
     * @return
     */
    List<String> getAllStockCodeWithPredix();
}
