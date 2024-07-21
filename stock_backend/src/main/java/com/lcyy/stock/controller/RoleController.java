package com.lcyy.stock.controller;

import com.lcyy.stock.pojo.domain.MenusPermDomain;
import com.lcyy.stock.pojo.entity.SysRole;
import com.lcyy.stock.service.RoleService;
import com.lcyy.stock.vo.req.AddRolesAndPermsReqVo;
import com.lcyy.stock.vo.req.UpdatePermsByRoleIdReqVo;
import com.lcyy.stock.vo.resp.PageResult;
import com.lcyy.stock.vo.resp.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author: dlwlrma
 * @data 2024年07月21日 16:56
 * @Description: TODO:用户角色控制器
 */
@Api(value = "/api", tags = {": TODO:用户角色控制器"})
@RestController
@RequestMapping("/api")
public class RoleController {

    @Autowired
    private RoleService roleService;

    /**
     * 角色管理
     * @author dlwlrma
     * @date 2024/7/21 17:19
     * @param map
     * @return com.lcyy.stock.vo.resp.R<com.lcyy.stock.vo.resp.PageResult<com.lcyy.stock.pojo.entity.SysRole>>
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "Map<String, Integer>", name = "map", value = "", required = true)
    })
    @ApiOperation(value = "", notes = "", httpMethod = "POST")
    @PostMapping("/roles")
    public R<PageResult<SysRole>> getRolesPageInfo(@RequestBody Map<String,Integer> map){
        return roleService.getRolesPageInfo(map.get("pageNum"),map.get("pageSize"));
    }

    /**
     * 树状结构回显权限集合,底层通过递归获取权限数据集合
     * @author dlwlrma
     * @date 2024/7/21 17:19
     * @return com.lcyy.stock.vo.resp.R<java.util.List<com.lcyy.stock.pojo.domain.MenusPermDomain>>
     */
    @ApiOperation(value = "树状结构回显权限集合,底层通过递归获取权限数据集合", notes = "树状结构回显权限集合,底层通过递归获取权限数据集合", httpMethod = "GET")
    @GetMapping("/permissions/tree/all")
    public R<List<MenusPermDomain>> getPermissionsTreeAll(){
       return roleService.getPermissionsTreeAll();
    }

    /**
     * 添加角色和角色关联权限
     * @author dlwlrma
     * @date 2024/7/21 20:19
     * @param reqVo
     * @return com.lcyy.stock.vo.resp.R
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "AddRolesAndPermsReqVo", name = "reqVo", value = "", required = true)
    })
    @ApiOperation(value = "添加角色和角色关联权限", notes = "添加角色和角色关联权限", httpMethod = "POST")
    @PostMapping("role")
    public R addRolesAndPerms(@RequestBody AddRolesAndPermsReqVo reqVo){
        return roleService.addRolesAndPerms(reqVo);
    }

    /**
     * 根据角色id查找对应的权限id集合
     * @author dlwlrma
     * @date 2024/7/21 20:21
     * @param roleId
     * @return com.lcyy.stock.vo.resp.R<java.util.List<java.lang.Long>>
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "string", name = "roleId", value = "", required = true)
    })
    @ApiOperation(value = "", notes = "", httpMethod = "GET")
    @GetMapping("/role/{roleId}")
    public R<List<Long>> getPermsByRoleId(@PathVariable String roleId){
        return roleService.getPermsByRoleId(roleId);
    }

    /**
     * 更新用户角色信息
     * @author dlwlrma
     * @date 2024/7/21 20:32
     * @param reqVo
     * @return com.lcyy.stock.vo.resp.R
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "UpdatePermsByRoleIdReqVo", name = "reqVo", value = "", required = true)
    })
    @ApiOperation(value = "更新用户角色信息", notes = "更新用户角色信息", httpMethod = "PUT")
    @PutMapping("role")
    public R updatePermsByRoleId(@RequestBody UpdatePermsByRoleIdReqVo reqVo){
        return roleService.updatePermsByRoleId(reqVo);
    }

    /**
     * 根据用户id删除用户
     * @author dlwlrma
     * @date 2024/7/21 20:58
     * @param roleId
     * @return com.lcyy.stock.vo.resp.R
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "string", name = "roleId", value = "", required = true)
    })
    @ApiOperation(value = "根据用户id删除用户", notes = "根据用户id删除用户", httpMethod = "DELETE")
    @DeleteMapping("/role/{roleId}")
    public R deleteRoles(@PathVariable String roleId){
        return roleService.deleteRoles(roleId);
    }

    /**
     * 更新用户的状态信息
     * @author dlwlrma
     * @date 2024/7/21 21:16
     * @param roleId
     * @param status
     * @return com.lcyy.stock.vo.resp.R
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "string", name = "roleId", value = "", required = true),
            @ApiImplicitParam(paramType = "path", dataType = "int", name = "status", value = "", required = true)
    })
    @ApiOperation(value = "更新用户的状态信息", notes = "更新用户的状态信息", httpMethod = "POST")
    @PostMapping("/role/{roleId}/{status}")
    public R updateRoleStatus(@PathVariable String roleId, @PathVariable Integer status){
        return roleService.updateRoleStatus(roleId,status);
    }
}
