package com.lcyy.stock.security.utils;

/**
 * @author: dlwlrma
 * @data 2024年07月21日 16:46
 * @Description: TODO:配置
 */
public class JwtTokenUtil {
    // Token请求头
    public static final String TOKEN_HEADER = "authorization";
    // Token前缀
    public static final String TOKEN_PREFIX = "Bearer ";
    // 签名主题
    public static final String SUBJECT = "JRZS";
    // 过期时间,单位毫秒
    public static final long EXPIRITION = 1000 * 60 * 60* 24 * 7;
    // 应用密钥// https://blog.csdn.net/MyxZxd/article/details/109728302
    public static final String APPSECRET_KEY = "fhnfhn";
    // 角色权限声明
    private static final String ROLE_CLAIMS = "role";


}
