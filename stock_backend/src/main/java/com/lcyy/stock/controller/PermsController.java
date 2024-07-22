package com.lcyy.stock.controller;

import com.lcyy.stock.pojo.domain.SysPermissionDomain;
import com.lcyy.stock.pojo.entity.SysPermission;
import com.lcyy.stock.service.PermsService;
import com.lcyy.stock.vo.req.PermissionAddVo;
import com.lcyy.stock.vo.req.PermissionUpdateVo;
import com.lcyy.stock.vo.resp.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: dlwlrma
 * @data 2024年07月22日 12:47
 * @Description: TODO:用户权限控制器
 */
@Api(value = "/api", tags = {": TODO:用户权限控制器"})
@RestController
@RequestMapping("/api")
public class PermsController {

    @Autowired
    private PermsService permsService;

    /**
     * 查询所有权限集合
     * @author dlwlrma
     * @date 2024/7/22 12:52
     * @return com.lcyy.stock.vo.resp.R<java.util.List<com.lcyy.stock.pojo.entity.SysPermission>>
     */
    @ApiOperation(value = "查询所有权限集合", notes = "查询所有权限集合", httpMethod = "GET")
    @GetMapping("/permissions")
    public R<List<SysPermission>> getAllPerms(){
        return permsService.getAllPerms();
    }

    /**
     * 添加权限时回显权限树,仅仅显示目录和菜单
     * @author dlwlrma
     * @date 2024/7/22 13:07
     * @return com.lcyy.stock.vo.resp.R<java.util.List<com.lcyy.stock.pojo.domain.SysPermissionDomain>>
     */
    @ApiOperation(value = "添加权限时回显权限树,仅仅显示目录和菜单", notes = "添加权限时回显权限树,仅仅显示目录和菜单", httpMethod = "GET")
    @GetMapping("/permissions/tree")
    public R<List<SysPermissionDomain>> getPermsTree(){
        return permsService.getPermsTree();
    }

    /**
     * 权限添加按钮
     * @author dlwlrma
     * @date 2024/7/22 13:31
     * @param permissionAddVo 
     * @return com.lcyy.stock.vo.resp.R
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "PermissionAddVo", name = "permissionAddVo", value = "", required = true)
    })
    @ApiOperation(value = "权限添加按钮", notes = "权限添加按钮", httpMethod = "POST")
    @PostMapping("/permission")
    public R addPerms(@RequestBody PermissionAddVo permissionAddVo){
        return permsService.addPerms(permissionAddVo);
    }

    /**
     * 更新用户权限
     * @author dlwlrma
     * @date 2024/7/22 14:08
     * @param permissionUpdateVo
     * @return com.lcyy.stock.vo.resp.R
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "PermissionUpdateVo", name = "permissionUpdateVo", value = "", required = true)
    })
    @ApiOperation(value = "更新用户权限", notes = "更新用户权限", httpMethod = "PUT")
    @PutMapping("/permission")
    public R update(@RequestBody PermissionUpdateVo permissionUpdateVo){
        return permsService.update(permissionUpdateVo);
    }

    /**
     * 删除用户权限
     * @author dlwlrma
     * @date 2024/7/22 14:15
     * @return com.lcyy.stock.vo.resp.R
     */
    @ApiOperation(value = "删除用户权限", notes = "删除用户权限", httpMethod = "DELETE")
    @DeleteMapping("/permission/{permissionId}")
    public R deletePerms(@PathVariable Long permissionId){
        return permsService.deletePerms(permissionId);
    }
 }
