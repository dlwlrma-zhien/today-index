package com.lcyy.stock.mapper;

import com.lcyy.stock.pojo.domain.stockBusinessDomain;
import com.lcyy.stock.pojo.entity.StockBusiness;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
* @author 22818
* @description 针对表【stock_business(主营业务表)】的数据库操作Mapper
* @createDate 2024-06-18 21:48:00
* @Entity com.lcyy.stock.pojo.entity.StockBusiness
*/
public interface StockBusinessMapper {

    int deleteByPrimaryKey(Long id);

    int insert(StockBusiness record);

    int insertSelective(StockBusiness record);

    StockBusiness selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(StockBusiness record);

    int updateByPrimaryKey(StockBusiness record);
    /**
     * 获取所有A股个股的编码集合
     * @author dlwlrma
     * @date 2024/6/30 16:56
     * @return java.util.List<java.lang.String>
     */
    List<String> getAllStockCodes();

    /**
     * 模糊查询联想推荐
     * @author dlwlrma
     * @date 2024/7/11 14:56
     * @param charArray
     * @return java.util.List<java.util.Map>
     */
    List<Map> getLinkCodes(@Param("charArray") char[] charArray);

    stockBusinessDomain getDescribe(@Param("code") String code);
}
