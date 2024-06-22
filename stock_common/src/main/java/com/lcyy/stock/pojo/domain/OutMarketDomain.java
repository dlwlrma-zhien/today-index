package com.lcyy.stock.pojo.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: dlwlrma
 * @data 2024年06月22日 13:17
 * @Description: TODO: 国外板块数据领域封装
 */
@Data
@ApiModel("国外大盘数据")
public class OutMarketDomain {
    /**
     * 大盘编码
     */
    @ApiModelProperty("大盘编码")
    private String code;

    /**
     * 大盘名称
     */
    @ApiModelProperty("大盘名称")
    private String name;

    /**
     * 当前点
     */
    @ApiModelProperty("大盘当前时间点")
    private BigDecimal curPoint;

    /**
     * 涨跌值
     */
    @ApiModelProperty("涨跌值")
    private BigDecimal upDown;

    /**
     * 涨幅
     */
    @ApiModelProperty("涨幅")
    private BigDecimal rose;

    /**
     * 当前时间 JsonFormat 时间序列化（来源自于jackson）为2024
     */
    @ApiModelProperty("当前时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date curTime;
}
