package com.lcyy.stock.mapper;

import com.lcyy.stock.pojo.domain.StockBlockDomain;
import com.lcyy.stock.pojo.entity.StockBlockRtInfo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
* @author 22818
* @description 针对表【stock_block_rt_info(股票板块详情信息表)】的数据库操作Mapper
* @createDate 2024-06-18 21:48:00
* @Entity com.lcyy.stock.pojo.entity.StockBlockRtInfo
*/
public interface StockBlockRtInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(StockBlockRtInfo record);

    int insertSelective(StockBlockRtInfo record);

    StockBlockRtInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(StockBlockRtInfo record);

    int updateByPrimaryKey(StockBlockRtInfo record);

    /**
     * TODO: 沪深两市板块分时行情数据查询，以交易时间和交易总金额降序查询，取前10条数据
     * @author dlwlrma
     * @date 2024/6/21 19:03
     * @param lastDate
     * @return java.util.List<com.lcyy.stock.pojo.domain.StockBlockDomain>
     */
    List<StockBlockDomain> getStockRtInfo(@Param("TimeData") Date lastDate);

    int insertBatch(@Param("list") List<StockBlockRtInfo> list);
}
