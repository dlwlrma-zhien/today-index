package com.lcyy.stock.vo.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author: dlwlrma
 * @data 2024年07月12日 15:12
 * @Description: TODO:封装用户信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRoleReqVo {

    private Long userId;

    private List<Long> roleIds;
 }
