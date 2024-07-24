package com.lcyy.stock.security.service;

import com.google.common.base.Strings;
import com.lcyy.stock.mapper.SysPermissionMapper;
import com.lcyy.stock.mapper.SysRoleMapper;
import com.lcyy.stock.mapper.SysUserMapper;
import com.lcyy.stock.pojo.domain.MenusPermDomain;
import com.lcyy.stock.pojo.entity.SysPermission;
import com.lcyy.stock.pojo.entity.SysRole;
import com.lcyy.stock.pojo.entity.SysUser;
import com.lcyy.stock.security.user.LoginUserDetail;
import com.lcyy.stock.utils.ParsePerm;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: dlwlrma
 * @data 2024年07月24日 13:45
 * @Description: TODO:获取用户详情服务bean
 */
@Service("UserDetailsService")
public class LoginUserDetailService implements UserDetailsService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysPermissionMapper sysPermissionMapper;

    @Autowired
    private SysRoleMapper sysRoleMapper;


    /**
     * 根据用户传入的用户名称，获取用户的详细信息
     * @author dlwlrma
     * @date 2024/7/24 13:47
     * @param userName
     * @return org.springframework.security.core.userdetails.UserDetails
     */
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        //1.根据用户名称
        SysUser dbUser = sysUserMapper.findUserInfoByUserName(userName);
        if (dbUser==null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        //2.组装UserDetail
        List<SysPermission> permissions = sysPermissionMapper.getPermsByUserId(dbUser.getId());
        //前端需要的获取树状权限菜单数据
        List<MenusPermDomain> tree = ParsePerm.digui(permissions, 0l);
        //前端需要的获取菜单按钮集合
        List<String> authBtnPerms = permissions.stream()
                .filter(per -> !Strings.isNullOrEmpty(per.getCode()) && per.getType() == 3)
                .map(per -> per.getCode()).collect(Collectors.toList());
        //5.组装后端需要的权限标识
        //5.1 获取用户拥有的角色
        List<SysRole> roles = sysRoleMapper.getRoleByUserId(dbUser.getId());
        //5.2 将用户的权限标识和角色标识维护到权限集合中
        List<String> perms=new ArrayList<>();
        permissions.stream().forEach(per->{
            if (StringUtils.isNotBlank(per.getPerms())) {
                perms.add(per.getPerms());
            }
        });
        roles.stream().forEach(role->{
            perms.add("ROLE_"+role.getName());
        });
        String[] permStr=perms.toArray(new String[perms.size()]);
        //5.3 将用户权限标识转化成权限对象集合
        List<GrantedAuthority> authorityList = AuthorityUtils.createAuthorityList(permStr);
        //6.封装用户详情信息实体对象
        LoginUserDetail loginUserDetail = new LoginUserDetail();
        //将用户的id nickname等相同属性信息复制到详情对象中
        BeanUtils.copyProperties(dbUser,loginUserDetail);
        loginUserDetail.setMenus(tree);
        loginUserDetail.setAuthorities(authorityList);
        loginUserDetail.setPermissions(authBtnPerms);
        return loginUserDetail;
    }
}
