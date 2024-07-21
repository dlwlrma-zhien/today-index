package com.lcyy.stock.vo.req;

import lombok.Data;

import java.util.List;

/**
 * 添加用户响应数据封装
 * @author dlwlrma
 * @date 2024/7/21 17:44
 * @return null
 */
@Data
public class AddRolesAndPermsReqVo {
    private String name;
    private String description;
    private List<Long> permissionsIds;
}