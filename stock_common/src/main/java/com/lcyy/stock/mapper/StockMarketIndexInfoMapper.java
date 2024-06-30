package com.lcyy.stock.mapper;

import com.lcyy.stock.pojo.domain.InnerMarketDomain;
import com.lcyy.stock.pojo.entity.StockMarketIndexInfo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
* @author 22818
* @description 针对表【stock_market_index_info(国内大盘数据详情表)】的数据库操作Mapper
* @createDate 2024-06-18 21:48:00
* @Entity com.lcyy.stock.pojo.entity.StockMarketIndexInfo
*/
public interface StockMarketIndexInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(StockMarketIndexInfo record);

    int insertSelective(StockMarketIndexInfo record);

    StockMarketIndexInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(StockMarketIndexInfo record);

    int updateByPrimaryKey(StockMarketIndexInfo record);

    /**
     * TODO: 根据最新时间查询指定大盘编码对应的数据
     * @param curDate：指定时间点
     * @param mCodes ：大盘编码集合
     * @return
     */
    List<InnerMarketDomain> getMarketInfo(@Param("curDate") Date curDate,@Param("marketCodes") List<String> mCodes);

    /**
     * 统计知指定范围，指定大盘每分钟的成交量
     * @author dlwlrma
     * @date 2024/6/26 17:09
     * @param openDate 开始时间
     * @param EndDate 起止时间
     * @param marketCodes 大盘编码
     * @return java.util.List<java.util.Map>
     */
    List<Map> getSumAmtInfo(@Param("openDate") Date openDate, @Param("EndDate")Date EndDate,@Param("marketCodes") List<String> marketCodes);

    /**
     * 批量插入数据（A股大盘集合）
     * @author dlwlrma
     * @date 2024/6/30 15:55
     * @param entities
     * @return int
     */
    int insertBatch(@Param("infos") List<StockMarketIndexInfo> entities);
}
