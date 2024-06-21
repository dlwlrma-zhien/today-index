package com.lcyy.stock.controller;

import com.lcyy.stock.pojo.domain.InnerMarketDomain;
import com.lcyy.stock.pojo.domain.StockBlockDomain;
import com.lcyy.stock.service.StockService;
import com.lcyy.stock.vo.resp.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: dlwlrma
 * @data 2024年06月21日 16:18
 * @Description: TODO: 定义股票选相关控制器接口
 */
@RestController
@RequestMapping("/api/quot")
@Api(value = "/api/quot",tags = {"定义股票相关控制器接口"})
public class StockController {

    @Autowired
    private StockService stockService;

    @ApiOperation(value = "获取国内最新大盘数据功能",notes = "获取国内最新大盘数据功能",httpMethod = "GET")
    @GetMapping("/index/all")
    public R<List<InnerMarketDomain>> getInnerMarketInfo(){
        return stockService.getInnerMarketInfo();
    }

    @ApiOperation(value = "获取国内板块前十名",notes = "获取国内板块前十名",httpMethod = "GET")
    @GetMapping("/sector/all")
    public R<List<StockBlockDomain>> getStockBlock(){
        return stockService.getStockBlock();
    }
}
