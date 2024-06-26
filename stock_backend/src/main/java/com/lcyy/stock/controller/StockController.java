package com.lcyy.stock.controller;

import com.lcyy.stock.pojo.domain.InnerMarketDomain;
import com.lcyy.stock.pojo.domain.OutMarketDomain;
import com.lcyy.stock.pojo.domain.StockBlockDomain;
import com.lcyy.stock.pojo.domain.StockUpDownDomain;
import com.lcyy.stock.service.StockService;
import com.lcyy.stock.vo.resp.PageResult;
import com.lcyy.stock.vo.resp.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    @GetMapping("stock/increase")
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

}
