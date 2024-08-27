package com.lcyy.stock.controller;

import com.lcyy.stock.pojo.domain.*;
import com.lcyy.stock.service.StockService;
import com.lcyy.stock.vo.resp.PageResult;
import com.lcyy.stock.vo.resp.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author: dlwlrma
 * @data 2024年06月21日 16:18
 * @Description: TODO: 定义股票选相关控制器接口
 */
@Api(value = "/api/quot", tags = {": TODO: 定义股票选相关控制器接口"})
@RestController
@RequestMapping("/api/quot")
public class StockController {

    @Autowired
    private StockService stockService;

    @ApiOperation(value = "获取国内最新大盘数据功能", notes = "获取国内最新大盘数据功能", httpMethod = "GET")
    @GetMapping("/index/all")
    public R<List<InnerMarketDomain>> getInnerMarketInfo(){
        return stockService.getInnerMarketInfo();
    }

    @ApiOperation(value = "国外数据板块展示", notes = "国外数据展示", httpMethod = "GET")
    @GetMapping("/external/index")
    public R<List<OutMarketDomain>> getOutMarketInfo(){
        return stockService.getOutMarketInfo();
    }



    @ApiOperation(value = "获取国内板块前十名", notes = "获取国内板块前十名", httpMethod = "GET")
    @GetMapping("/sector/all")
    public R<List<StockBlockDomain>> getStockBlock(){
        return stockService.getStockBlock();
    }

    /**
     * TODO:分页查询最新的交易数据
     *
     * @param page 当前页
     * @param pageSize 每页大小
     * @return com.lcyy.stock.vo.resp.R<com.lcyy.stock.vo.resp.PageResult < com.lcyy.stock.pojo.domain.StockBlockDomain>>
     * @author dlwlrma
     * @date 2024/6/22 16:33
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "int", name = "page", value = "当前页"),
            @ApiImplicitParam(paramType = "query", dataType = "int", name = "pageSize", value = "每页大小")
    })
    @ApiOperation(value = "分页查询最新的交易数据", notes = "分页查询最新的交易数据", httpMethod = "GET")
    @GetMapping("/stock/all")
    public R<PageResult<StockUpDownDomain>> getStockInfoByPage(@RequestParam(value = "page",required = false,defaultValue = "1")Integer page,
                                                               @RequestParam(value = "pageSize",required = false,defaultValue = "20")Integer pageSize){
        return stockService.getStockInfoByPage(page,pageSize);
    }

    /**
     * 统计最新股票交易日内涨跌停的数量
     * @author dlwlrma
     * @date 2024/6/22 18:17
     * @return com.lcyy.stock.vo.resp.R<java.util.Map<java.lang.String,java.util.List>>
     */
    @ApiOperation(value = "统计最新股票交易日内涨跌停的数量", notes = "统计最新股票交易日内涨跌停的数量", httpMethod = "GET")
    @GetMapping("/stock/updown/count")
    public R<Map<String,List>> getStockUpDownCount(){
        return stockService.getStockUpDownCount();
    }

    /**
     * TODO: 统计沪深两市个股最新交易数据，并按涨幅降序排序查询前4条数据
     * @author dlwlrma
     * @date 2024/6/22 19:53
     * @return com.lcyy.stock.vo.resp.R<java.util.List<com.lcyy.stock.pojo.domain.StockUpdownDomain>>
     */
    @ApiOperation(value = " 统计沪深两市个股最新交易数据，并按涨幅降序排序查询前4条数据", notes = " 统计沪深两市个股最新交易数据，并按涨幅降序排序查询前4条数据", httpMethod = "GET")
    @GetMapping("/stock/increase")
    public R<List<StockUpDownDomain>> getStockInnerBlock(){
        return stockService.getStockInnerBlock();
    }

    /**
     * 导出指定页码的股票信息
     * @author dlwlrma
     * @date 2024/6/23 20:26
     * @param page
     * @param pageSize
     * @param response
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "int", name = "page", value = ""),
            @ApiImplicitParam(paramType = "query", dataType = "int", name = "pageSize", value = "")
    })
    @ApiOperation(value = "导出指定页码的股票信息", notes = "导出指定页码的股票信息", httpMethod = "GET")
    @GetMapping("/stock/export")
    public void exportStockUpDownInfo(@RequestParam(value = "page",required = false,defaultValue = "1")Integer page,
                                      @RequestParam(value = "pageSize",required = false,defaultValue = "20")Integer pageSize,
                                      HttpServletResponse response) throws IOException {
         stockService.exportStockUpDownInfo(page,pageSize,response);
    }

    /**
     * 统计A股大盘T日和T-1日成交量对比功能（成交量为沪深两市成交量之和）
     * @author dlwlrma
     * @date 2024/6/26 16:48
     * @return com.lcyy.stock.vo.resp.R<java.util.Map<java.lang.String,java.util.List>>
     */
    @ApiOperation(value = "统计A股大盘T日和T-1日成交量对比功能（成交量为沪深两市成交量之和）", notes = "统计A股大盘T日和T-1日成交量对比功能（成交量为沪深两市成交量之和）", httpMethod = "GET")
    @GetMapping("/stock/tradeAmt")
    public R<Map<String,List>> getCompareStockTradeAmt(){
        return stockService.getCompareStockTradeAmt();
    }

    /**
     * 统计当前时间下，A股在各个涨跌区间股票的数量
     * @author dlwlrma
     * @date 2024/6/27 14:36
 * @return com.lcyy.stock.vo.resp.R<java.util.Map>
     */
    @ApiOperation(value = "统计当前时间下，A股在各个涨跌区间股票的数量", notes = "统计当前时间下，A股在各个涨跌区间股票的数量", httpMethod = "GET")
    @GetMapping("/stock/updown")
    public R<Map> getIncreaseRangeInfo(){
        return stockService.getIncreaseRangeInfo();
    }

    /**
     * 获取指定股票的k线图
     * @author dlwlrma
     * @date 2024/6/27 22:28
     * @param code
     * @return com.lcyy.stock.vo.resp.R<java.util.List<com.lcyy.stock.pojo.domain.Stock4MinuteDomain>>
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "code", value = "", required = true)
    })
    @ApiOperation(value = "获取指定股票的k线图", notes = "获取指定股票的k线图", httpMethod = "GET")
    @GetMapping("/stock/screen/time-sharing")
    public R<List<Stock4MinuteDomain>> getStockScreenTimeSharing(@RequestParam(value = "code",required = true) String code){
        return stockService.getStockScreenTimeSharing(code);
    }

    /**
     * 单个个股日K 数据查询 ，可以根据时间区间查询数日的K线数据
     * @author dlwlrma
     * @date 2024/6/27 22:46
     * @param code
     * @return com.lcyy.stock.vo.resp.R<java.util.List<com.lcyy.stock.pojo.domain.Stock4EvrDayDomain>>
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "code", value = "", required = true)
    })
    @ApiOperation(value = "单个个股日K 数据查询 ，可以根据时间区间查询数日的K线数据", notes = "单个个股日K 数据查询 ，可以根据时间区间查询数日的K线数据", httpMethod = "GET")
    @GetMapping("/stock/screen/dkline")
    public R<List<Stock4EvrDayDomain>> getScreenDkline(@RequestParam(value = "code",required = true) String code){
        return stockService.getScreenDkLine(code);
    }

    /**
     * 股票code联想推荐功能
     * @author dlwlrma
     * @date 2024/7/11 15:10
     * @return com.lcyy.stock.vo.resp.R<java.util.List<java.util.Map>>
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "searchStr", value = "")
    })
    @ApiOperation(value = "股票code联想推荐功能", notes = "股票code联想推荐功能", httpMethod = "GET")
    @GetMapping("/stock/search")
    public R<List<Map>> getCodesAndStockName(@RequestParam(value = "searchStr",required = false)String searchStr){
        return stockService.getCodesAndStockName(searchStr);
    }

    /**
     * 统计每周内的股票数据
     * @author dlwlrma
     * @date 2024/7/11 15:30
     * @param code
     * @return com.lcyy.stock.vo.resp.R<java.util.List<com.lcyy.stock.pojo.domain.Stock4WeeklineDomain>>
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "code", value = "", required = true)
    })
    @ApiOperation(value = "统计每周内的股票数据", notes = "统计每周内的股票数据", httpMethod = "GET")
    @GetMapping("/stock/screen/weekkline")
    public R<List<Stock4WeeklineDomain>> getScreenWkLine(@RequestParam("code") String code){
        return stockService.getScreenWkLine(code);
    }

    /**
     * 个股交易流水行情数据查询--查询最新交易流水，按照时间降序排序取前10
     * @author dlwlrma
     * @date 2024/7/11 16:16
     * @param code
     * @return com.lcyy.stock.vo.resp.R<java.util.List<com.lcyy.stock.pojo.domain.StockScreenSecondDomain>>
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "code", value = "", required = true)
    })
    @ApiOperation(value = "个股交易流水行情数据查询--查询最新交易流水，按照时间降序排序取前10", notes = "个股交易流水行情数据查询--查询最新交易流水，按照时间降序排序取前10", httpMethod = "GET")
    @GetMapping("/stock/screen/second")
    public R<List<StockScreenSecondDomain>> getStockSecond(@RequestParam("code") String code){
        return stockService.getStockSecond(code);
    }

    /**
     * 获取个股最新分时行情数据，主要包含：
     * 	开盘价、前收盘价、最新价、最高价、最低价、成交金额和成交量、交易时间信息;
     * @author dlwlrma
     * @date 2024/7/11 16:40
     * @param code
     * @return com.lcyy.stock.vo.resp.R<com.lcyy.stock.pojo.domain.StockScreenDetailDomain>
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "code", value = "", required = true)
    })
    @ApiOperation(value = "获取个股最新分时行情数据", notes = "获取个股最新分时行情数", httpMethod = "GET")
    @GetMapping("/stock/screen/second/detail")
    public R<StockScreenDetailDomain> getStockDetail(@RequestParam(value = "code",required = true) String code){
        return stockService.getStockDetail(code);
    }

    /**
     * 个股主营业务
     * @author dlwlrma
     * @date 2024/7/11 17:04
     * @param code
     * @return com.lcyy.stock.vo.resp.R<com.lcyy.stock.pojo.domain.stockBusinessDomain>
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "code", value = "", required = true)
    })
    @ApiOperation(value = "个股主营业务", notes = "个股主营业务", httpMethod = "GET")
    @GetMapping("/stock/describe")
    public R<stockBusinessDomain> getDescribe(@RequestParam(value = "code",required = true) String code){
        return stockService.getDescribe(code);
    }
    
}
