package com.lcyy.stock.service;

import com.lcyy.stock.pojo.domain.SysPermissionDomain;
import com.lcyy.stock.pojo.entity.SysPermission;
import com.lcyy.stock.vo.req.PermissionAddVo;
import com.lcyy.stock.vo.req.PermissionUpdateVo;
import com.lcyy.stock.vo.resp.R;

import java.util.List;

/**
 * @author: dlwlrma
 * @data 2024年07月22日 12:51
 * 用户权限接口
 */
public interface PermsService {

    R<List<SysPermission>> getAllPerms();

    R<List<SysPermissionDomain>> getPermsTree();

    R addPerms(PermissionAddVo permissionAddVo);

    R update(PermissionUpdateVo permissionUpdateVo);

    R deletePerms(Long permissionId);

}
