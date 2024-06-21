package com.lcyy.stock.vo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: dlwlrma
 * @data 2024年06月19日 18:15
 * @Description: TODO: 登录请求数据 例如： 用户名，密码，验证码
 */

@Data
@ApiModel("登录请求数据")
public class LoginReqVo {
    //用户名
    @ApiModelProperty(value = "用户名")
    private String username;

    //密码
    @ApiModelProperty(value = "名文密码")
    private String password;

    //验证码
    @ApiModelProperty(value = "验证码")
    private String code;

    //会话id
    @ApiModelProperty(value = "会话id")
    private String sessionId;
}
