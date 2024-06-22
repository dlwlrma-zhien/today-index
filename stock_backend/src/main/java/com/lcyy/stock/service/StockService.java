package com.lcyy.stock.service;

import com.lcyy.stock.pojo.domain.InnerMarketDomain;
import com.lcyy.stock.pojo.domain.OutMarketDomain;
import com.lcyy.stock.pojo.domain.StockBlockDomain;
import com.lcyy.stock.pojo.domain.StockUpDownDomain;
import com.lcyy.stock.vo.resp.PageResult;
import com.lcyy.stock.vo.resp.R;
import io.swagger.annotations.ApiModel;

import java.util.List;
import java.util.Map;

/**
 * @author: dlwlrma
 * @data 2024年06月21日 16:23
 * TODO: 股票服务接口
 */
@ApiModel(description = "TODO: 股票服务接口")
public interface StockService {
    /**
     * TODO: 获取国内最新大盘数据
     * @author dlwlrma
     * @date 2024/6/21 16:24
     * @return com.lcyy.stock.vo.resp.R<java.util.List<com.lcyy.stock.pojo.domain.InnerMarketDomain>>
     */
    R<List<InnerMarketDomain>> getInnerMarketInfo();

    /**
     * TODO：获取沪深两市板块最新数据，以交易总金额降序查询，取前10条数据
     * @author dlwlrma
     * @date 2024/6/21 18:51
     * @return com.lcyy.stock.vo.resp.R<java.util.List<com.lcyy.stock.pojo.domain.StockBlockDomain>>
     */
    R<List<StockBlockDomain>> getStockBlock();

    /**
     * TODO: 获取国外最新数据展示
     * @author dlwlrma
     * @date 2024/6/22 13:52
     * @return com.lcyy.stock.vo.resp.R<java.util.List<com.lcyy.stock.pojo.domain.OutMarketDomain>>
     */
    R<List<OutMarketDomain>> getOutMarketInfo();

    R<PageResult<StockUpDownDomain>> getStockInfoByPage(Integer page, Integer pageSize);

    R<Map<String, List>> getStockUpDownCount();

    /**
     * 涨幅榜展示
     * @author dlwlrma
     * @date 2024/6/22 19:53
     * @return com.lcyy.stock.vo.resp.R<java.util.List<com.lcyy.stock.pojo.domain.StockUpdownDomain>>
     */
    R<List<StockUpDownDomain>> getStockInnerBlock();
}
