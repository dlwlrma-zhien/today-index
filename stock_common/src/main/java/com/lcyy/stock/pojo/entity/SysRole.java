package com.lcyy.stock.pojo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 角色表
 * @TableName sys_role
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysRole implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 状态(1:正常0:弃用)
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除(1未删除；0已删除)
     */
    private Integer deleted;

    private static final long serialVersionUID = 1L;
}