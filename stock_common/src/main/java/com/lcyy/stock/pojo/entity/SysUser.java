package com.lcyy.stock.pojo.entity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户表
 * @TableName sys_user
 */
@Data
@ApiModel("用户的基本信息封装")
public class SysUser implements Serializable {
    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户主键ID")
    private Long id;

    /**
     * 账户
     */
    @ApiModelProperty(value = "用户的姓名")
    private String username;

    /**
     * 用户密码密文
     */
    @ApiModelProperty(value = "用户的密码")
    private String password;

    /**
     * 手机号码
     */
    @ApiModelProperty(value = "用户的手机号")
    private String phone;

    /**
     * 真实名称
     */
    @ApiModelProperty(value = "用户的真实姓名")
    private String realName;

    /**
     * 昵称
     */
    @ApiModelProperty(value = "用户的昵称")
    private String nickName;

    /**
     * 邮箱(唯一)
     */
    @ApiModelProperty(value = "用户的邮箱并且是唯一的")
    private String email;

    /**
     * 账户状态(1.正常 2.锁定 )
     */
    @ApiModelProperty(value = "用户的账户状态：1是正常，2是锁定")
    private Integer status;

    /**
     * 性别(1.男 2.女)
     */
    @ApiModelProperty(value = "用户的性别，1是男，2是女")
    private Integer sex;

    /**
     * 是否删除(1未删除；0已删除)
     */
    @ApiModelProperty(value = "是否删除，1是未删除，0是已删除")
    private Integer deleted;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人是谁")
    private Long createId;

    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人是谁")
    private Long updateId;

    /**
     * 创建来源(1.web 2.android 3.ios )
     */
    @ApiModelProperty(value = "创建的来源，1是web，2是android，3是ios")
    private Integer createWhere;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建的时间")
    private Date createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新的时间")
    private Date updateTime;

    @ApiModelProperty(value = "常量")
    private static final long serialVersionUID = 1L;
}