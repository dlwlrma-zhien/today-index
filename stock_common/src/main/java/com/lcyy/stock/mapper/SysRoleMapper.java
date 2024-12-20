package com.lcyy.stock.mapper;

import com.lcyy.stock.pojo.entity.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 22818
* @description 针对表【sys_role(角色表)】的数据库操作Mapper
* @createDate 2024-06-18 21:48:00
* @Entity com.lcyy.stock.pojo.entity.SysRole
*/
public interface SysRoleMapper {

    int deleteByPrimaryKey(Long id);

    int insert(SysRole record);

    int insertSelective(SysRole record);

    SysRole selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysRole record);

    int updateByPrimaryKey(SysRole record);

    List<SysRole> selectAll();

    int addRole(@Param("role") SysRole role);

    int deleteRoles(@Param("roleId") String roleId);

    int updateRoleStatus(@Param("roleId") String roleId, @Param("status") Integer status);

    List<SysRole> getRoleByUserId(@Param("id") Long id);
}
