package com.lcyy.stock.mapper;

import com.lcyy.stock.pojo.domain.OutMarketDomain;
import com.lcyy.stock.pojo.entity.StockOuterMarketIndexInfo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
* @author 22818
* @description 针对表【stock_outer_market_index_info(外盘详情信息表)】的数据库操作Mapper
* @createDate 2024-06-18 21:48:00
* @Entity com.lcyy.stock.pojo.entity.StockOuterMarketIndexInfo
*/
public interface StockOuterMarketIndexInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(StockOuterMarketIndexInfo record);

    int insertSelective(StockOuterMarketIndexInfo record);

    StockOuterMarketIndexInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(StockOuterMarketIndexInfo record);

    int updateByPrimaryKey(StockOuterMarketIndexInfo record);

    /**
     * 获取国外最新股票数据展示
     * @author dlwlrma
     * @date 2024/6/22 14:03
     * @param lastDate
     * @param outMcodes
     * @return java.util.List<com.lcyy.stock.pojo.domain.OutMarketDomain>
     */
    List<OutMarketDomain> getOutMarketInfo(@Param("lastDate") Date lastDate,@Param("marketCodes") List<String> outMcodes);
}
