package com.lcyy.stock.pojo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 角色权限表
 * @TableName sys_role_permission
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysRolePermission implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 菜单权限id
     */
    private Long permissionId;

    /**
     * 创建时间
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;
}