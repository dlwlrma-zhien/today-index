package com.lcyy.stock.mapper;

import com.lcyy.stock.pojo.domain.*;
import com.lcyy.stock.pojo.entity.StockRtInfo;
import io.swagger.annotations.ApiModel;
import org.apache.ibatis.annotations.Param;
import org.joda.time.DateTime;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
* @author 22818
* @description 针对表【stock_rt_info(个股详情信息表)】的数据库操作Mapper
* @createDate 2024-06-18 21:48:00
* @Entity com.lcyy.stock.pojo.entity.StockRtInfo
*/
@ApiModel(description = "针对表【stock_rt_info(个股详情信息表)】的数据库操作Mapper")
public interface StockRtInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(StockRtInfo record);

    int insertSelective(StockRtInfo record);

    StockRtInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(StockRtInfo record);

    int updateByPrimaryKey(StockRtInfo record);
/**
 * TODO: 查询指定时间点下的最新数据
 * @author dlwlrma
 * @date 2024/6/22 16:48
 * @param lastDate
 * @return java.util.List<com.lcyy.stock.pojo.domain.StockUpdownDomain>
 */
    List<StockUpDownDomain> getStockInfoByPage(@Param("lastDate") Date lastDate);

    /**
     * 统计指定日期范围内股票涨停或跌停的数据流水
     * @author dlwlrma
     * @date 2024/6/22 18:39
     * @param startDate 开始时间（开盘时间）
     * @param endDate 结束时间
     * @param flag 约定：0为跌停，1为涨停
     * @return java.util.List<java.util.Map>
     */
    List<Map> getStockUpDownCount(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("flag") int flag);


    /**
     * 统计沪深两市个股最新交易数据，并按涨幅降序排序查询前4条数据
     * @author dlwlrma
     * @date 2024/6/22 20:59
     * @param lastDate 
     * @return java.util.List<com.lcyy.stock.pojo.domain.StockUpDownDomain>
     */
    List<StockUpDownDomain> getStockInnerBlock(@Param("lastDate") Date lastDate);

    List<Map> getIncreaseRangeInfo(@Param("curDateTime") Date curDateTime);

    List<Stock4MinuteDomain> getStockScreenTimeSharing(@Param("startTime") Date startTime,@Param("endTime") Date endTime,@Param("code") String code);

    List<Stock4EvrDayDomain> getScreenDkLine(@Param("startTime") Date startTime,@Param("endTime") Date endTime,@Param("code") String code);

    /**
     * 批量插入个股数据
     * @author dlwlrma
     * @date 2024/6/30 18:26
     * @param list
     * @return int
     */
    int insertBatch(@Param("list") List<StockRtInfo> list);

    /**
     * 统计周k线
     * @author dlwlrma
     * @date 2024/7/11 15:40
     * @param startTime
     * @param endTime
     * @param code
     * @return java.util.List<com.lcyy.stock.pojo.domain.Stock4WeeklineDomain>
     */
    List<Stock4WeeklineDomain> getScreenWkLine(@Param("startTime") Date startTime, @Param("endTime") Date endTime, @Param("code") String code);

    List<StockScreenSecondDomain> getStockSecond(@Param("code") String code);

    StockScreenDetailDomain getStockDetail(@Param("startData") Date startData, @Param("code") String code);
}
