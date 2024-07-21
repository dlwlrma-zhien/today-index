package com.lcyy.stock.service;

import com.lcyy.stock.pojo.domain.MenusPermDomain;
import com.lcyy.stock.pojo.entity.SysRole;
import com.lcyy.stock.vo.req.AddRolesAndPermsReqVo;
import com.lcyy.stock.vo.req.UpdatePermsByRoleIdReqVo;
import com.lcyy.stock.vo.resp.PageResult;
import com.lcyy.stock.vo.resp.R;

import java.util.List;

/**
 * @author: dlwlrma
 * @data 2024年07月12日 14:40
 * TODO:用户角色相关接口
 */
public interface RoleService {

    R<PageResult<SysRole>> getRolesPageInfo(Integer pageNum, Integer pageSize);

    R<List<MenusPermDomain>> getPermissionsTreeAll();

    R addRolesAndPerms(AddRolesAndPermsReqVo reqVo);

    R<List<Long>> getPermsByRoleId(String roleId);

    R updatePermsByRoleId(UpdatePermsByRoleIdReqVo reqVo);

    R deleteRoles(String roleId);

    R updateRoleStatus(String roleId, Integer status);
}
