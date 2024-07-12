package com.lcyy.stock.mapper;

import com.lcyy.stock.pojo.domain.SysUserDomain;
import com.lcyy.stock.pojo.entity.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
* @author dlwlrma
* @description 针对表【sys_user(用户表)】的数据库操作Mapper
* @createDate 2024-06-18 21:48:00
* @Entity com.lcyy.stock.pojo.entity.SysUser
*/
public interface SysUserMapper {

    int deleteByPrimaryKey(Long id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    SysUser findUserInfoByUserName(@Param("userName") String userName);
    //查询所有信息
    List<SysUser> findAll();

    List<SysUserDomain> getUsersInfoByMCondition(@Param("username") String userName, @Param("nickName") String nickName, @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    int addUser(@Param("user") SysUser user);
}
