package com.lcyy.stock.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcyy.stock.mapper.SysPermissionMapper;
import com.lcyy.stock.mapper.SysRoleMapper;
import com.lcyy.stock.mapper.SysRolePermissionMapper;
import com.lcyy.stock.pojo.domain.MenusPermDomain;
import com.lcyy.stock.pojo.entity.SysPermission;
import com.lcyy.stock.pojo.entity.SysRole;
import com.lcyy.stock.pojo.entity.SysRolePermission;
import com.lcyy.stock.service.RoleService;
import com.lcyy.stock.utils.IdWorker;
import com.lcyy.stock.utils.ParsePerm;
import com.lcyy.stock.vo.req.AddRolesAndPermsReqVo;
import com.lcyy.stock.vo.req.UpdatePermsByRoleIdReqVo;
import com.lcyy.stock.vo.resp.PageResult;
import com.lcyy.stock.vo.resp.R;
import com.lcyy.stock.vo.resp.ResponseCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: dlwlrma
 * @data 2024年07月21日 17:05
 * @Description: TODO:业务层实现业务接口
 */
@ApiModel(description = ": TODO:业务层实现业务接口")
@Service("RoleService")
@Slf4j
public class RoleServiceImpl implements RoleService {

    @ApiModelProperty(hidden = true)
    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    SysPermissionMapper sysPermissionMapper;

    @Autowired
    private SysRolePermissionMapper sysRolePermissionMapper;

    @Autowired
    private IdWorker idWorker;

    @Override
    public R<PageResult<SysRole>> getRolesPageInfo(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageNum);
        List<SysRole> rolelist=sysRoleMapper.selectAll();
        if (CollectionUtils.isEmpty(rolelist)) {
            return R.error(ResponseCode.DATA_ERROR);
        }
        PageInfo<SysRole> pageInfo = new PageInfo<>(rolelist);
        PageResult<SysRole> result = new PageResult<>(pageInfo);
        return R.ok(result);
    }

    @Override
    public R<List<MenusPermDomain>> getPermissionsTreeAll() {
       List<SysPermission> permissionsList = sysPermissionMapper.selectAll();
        if (CollectionUtils.isEmpty(permissionsList)) {
            return R.error(ResponseCode.DATA_ERROR);
        }
        return R.ok(ParsePerm.digui(permissionsList,0L));
    }

    @Override
    public R addRolesAndPerms(AddRolesAndPermsReqVo reqVo) {
       SysRole role = SysRole.builder().id(idWorker.nextId()).name(reqVo.getName()).description(reqVo.getDescription()).build();
       int i =sysRoleMapper.addRole(role);

        if (i>0) {
            List<SysRolePermission> list = new ArrayList<>();
            for (Long permissionid : reqVo.getPermissionsIds()){
                list.add(SysRolePermission.builder().id(idWorker.nextId()).roleId(role.getId()).permissionId(permissionid).build());
            }
            int j = sysPermissionMapper.insertPerms(list);
            if (j>0) {
                return R.ok("操作成功！");
            }
        }
        return R.error(ResponseCode.ERROR);
    }

    @Override
    public R<List<Long>> getPermsByRoleId(String roleId) {
        List<Long> pemers = sysRolePermissionMapper.getPermsByRoleId(roleId);
        if (CollectionUtils.isEmpty(pemers)) {
            return R.error(ResponseCode.ERROR);
        }
        return R.ok(pemers);
    }

    @Override
    public R updatePermsByRoleId(UpdatePermsByRoleIdReqVo reqVo) {
       List<Long> perms = sysRolePermissionMapper.getPermsByRoleId(String.valueOf(reqVo.getId()));
        if (!CollectionUtils.isEmpty(perms)) {
            int i = sysRolePermissionMapper.deleteByRoleId(reqVo.getId());
            if (i<=0) {
                return R.error(ResponseCode.ERROR);
            }
        }
        List<SysRolePermission> rolePermissions =  new ArrayList<>();
        for (Long permissionId : reqVo.getPermissionsIds()){
            rolePermissions.add(SysRolePermission.builder().id(idWorker.nextId()).roleId(reqVo.getId()).permissionId(permissionId).build());
        }
        int j = sysPermissionMapper.insertPerms(rolePermissions);
        if (j>0) {
            return R.ok("操作成功");
        }
        return R.error(ResponseCode.ERROR);
    }

    @Override
    public R deleteRoles(String roleId) {
       int i = sysRoleMapper.deleteRoles(roleId);
        if (i>0) {
            return R.ok(ResponseCode.SUCCESS);
        }
        return R.error(ResponseCode.ERROR);
    }

    @Override
    public R updateRoleStatus(String roleId, Integer status) {
        int i =sysRoleMapper.updateRoleStatus(roleId,status);
        if (i>0) {
            return R.ok("操作成功");
        }
        return R.error(ResponseCode.ERROR);
    }
}
