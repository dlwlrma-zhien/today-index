package com.lcyy.stock.vo.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


/**
 * @author: dlwlrma
 * @data 2024年07月12日 11:47
 * @Description: TODO: 用户请求数据封装
 */
@ApiModel(description = ": TODO: 用户请求数据封装")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserMconditionReqvo {

    //分页参数
    @ApiModelProperty("分页参数")
    private Integer pageNum;

    //分页大小
    @ApiModelProperty("分页大小")
    private Integer pageSize;

    //用户姓名
    @ApiModelProperty("用户姓名")
    private String userName;

    //真实姓名
    @ApiModelProperty("真实姓名")
    private String nickName;

    //开始时间
    @ApiModelProperty("开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Shanghai")
    private Date startTime;

    //结束时间
    @ApiModelProperty("结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Shanghai")
    private Date endTime;

}
