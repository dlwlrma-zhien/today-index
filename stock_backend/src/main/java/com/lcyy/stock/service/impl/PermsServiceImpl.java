package com.lcyy.stock.service.impl;

import com.lcyy.stock.execption.BusinessException;
import com.lcyy.stock.mapper.SysPermissionMapper;
import com.lcyy.stock.mapper.SysRolePermissionMapper;
import com.lcyy.stock.pojo.domain.SysPermissionDomain;
import com.lcyy.stock.pojo.entity.SysPermission;
import com.lcyy.stock.service.PermsService;
import com.lcyy.stock.utils.IdWorker;
import com.lcyy.stock.vo.req.PermissionAddVo;
import com.lcyy.stock.vo.req.PermissionUpdateVo;
import com.lcyy.stock.vo.resp.R;
import com.lcyy.stock.vo.resp.ResponseCode;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: dlwlrma
 * @data 2024年07月22日 12:55
 * @Description: TODO:
 */
@Service("PermsService")
@Data
@Slf4j
public class PermsServiceImpl implements PermsService {

    @Autowired
    private SysPermissionMapper sysPermissionMapper;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private SysRolePermissionMapper sysRolePermissionMapper;

    @Override
    public R<List<SysPermission>> getAllPerms() {
        List<SysPermission> list = sysPermissionMapper.selectAll();
        if (CollectionUtils.isEmpty(list)) {
            return R.error(ResponseCode.DATA_ERROR);
        }
        return R.ok(list);
    }

    @Override
    public R<List<SysPermissionDomain>> getPermsTree() {
        List<SysPermissionDomain> permsTreeList = new ArrayList<>();
        List<SysPermission> all = sysPermissionMapper.selectAll();
        permsTreeList.add(SysPermissionDomain.builder().id(0L).title("顶级菜单").level(0).build());
        permsTreeList.addAll(getPermissionLevelTree(all,0l,1));
        if (CollectionUtils.isEmpty(permsTreeList)) {
            return R.error(ResponseCode.DATA_ERROR);
        }
        return R.ok(permsTreeList);
    }

    @Override
    public R addPerms(PermissionAddVo permissionAddVo) {
        SysPermission addPerms = new SysPermission();
        BeanUtils.copyProperties(permissionAddVo,addPerms);
        checkPermissionForm(addPerms);
        addPerms.setId(idWorker.nextId());
        int i = sysPermissionMapper.addPerms(addPerms);
        if (i>0) {
            return R.ok("添加成功");
        }
        return R.error(ResponseCode.ERROR);
    }

    @Override
    public R update(PermissionUpdateVo permissionUpdateVo) {
        SysPermission addPerms = new SysPermission();
        BeanUtils.copyProperties(permissionUpdateVo,addPerms);
        checkPermissionForm(addPerms);
        int i=sysPermissionMapper.updatePerms(addPerms);
        if(i>0){
            return R.ok("更新成功");
        }
        else return R.error(ResponseCode.DATA_ERROR.getMessage());
    }

    @Override
    public R deletePerms(Long permissionId) {
        int count =this.sysPermissionMapper.findChildrenCountByParentId(permissionId);
        if (count>0) {
            throw new BusinessException(ResponseCode.ROLE_PERMISSION_RELATION.getMessage());
        }
        //2.删除角色关联权限的信息
        this.sysRolePermissionMapper.deleteByPermissionId(permissionId);
        //3.更新权限状态为已删除
        SysPermission permission = SysPermission.builder().id(permissionId).deleted(0).updateTime(new Date()).build();
        int updateCount = this.sysPermissionMapper.updateByPrimaryKeySelective(permission);
        if (updateCount!=1) {
            throw new BusinessException(ResponseCode.ERROR.getMessage());
        }
        return R.ok(ResponseCode.SUCCESS.getMessage());
    }

    /**
     * 递归设置级别，用于权限列表 添加/编辑 所属菜单树结构数据
     * @author dlwlrma
     * @date 2024/7/22 13:17
     * @param permissions
     * @param id
     * @param level
     * @return java.util.List<com.lcyy.stock.pojo.domain.SysPermissionDomain>
     */
    private List<SysPermissionDomain> getPermissionLevelTree(List<SysPermission> permissions, Long id, Integer level){
        List<SysPermissionDomain> list = new ArrayList<>();
        for (SysPermission permission : permissions){
            if(permission.getType().intValue()!=3 && permission.getId().equals(id)){
                SysPermissionDomain nodeTreeVo = new SysPermissionDomain();
                nodeTreeVo.setId(permission.getId());
                nodeTreeVo.setTitle(permission.getTitle());
                nodeTreeVo.setLevel(permission.getType());
                list.add(nodeTreeVo);
                list.addAll(getPermissionLevelTree(permissions,permission.getId(),level+1));
            }
        }
        return list;
    }

    private void checkPermissionForm(SysPermission vo){
        if (vo!=null || vo.getType()!=null || vo.getPid() != null) {
            Integer type = vo.getType();
            Long pid = vo.getPid();
            SysPermission permission = this.sysPermissionMapper.selectByPrimaryKey(pid);
            if(type==1){
                if(!pid.equals("0") || (permission!=null && permission.getType()>1)){
                    throw new BusinessException(ResponseCode.OPERATION_MENU_PERMISSION_URL_CODE_NULL.getMessage());
                }
            } else if (type == 2) {
                if(permission ==null || permission.getType() !=-1){
                    throw new BusinessException(ResponseCode.OPERATION_MENU_PERMISSION_CATALOG_ERROR.getMessage().toString());
                }
                if(StringUtils.isEmpty(vo.getUrl())){
                    throw new BusinessException(ResponseCode.OPERATION_MENU_PERMISSION_URL_CODE_NULL.getMessage());
                }
            } else if (type==3) {
                if(permission==null||permission.getType()!=2){
                    throw new BusinessException(ResponseCode.OPERATION_MENU_PERMISSION_BTN_ERROR.getMessage());
                } else if (vo.getUrl() == null || vo.getCode() == null || vo.getMethod() == null) {
                    throw new BusinessException(ResponseCode.DATA_ERROR.getMessage());
                }
            }
            else {
                throw new BusinessException(ResponseCode.DATA_ERROR.getMessage());
            }
        }else {
            throw new BusinessException(ResponseCode.DATA_ERROR.getMessage());
        }
    }
}
