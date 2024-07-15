package com.lcyy.stock.sharding;


import com.google.common.collect.Range;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;
import org.joda.time.DateTime;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author: dlwlrma
 * @data 2024年07月15日 15:17
 * @Description: TODO:公共的分库算法类，包含个股，大盘，板块相关表
 */
public class CommonAlg4Db implements PreciseShardingAlgorithm<Date>, RangeShardingAlgorithm<Date> {
    /**
     * 精准查询时走该方法，cur_time的条件必须是 = 或者 in
     * @author dlwlrma
     * @date 2024/7/15 15:19
     * @param dsNames ds-2021,ds-2022,ds-2023
     * @param ShardingValue
     * @return java.lang.String
     * 分库策略：按照年份拆分
     */
    @Override
    public String doSharding(Collection<String> dsNames, PreciseShardingValue<Date> ShardingValue) {
        //获取逻辑表
        String logicTableName = ShardingValue.getLogicTableName();
        //获取分偏键
        String columnName = ShardingValue.getColumnName();
        //获取等值查询的条件值
        Date curTime = ShardingValue.getValue();
        //获取条件中对应的年份，然后从ds集合中过滤出被年份结尾的数据源即可
        String year = new DateTime(curTime).getYear()+"";
        //过滤
        Optional<String> result = dsNames.stream().filter(dsName -> dsName.endsWith(year)).findFirst();
        if (result.isPresent()) {
            return result.get();
        }
        return null;
    }

    /**
     * 范围查询
     * @author dlwlrma
     * @date 2024/7/15 15:32
     * @param dsNames ds-2021,ds-2022,ds-2023
     * @param ShardingValue
     * @return java.util.Collection<java.lang.String>
     */
    @Override
    public Collection<String> doSharding(Collection<String> dsNames, RangeShardingValue<Date> ShardingValue) {
        //获取逻辑表
        String logicTableName = ShardingValue.getLogicTableName();
        //获取分片键
        String columnName = ShardingValue.getColumnName();

        //获取范围数据的封装
        Range<Date> valueRange = ShardingValue.getValueRange();
        //判断下限
        if (valueRange.hasLowerBound()) {
            //获取下限日期（起始值）
            Date lowerDate = valueRange.lowerEndpoint();
            //获取年份  dsNames--> ds_2021 ds_2022 ds_2023
            int year = new DateTime(lowerDate).getYear();
            dsNames= dsNames.stream().filter(dsName->Integer.parseInt(dsName.substring(dsName.lastIndexOf("-")+1))>=year)
                    .collect(Collectors.toList());
        }
        //2.2 判断是否有上限值
        if (valueRange.hasUpperBound()) {
            Date upperDate = valueRange.upperEndpoint();
            int year = new DateTime(upperDate).getYear();
            dsNames= dsNames.stream().filter(dsName->Integer.parseInt(dsName.substring(dsName.lastIndexOf("-")+1))<=year)
                    .collect(Collectors.toList());
        }
        return dsNames;
    }
}
