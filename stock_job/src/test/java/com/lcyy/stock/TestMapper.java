package com.lcyy.stock;

import com.google.common.collect.Lists;
import com.lcyy.stock.mapper.StockBusinessMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: dlwlrma
 * @data 2024年06月30日 16:58
 * @Description: TODO: 测试所有个股编码集合
 */
@SpringBootTest
public class TestMapper {

    @Autowired
    private StockBusinessMapper stockBusinessMapper;

    @Test
    public void test01(){
        List<String> allStockCodes = stockBusinessMapper.getAllStockCodes();
        //收集到的编码没有前缀编码，因此转换为有前缀编码的
        allStockCodes = allStockCodes.stream().map(code->code.startsWith("6")?"sh"+code:"sz"+code).collect(Collectors.toList());
        System.out.println("allStockCodes = " + allStockCodes);
        //将所有的大盘编码集合拆分为小的编码集合（分组45->15,15,10）
        //使用的谷歌 guava 工具包，可以将一个很大的集合拆分成若干的小的集合
        Lists.partition(allStockCodes,15).forEach(codes->{
            System.out.println("codes = " + codes);
        });
    }
}
