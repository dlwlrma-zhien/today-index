package com.lcyy.stock.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcyy.stock.mapper.StockBlockRtInfoMapper;
import com.lcyy.stock.mapper.StockMarketIndexInfoMapper;
import com.lcyy.stock.mapper.StockOuterMarketIndexInfoMapper;
import com.lcyy.stock.mapper.StockRtInfoMapper;
import com.lcyy.stock.pojo.domain.InnerMarketDomain;
import com.lcyy.stock.pojo.domain.OutMarketDomain;
import com.lcyy.stock.pojo.domain.StockBlockDomain;
import com.lcyy.stock.pojo.domain.StockUpDownDomain;

import com.lcyy.stock.pojo.vo.StockInfoConfig;
import com.lcyy.stock.service.StockService;
import com.lcyy.stock.utils.DateTimeUtil;
import com.lcyy.stock.vo.resp.PageResult;
import com.lcyy.stock.vo.resp.R;
import com.lcyy.stock.vo.resp.ResponseCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: dlwlrma
 * @data 2024年06月21日 16:25
 * @Description: TODO: 股票服务实现
 */
@ApiModel(description = ": TODO: 股票服务实现")
@Service
public class StockServiceImpl implements StockService {

    @ApiModelProperty(hidden = true)
    @Autowired
    private StockInfoConfig stockInfoConfig;

    @ApiModelProperty(hidden = true)
    @Autowired
    private StockMarketIndexInfoMapper stockMarketIndexInfoMapper;

    @ApiModelProperty(hidden = true)
    @Autowired
    private StockBlockRtInfoMapper stockBlockRtInfoMapper;

    @ApiModelProperty(hidden = true)
    @Autowired
    private StockOuterMarketIndexInfoMapper stockOuterMarketIndexInfoMapper;

    @ApiModelProperty(hidden = true)
    @Autowired
    private StockRtInfoMapper stockRtInfoMapper;


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

    @Override
    public R<List<OutMarketDomain>> getOutMarketInfo() {
        //1.获取国外最新股票交易时间点
        Date lastDate1 = DateTimeUtil.getLastDate4Stock(DateTime.now()).toDate();
        lastDate1 = DateTime.parse("2022-05-18 15:58:00",DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();

        //2.获取国外外盘编码集合
        List<String> outMcodes = stockInfoConfig.getOuter();

        //3.调用mapper集合
        List<OutMarketDomain> data= stockOuterMarketIndexInfoMapper.getOutMarketInfo(lastDate1,outMcodes);

        //4.封装并响应
        return R.ok(data);
    }

    @Override
    public R<PageResult<StockUpDownDomain>> getStockInfoByPage(Integer page, Integer pageSize) {
        //1.获取最新的股票交易时间
        Date lastDate2 = DateTimeUtil.getLastDate4Stock(DateTime.now()).toDate();
        lastDate2 = DateTime.parse("2021-12-30 09:42:00",DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();

        //2.设置分页参数
        PageHelper.startPage(page, pageSize);

        //3.设置mapper查询
        List<StockUpDownDomain> pageData = stockRtInfoMapper.getStockInfoByPage(lastDate2);

        //4.组装分页数据
        PageInfo<StockUpDownDomain> pageInfo = new PageInfo<>(pageData);
        PageResult<StockUpDownDomain> pageResult = new PageResult<>(pageInfo);

        //5.返回数据给前端
        return R.ok(pageResult);

    }

    @Override
    public R<Map<String, List>> getStockUpDownCount() {
        //1.获取当前股票最新交易时间点
        DateTime curDateTime = DateTimeUtil.getLastDate4Stock(DateTime.now());
        Date endDate = curDateTime.toDate();
        curDateTime = DateTime.parse("2022-01-06 14:25:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"));

        //2.获取最新交易点对应的开盘时间点
        DateTime startDate = DateTimeUtil.getOpenDate(curDateTime);

        //3.统计涨停数据
        List<Map> upList = stockRtInfoMapper.getStockUpDownCount(startDate,endDate,1);

        //4.统计跌停数据
        List<Map> downList = stockRtInfoMapper.getStockUpDownCount(startDate,endDate,0);

        //5.封装数据
        Map<String, List> info = new HashMap<>();
        info.put("upList",upList);
        info.put("downList",downList);

        //6.响应给前端
        return R.ok(info);
    }

    @Override
    public R<List<StockUpDownDomain>> getStockInnerBlock() {
        //1.获取当前股票最新交易时间点
        Date lastDate = DateTimeUtil.getLastDate4Stock(DateTime.now()).toDate();
        lastDate = DateTime.parse("2021-12-30 09:42:00",DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();

        //2.调用mapper方法
        List<StockUpDownDomain> data = stockRtInfoMapper.getStockInnerBlock(lastDate);

        //3.封装并响应
        if(CollectionUtils.isEmpty(data)){
            return R.error(ResponseCode.DATA_ERROR);
        }
        return R.ok(data);
    }
}
