package com.lcyy.stock.pojo.domain;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author: dlwlrma
 * @data 2024年07月11日 17:00
 * @Description: TODO：个股主营业务信息封装
 */
public class stockBusinessDomain {

    //股票编码
    @ApiModelProperty(value = "股票编码")
    private String code;
    //行业，也就是行业板块名称
    @ApiModelProperty(value = "行业，也就是行业板块名称")
    private String trade;
    //公司主营业务
    @ApiModelProperty(value = "公司主营业务")
    private String business;
    //公司名称
    @ApiModelProperty(value = "公司名称")
    private String name;
}
