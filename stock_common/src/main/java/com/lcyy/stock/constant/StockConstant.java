package com.lcyy.stock.constant;

/**
 * TODO: 定义常量值类,方便维护
 * @author dlwlrma
 * @date 2024/6/20 18:45
 * @return null
 */
public class StockConstant {

    /**
     * 定义校验码的前缀
     */
    public static final String CHECK_PREFIX="CK:";

    /**
     * http请求头携带Token信息key
     */
    public static final String TOKEN_HEADER = "authorization";

    /**
     * 缓存股票相关信息的cacheNames命名前缀
     */
    public static final String STOCK="stock";
}