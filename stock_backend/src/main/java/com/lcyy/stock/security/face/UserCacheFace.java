package com.lcyy.stock.security.face;

import com.lcyy.stock.pojo.domain.MenusPermDomain;
import com.lcyy.stock.pojo.entity.SysPermission;

import java.util.List;

/**
 * @author: dlwlrma
 * @data 2024年07月25日 16:14
 * @Description: TODO:
 */
public interface UserCacheFace {
    /**
     * 获取指定用户的权限集合
     * @param uid
     */
    public List<SysPermission> getUserPermsById(Long uid);
    /**
     * 缓存用户权限信息
     * @param uid
     * @return
     */
    public List<String> getUserPermsAndRole(List<SysPermission> perms,Long uid);

    /**
     * 用户前端侧边栏信息缓存
     * @param perms
     * @return
     */
    public List<MenusPermDomain> getMenus(List<SysPermission> perms, Long uid);

    /**
     * 用户关联的按钮权限信息缓存
     * @param perms
     * @return
     */
    public List<String> getPermissionsButton(List<SysPermission> perms, Long uid);
}
