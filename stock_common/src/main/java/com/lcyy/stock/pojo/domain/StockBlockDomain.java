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
 * @data 2024年06月21日 18:23
 * @Description: TODO: 国内板块实体类封装
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel("国内板块数据")
public class StockBlockDomain {
    /**
     * 公司数量
     */
    @ApiModelProperty("公司数量")
    private Integer companyNum;

    /**
     * 交易量
     */
    @ApiModelProperty("交易量")
    private long tradeAmt;

    /**
     * 板块编码
     */
    @ApiModelProperty("板块编码")
    private String code;

    /**
     * 平均价
     */
    @ApiModelProperty("平均价格")
    private BigDecimal avgPrice;

    /**
     * 板块名称
     */
    @ApiModelProperty("板块名称")
    private String name;

    /**
     * 当前日期 时间序列化（来源自于jackson）为2024
     */
    @ApiModelProperty("当前日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date curDate;

    /**
     * 交易金额
     */
    @ApiModelProperty("交易总金额")
    private BigDecimal tradeVol;

    /**
     * 涨跌率
     */
    @ApiModelProperty("涨幅")
    private BigDecimal updownRate;
}
