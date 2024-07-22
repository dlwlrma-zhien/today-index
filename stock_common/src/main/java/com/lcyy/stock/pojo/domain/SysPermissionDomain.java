package com.lcyy.stock.pojo.domain;

import com.lcyy.stock.pojo.entity.SysPermission;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: dlwlrma
 * @data 2024年07月22日 13:02
 * @Description: TODO:权限管理用户数据封装
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SysPermissionDomain {
    private Long id;
    private String title;
    private Integer level;
    //有参构造器
    public SysPermissionDomain(SysPermission sysPermission){
        id = sysPermission.getId();
        title = sysPermission.getTitle();
        level = sysPermission.getType();
    }
}
