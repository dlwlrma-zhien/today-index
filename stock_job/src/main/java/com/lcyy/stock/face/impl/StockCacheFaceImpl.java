package com.lcyy.stock.face.impl;

import com.lcyy.stock.face.StockCacheFace;
import com.lcyy.stock.mapper.StockBusinessMapper;
import com.lcyy.stock.pojo.entity.StockBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: dlwlrma
 * @data 2024年07月25日 15:54
 * @Description: TODO:定义股票缓存层的实现
 */
@Component("StockCacheFace")
public class StockCacheFaceImpl implements StockCacheFace {

    @Autowired
    private StockBusinessMapper stockBusinessMapper;

    @Cacheable(cacheNames = "stock",key = "'getAllStockCodeWithPredix'")
    @Override
    public List<String> getAllStockCodeWithPredix() {
        //1.获取所有A股股票的编码
        List<String> allCodes = stockBusinessMapper.getAllStockCodes();
        //2.添加股票前缀 sh sz
        List<String> prefixCodes = allCodes.stream().map(code -> {
            code = code.startsWith("6") ? "sh" + code : "sz" + code;
            return code;
        }).collect(Collectors.toList());
        return prefixCodes;
    }

}
