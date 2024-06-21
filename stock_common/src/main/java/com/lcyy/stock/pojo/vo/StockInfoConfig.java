package com.lcyy.stock.pojo.vo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author: dlwlrma
 * @data 2024年06月21日 15:35
 * @Description: TODO: 定义股票相关信息封装
 */
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
    private List<String> inner;

    /**
     * TODO: 国外大盘数据集合
     * @author dlwlrma
     * @date 2024/6/21 15:37
     * @param null
     * @return null
     */
    private List<String> outer;

}
