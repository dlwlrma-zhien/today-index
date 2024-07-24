package com.lcyy.stock.security.user;

import com.lcyy.stock.pojo.domain.MenusPermDomain;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

/**
 * @author: dlwlrma
 * @data 2024年07月24日 14:15
 * @Description: TODO:自定义用户认证详情类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginUserDetail implements UserDetails {
    /**
     * 用户名称
     */
    private String username;
//    @Override
//    public String getUsername() {
//        return null;
//    }

    /**
     * 密码
     */
    private String password;
//    @Override
//    public String getPassword() {
//        return null;
//    }

    /**
     * 权限信息
     */
    private List<GrantedAuthority> authorities;
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return null;
//    }

    /**
     * 账户是否过期
     */
    private boolean isAccountNonExpired=true;
//    @Override
//    public boolean isAccountNonExpired() {
//        return false;
//    }

    /**
     * 账户是否被锁定
     *  true：没有被锁定
     */
    private boolean isAccountNonLocked=true;
//    @Override
//    public boolean isAccountNonLocked() {
//        return false;
//    }

    /**
     * 密码是否过期
     *  true:没有过期
     */
    private boolean isCredentialsNonExpired=true;
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return false;
//    }

    /**
     * 账户是否禁用
     *  true：没有禁用
     */
    private boolean isEnabled=true;
//    @Override
//    public boolean isEnabled() {
//        return false;
//    }

    /**
     * 用户ID
     */
    private Long id;
    /**
     * 电话
     */
    private String phone;
    /**
     * 昵称
     */
    private String nickName;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 性别(1.男 2.女)
     */
    private Integer sex;

    /**
     * 账户状态(1.正常 2.锁定 )
     */
    private Integer status;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 权限树，不包含按钮相关权限信息
     */
    private List<MenusPermDomain> menus;

    /**
     * 按钮权限树
     */
    private List<String> permissions;
}
