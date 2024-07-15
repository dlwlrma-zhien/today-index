package com.lcyy.stock.sharding;


import com.google.common.collect.Range;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author: dlwlrma
 * @data 2024年07月15日 15:17
 * @Description: TODO:公共的个股流水表的分表算法类，包含个股
 */
public class CommonAlg4Tb implements PreciseShardingAlgorithm<Date>, RangeShardingAlgorithm<Date> {
    /**
     * 精准查询时走该方法，cur_time的条件必须是 = 或者 in
     * @author dlwlrma
     * @date 2024/7/15 15:19
     * @param tbNames ds-2021,ds-2022,ds-2023
     * @param ShardingValue
     * @return java.lang.String
     * 分库策略：按照月份拆分
     */
    @Override
    public String doSharding(Collection<String> tbNames, PreciseShardingValue<Date> ShardingValue) {
        //1.思路：根据传入的日期值，获取年份字符串
        //获取分片字段的名称colume
//        String columnName = shardingValue.getColumnName();
        //获取逻辑表名称
//        String logicTableName = shardingValue.getLogicTableName();
        //获取分片值
        Date value = ShardingValue.getValue();
        //获取年月组成的字符串
        String yearMonth = new DateTime(value).toString(DateTimeFormat.forPattern("yyyyMM"));
        //过滤表的名称集合，获取名称后缀与yearMonth一致的表名称
        Optional<String> optional = tbNames.stream().filter(tbName -> tbName.endsWith(yearMonth)).findFirst();
        String tbName=null;
        if (optional.isPresent()) {
            tbName=optional.get();
        }
        return tbName;
    }

    /**
     * 范围查询
     * @author dlwlrma
     * @date 2024/7/15 15:32
     * @param tbNames ds-2021,ds-2022,ds-2023
     * @param ShardingValue
     * @return java.util.Collection<java.lang.String>
     */
    @Override
    public Collection<String> doSharding(Collection<String> tbNames, RangeShardingValue<Date> ShardingValue) {
        //获取分片字段名称
//        String columnName = shardingValue.getColumnName();
//        //获取逻辑表名称
//        String logicTableName = shardingValue.getLogicTableName();
        //1.获取范围封装对象
        Range<Date> valueRange = ShardingValue.getValueRange();
        //2.1 判断是否有下限值
        if (valueRange.hasLowerBound()) {
            //获取下限日期
            Date lowerDate = valueRange.lowerEndpoint();
            //获取年份  dsNames--> ds_2021 ds_2022 ds_2023
            //获取年月组成的字符串
            String yearMonth = new DateTime(lowerDate).toString(DateTimeFormat.forPattern("yyyyMM"));
            Integer yearM = Integer.valueOf(yearMonth);
            tbNames= tbNames.stream().filter(tbName->Integer.parseInt(tbName.substring(tbName.lastIndexOf("_")+1))>=yearM)
                    .collect(Collectors.toList());
        }
        //2.2 判断是否有上限值
        if (valueRange.hasUpperBound()) {
            Date upperDate = valueRange.upperEndpoint();
            String yearMonth = new DateTime(upperDate).toString(DateTimeFormat.forPattern("yyyyMM"));
            Integer yearM = Integer.valueOf(yearMonth);
            tbNames= tbNames.stream().filter(tbName->Integer.parseInt(tbName.substring(tbName.lastIndexOf("_")+1))<=yearM)
                    .collect(Collectors.toList());
        }
        return tbNames;
    }
}
