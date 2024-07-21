package com.lcyy.stock.pojo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author: dlwlrma
 * @data 2024年07月21日 17:17
 * @Description: TODO:侧边栏权限树（不包含按钮权限）
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenusPermDomain {
    //权限ID
    private Long id;
    //权限标题
    private String title;
    //权限图标（按钮权限无图片）
    private String icon;
    //请求地址
    private String path;
    //权限名称对应前端vue组件名称
    private String name;
    //子权限树
    private List<MenusPermDomain> children;
}
