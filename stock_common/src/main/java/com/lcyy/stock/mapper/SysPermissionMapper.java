package com.lcyy.stock.mapper;

import com.lcyy.stock.pojo.entity.SysPermission;
import com.lcyy.stock.pojo.entity.SysRolePermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 22818
* @description 针对表【sys_permission(权限表（菜单）)】的数据库操作Mapper
* @createDate 2024-06-18 21:48:00
* @Entity com.lcyy.stock.pojo.entity.SysPermission
*/
public interface SysPermissionMapper {

    int deleteByPrimaryKey(Long id);

    int insert(SysPermission record);

    int insertSelective(SysPermission record);

    SysPermission selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysPermission record);

    int updateByPrimaryKey(SysPermission record);

    List<SysPermission> selectAll();

    int insertPerms(@Param("perms") List<SysRolePermission> list);

    int addPerms(@Param("addPerms") SysPermission addPerms);

    int updatePerms(@Param("addPerms") SysPermission addPerms);

    int findChildrenCountByParentId(@Param("permissionId") Long permissionId);
}
