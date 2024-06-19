package com.lcyy.stock.vo.req;

import lombok.Data;

/**
 * @author: dlwlrma
 * @data 2024年06月19日 18:15
 * @Description: TODO: 登录请求数据 例如： 用户名，密码，验证码
 */

@Data
public class LoginReqVo {
    //用户名
    private String username;

    //密码
    private String password;

    //验证码
    private String code;
}
