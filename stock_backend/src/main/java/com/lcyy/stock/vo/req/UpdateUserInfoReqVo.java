package com.lcyy.stock.vo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: dlwlrma
 * @data 2024年07月12日 15:52
 * @Description: TODO:更新用户信息数据封装
 */
@ApiModel(description = ": TODO:更新用户信息数据封装")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateUserInfoReqVo {
    //用户id
    @ApiModelProperty("用户id")
    private Long id;

    //用户姓名
    @ApiModelProperty("用户姓名")
    private String username;

    //用户手机号
    @ApiModelProperty("用户手机号")
    private String phone;

    //用户邮箱
    @ApiModelProperty("用户邮箱")
    private String email;

    //用户昵称
    @ApiModelProperty("用户昵称")
    private String nickName;

    //用户真实姓名
    @ApiModelProperty("用户真实姓名")
    private String realName;

    //用户性别
    @ApiModelProperty("用户性别")
    private Integer sex;

    //用户来源
    @ApiModelProperty("用户来源")
    private Integer createWhere;

    //用户状态
    @ApiModelProperty("用户状态")
    private Integer status;
}
