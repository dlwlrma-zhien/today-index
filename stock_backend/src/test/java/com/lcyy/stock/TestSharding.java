package com.lcyy.stock;

import com.lcyy.stock.mapper.StockBlockRtInfoMapper;
import com.lcyy.stock.mapper.StockBusinessMapper;
import com.lcyy.stock.mapper.StockRtInfoMapper;
import com.lcyy.stock.mapper.SysUserMapper;
import com.lcyy.stock.pojo.domain.Stock4EvrDayDomain;
import com.lcyy.stock.pojo.domain.Stock4WeeklineDomain;
import com.lcyy.stock.pojo.domain.StockBlockDomain;
import com.lcyy.stock.pojo.entity.StockBusiness;
import com.lcyy.stock.pojo.entity.SysUser;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

@SpringBootTest
public class TestSharding {
    @Autowired
    private SysUserMapper sysUserMapper;
    /**
     * 测试默认数据源
     */
    @Test
    public void testDefaultDs(){
        SysUser user = sysUserMapper.selectByPrimaryKey(1237365636208922624l);
        System.out.println(user);
    }

    @Autowired
    private StockBusinessMapper stockBusinessMapper;

    /**
     * @Description 测试广播表
     */
    @Test
    public void testBroadCast(){
//        StockBusiness pojo = StockBusiness.builder().stockCode("90000")
//                .stockName("900000")
//                .blockLabel("900000")
//                .blockName("900000")
//                .business("900000")
//                .updateTime(new Date())
//                .build();
//        stockBusinessMapper.insert(pojo);

//        stockBusinessMapper.deleteByPrimaryKey("90000");
        List<String> allStockCodes = stockBusinessMapper.getAllStockCodes();
        System.out.println(allStockCodes);
    }

    @Autowired
    private StockBlockRtInfoMapper stockBlockRtInfoMapper;
    @Test
    public void testCommonSharding(){
        Date curDate= DateTime.parse("2022-01-03 09:30:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        List<StockBlockDomain> info = stockBlockRtInfoMapper.getStockRtInfo(curDate);
        System.out.println(info);
    }

}