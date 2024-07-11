package com.lcyy.stock.pojo.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: dlwlrma
 * @data 2024年07月11日 16:08
 * @Description: TODO:个股交易流水行情数据查询--查询最新交易流水，按照交易时间降序取前十
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = ": TODO:个股交易流水行情数据查询--查询最新交易流水，按照交易时间降序取前十")
public class StockScreenSecondDomain {

    //当前时间，精确到分
    @ApiModelProperty("当前时间，精确到分")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "Asia/Shanghai")
    private Date date;

    //交易量
    @ApiModelProperty("交易量")
    private long tradeAmt;

    //交易金额
    @ApiModelProperty("交易金额")
    private BigDecimal tradeVol;

    //交易价格
    @ApiModelProperty("交易价格")
    private BigDecimal tradePrice;

}
