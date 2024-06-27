package com.lcyy.stock.service.impl;


import com.alibaba.excel.EasyExcel;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URLEncoder;
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
@Slf4j
public class StockServiceImpl implements StockService {

    @ApiModelProperty("注入StockInfoConfig")
    @Autowired
    private StockInfoConfig stockInfoConfig;

    @ApiModelProperty("注入StockMarketIndexInfoMapper")
    @Autowired
    private StockMarketIndexInfoMapper stockMarketIndexInfoMapper;

    @ApiModelProperty("注入StockBlockRtInfoMapper")
    @Autowired
    private StockBlockRtInfoMapper stockBlockRtInfoMapper;

    @ApiModelProperty("注入StockOuterMarketIndexInfoMapper")
    @Autowired
    private StockOuterMarketIndexInfoMapper stockOuterMarketIndexInfoMapper;

    @ApiModelProperty("注入StockRtInfoMapper")
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
        Date startDate = DateTimeUtil.getOpenDate(curDateTime).toDate();

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

    @Override
    public void exportStockUpDownInfo(Integer page, Integer pageSize, HttpServletResponse response) throws IOException {
        //1.获取分页内容
        R<PageResult<StockUpDownDomain>> info = this.getStockInfoByPage(page, pageSize);
        List<StockUpDownDomain> rows = info.getData().getRows();
        //2.将数据导出到excel中
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        try {
            String fileName = URLEncoder.encode("股票信息采集表", "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
            EasyExcel.write(response.getOutputStream(), StockUpDownDomain.class).sheet("股票涨幅信息").doWrite(rows);
        } catch (IOException e) {
            //打印日志信息
            log.error("当前页码：{}，每页大小：{}，当前时间：{}，异常信息：{}",page,pageSize,DateTime.now().toString("yyyy-MM-dd HH:mm:ss"),e.getMessage());
            //响应给前端
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            R<Object> error = R.error(ResponseCode.DATA_ERROR);
            String string = new ObjectMapper().writeValueAsString(error);
            response.getWriter().write(string);
        }
    }

    @Override
    public R<Map<String, List>> getCompareStockTradeAmt() {
        //1.获取最新股票交易日日期范围
        DateTime tEndTime = DateTimeUtil.getLastDate4Stock(DateTime.now());
        tEndTime = DateTime.parse("2022-01-03 14:40:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"));
        //装换为jdk的date,tEndTimeDate 截止日期
        Date tEndTimeDate = tEndTime.toDate();
        //开盘时间
        Date startDateTime = DateTimeUtil.getOpenDate(tEndTime).toDate();

        //2..获取t-1日的时间范围
        DateTime pretEndTime = DateTimeUtil.getPreviousTradingDay(DateTime.now());
        pretEndTime = DateTime.parse("2022-01-02 14:40:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"));
        Date pretEndTimeDate = pretEndTime.toDate();
        //开盘时间
        Date preStartDateTime = DateTimeUtil.getOpenDate(pretEndTime).toDate();

        //3.调用mapper查询,查询t日
        List<Map> tDate =  stockMarketIndexInfoMapper.getSumAmtInfo(startDateTime,tEndTimeDate,stockInfoConfig.getInner());
        List<Map> preDate =  stockMarketIndexInfoMapper.getSumAmtInfo(preStartDateTime,pretEndTimeDate,stockInfoConfig.getInner());

        //4.组装数据
        HashMap<String, List> info = new HashMap<>();
        info.put("amtList",tDate);
        info.put("yesAmtList",preDate);

        //5.返回给前端响应
        return R.ok(info);
    }

    @Override
    public R<Map> getIncreaseRangeInfo() {
        //1.获取最新股票交时间
        DateTime dateTime = DateTimeUtil.getLastDate4Stock(DateTime.now());
        dateTime = DateTime.parse("2022-01-06 09:55:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"));
        Date curDateTime = dateTime.toDate();

        //2.调用mapper方法查询
       List<Map> info =  stockRtInfoMapper.getIncreaseRangeInfo(curDateTime);

       //3.组装数据
        HashMap<String, Object> map = new HashMap<>();
        String curDateStr = new DateTime(curDateTime).toString(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"));
        map.put("time",curDateStr);
        map.put("infos",info);

        //4.响应给前端
        return R.ok(map);

    }
}
