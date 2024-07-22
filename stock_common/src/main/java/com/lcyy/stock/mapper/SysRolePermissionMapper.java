package com.lcyy.stock.mapper;

import com.lcyy.stock.pojo.entity.SysRolePermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 22818
* @description 针对表【sys_role_permission(角色权限表)】的数据库操作Mapper
* @createDate 2024-06-18 21:48:00
* @Entity com.lcyy.stock.pojo.entity.SysRolePermission
*/
public interface SysRolePermissionMapper {

    int deleteByPrimaryKey(Long id);

    int insert(SysRolePermission record);

    int insertSelective(SysRolePermission record);

    SysRolePermission selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysRolePermission record);

    int updateByPrimaryKey(SysRolePermission record);

    List<Long> getPermsByRoleId(@Param("roleId") String roleId);

    int deleteByRoleId(@Param("id") Long id);

    void deleteByPermissionId(@Param("permissionId") Long permissionId);
}
