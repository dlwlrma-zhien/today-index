package com.lcyy.stock.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author: dlwlrma
 * @data 2024年06月21日 15:35
 * @Description: TODO: 定义股票相关信息封装
 */
@ApiModel(description = ": TODO: 定义股票相关信息封装")
@Data
@ConfigurationProperties(prefix = "stock")
public class StockInfoConfig {

    /**
     * TODO: 国内A股大盘数据集合
     * @author dlwlrma
     * @date 2024/6/21 15:37
     * @param null
     * @return null
     */
    @ApiModelProperty("TODO: 国内A股大盘数据集合")
    private List<String> inner;

    /**
     * TODO: 国外大盘数据集合
     * @author dlwlrma
     * @date 2024/6/21 15:37
     * @param null
     * @return null
     */
    @ApiModelProperty("TODO: 国外大盘数据集合")
    private List<String> outer;

    /**
     * 查询当前股票涨幅区间的顺序排列
     * @author dlwlrma
     * @date 2024/6/27 21:09
     * @param null
     * @return null
     */
    @ApiModelProperty("查询当前股票涨幅区间的顺序排列")
    private List<String> upDownRange;

    /**
     * 大盘，外盘，个股的公共的url
     * @author dlwlrma
     * @date 2024/6/30 13:13
     */
    @ApiModelProperty("大盘，外盘，个股的公共的url")
    private String marketUrl;

    /**
     * 板块采集url
     * @author dlwlrma
     * @date 2024/6/30 13:14
     * @param null
     * @return null
     */
    @ApiModelProperty("板块采集url")
    private String blockUrl;

}
