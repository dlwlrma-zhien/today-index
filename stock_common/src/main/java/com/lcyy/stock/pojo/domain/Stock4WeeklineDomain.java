package com.lcyy.stock.pojo.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: dlwlrma
 * @data 2024年07月11日 15:20
 * @Description: TODO: 统计每周国内的股票数据信息
 */
@ApiModel(description = ": TODO: 统计每周国内的股票数据信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Stock4WeeklineDomain {

    //一周内平均价
    @ApiModelProperty("一周内平均价")
    private BigDecimal avgPrice;

    //一周内最低价
    @ApiModelProperty("一周内最低价")
    private BigDecimal minPrice;

    //周一开盘价
    @ApiModelProperty("周一开盘价")
    private BigDecimal openPrice;

    //一周内最高价
    @ApiModelProperty("一周内最高价")
    private BigDecimal maxPrice;

    //周五收盘价
    @ApiModelProperty("周五收盘价")
    private BigDecimal closePrice;

    //一周内最大开盘时间
    @ApiModelProperty("一周内最大开盘时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "Asia/Shanghai")
    private Date maxTime;

    //股票编码
    @ApiModelProperty("股票编码")
    private String code;

}

