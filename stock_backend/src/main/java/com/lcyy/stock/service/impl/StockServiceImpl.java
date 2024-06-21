package com.lcyy.stock.service.impl;

import com.lcyy.stock.mapper.StockBlockRtInfoMapper;
import com.lcyy.stock.mapper.StockMarketIndexInfoMapper;
import com.lcyy.stock.pojo.domain.InnerMarketDomain;
import com.lcyy.stock.pojo.domain.StockBlockDomain;
import com.lcyy.stock.pojo.entity.StockMarketIndexInfo;
import com.lcyy.stock.pojo.entity.StockOuterMarketIndexInfo;
import com.lcyy.stock.pojo.vo.StockInfoConfig;
import com.lcyy.stock.service.StockService;
import com.lcyy.stock.utils.DateTimeUtil;
import com.lcyy.stock.vo.resp.R;
import com.lcyy.stock.vo.resp.ResponseCode;
import org.checkerframework.checker.units.qual.A;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * @author: dlwlrma
 * @data 2024年06月21日 16:25
 * @Description: TODO: 股票服务实现
 */
@Service
public class StockServiceImpl implements StockService {

    @Autowired
    private StockInfoConfig stockInfoConfig;

    @Autowired
    private StockMarketIndexInfoMapper stockMarketIndexInfoMapper;

    @Autowired
    private StockBlockRtInfoMapper stockBlockRtInfoMapper;


    @Override
    public R<List<InnerMarketDomain>> getInnerMarketInfo() {
        //1.获取最新股票交易时间点（精确到分钟，秒和毫秒置为0）
        Date curDate = DateTimeUtil.getLastDate4Stock(DateTime.now()).toDate();
        curDate = DateTime.parse("2022-07-07 14:51:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        //2.获取大盘编码集合
        List<String> mCodes = stockInfoConfig.getInner();

        //3.调用mapper集合
        List<InnerMarketDomain> data = stockMarketIndexInfoMapper.getMarketInfo(curDate,mCodes);

        //4.封装并相应
        return R.ok(data);
    }

    @Override
    public R<List<StockBlockDomain>> getStockBlock() {
        //1.获取最新股票交易时间点
        Date lastDate = DateTimeUtil.getLastDate4Stock(DateTime.now()).toDate();
        lastDate = DateTime.parse("2021-12-21 14:30:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();

        //2.调用mapper接口
        List<StockBlockDomain> info = stockBlockRtInfoMapper.getStockRtInfo(lastDate);

        //3.进行小小判断看是否为空值
        if(CollectionUtils.isEmpty(info)){
            return R.error(ResponseCode.DATA_ERROR.getMessage());
        }
        return R.ok(info);

    }
}
