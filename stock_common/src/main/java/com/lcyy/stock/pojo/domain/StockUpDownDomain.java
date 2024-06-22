package com.lcyy.stock.pojo.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: dlwlrma
 * @data 2024年06月22日 14:46
 * @Description: TODO: 封装股票涨跌信息
 */
@ApiModel(description = ": TODO: 封装股票涨跌信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StockUpDownDomain {

    /**
     * 股票编码
     */
    @ApiModelProperty("股票编码")
    private String code;

    /**
     * 股票名称
     */
    @ApiModelProperty("股票名称")
    private String name;

    /**
     * 前收盘价
     */
    @ApiModelProperty("前收盘价")
    private BigDecimal preClosePrice;

    /**
     * 当前价格
     */
    @ApiModelProperty("当前价格")
    private BigDecimal tradePrice;

    /**
     * 涨跌
     */
    @ApiModelProperty("涨跌")
    private BigDecimal increase;

    /**
     * 涨幅
     */
    @ApiModelProperty("涨幅")
    private BigDecimal upDown;

    /**
     * 振幅
     */
    @ApiModelProperty("振幅")
    private BigDecimal amplitude;

    /**
     * 交易量
     */
    @ApiModelProperty("交易量")
    private Long tradeAmt;

    /**
     * 交易金额
     */
    @ApiModelProperty("交易金额")
    private BigDecimal tradeVol;

    /**
     * 日期
     */
    @ApiModelProperty("日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date curDate;
}
