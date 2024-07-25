package com.lcyy.stock.security.face.impl;

import com.lcyy.stock.mapper.SysPermissionMapper;
import com.lcyy.stock.mapper.SysRoleMapper;
import com.lcyy.stock.pojo.domain.MenusPermDomain;
import com.lcyy.stock.pojo.entity.SysPermission;
import com.lcyy.stock.pojo.entity.SysRole;
import com.lcyy.stock.security.face.UserCacheFace;
import com.lcyy.stock.utils.ParsePerm;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: dlwlrma
 * @data 2024年07月25日 16:21
 * @Description: TODO:用户权限数据缓存
 */
@Component("UserCacheFace")
@CacheConfig(cacheNames = "user")
public class UserCacheFaceImpl implements UserCacheFace {

    @Autowired
    private SysPermissionMapper sysPermissionMapper;

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Cacheable(key="'getUserPermsById'+#id")
    @Override
    public List<SysPermission> getUserPermsById(Long id) {
        //获取指定用户的权限集合 添加获取侧边栏数据和按钮权限的结合信息
        List<SysPermission> perms = sysPermissionMapper.getPermsByUserId(id);
        return perms;
    }

    @Cacheable(key="'getUserPermsAndRole'+#id")
    @Override
    public List<String> getUserPermsAndRole(List<SysPermission> perms, Long id) {
        //5.1 获取用户拥有的角色
        List<SysRole> roles = sysRoleMapper.getRoleByUserId(id);
        //5.2 将用户的权限标识和角色标识维护到权限集合中
        List<String> ps=new ArrayList<>();
        perms.stream().forEach(per->{
            if (StringUtils.isNotBlank(per.getPerms())) {
                ps.add(per.getPerms());
            }
        });
        roles.stream().forEach(role->{
            ps.add("ROLE_"+role.getName());
        });
        return ps;
    }

    @Cacheable(key="'getMenus'+#id")
    @Override
    public List<MenusPermDomain> getMenus(List<SysPermission> perms, Long id) {
        //前端需要的获取树状权限菜单数据
        List<MenusPermDomain> menus = ParsePerm.digui(perms, 0L);
        return menus;
    }

    @Cacheable(key="'getPermissionsButton'+#id")
    @Override
    public List<String> getPermissionsButton(List<SysPermission> perms, Long id) {
        //前端需要的获取菜单按钮集合
        List<String> permissions = ParsePerm.getPermissions(perms);
        return permissions;
    }
}
