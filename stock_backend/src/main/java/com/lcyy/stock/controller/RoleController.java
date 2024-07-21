package com.lcyy.stock.controller;

import com.lcyy.stock.pojo.domain.MenusPermDomain;
import com.lcyy.stock.pojo.entity.SysRole;
import com.lcyy.stock.service.RoleService;
import com.lcyy.stock.vo.req.AddRolesAndPermsReqVo;
import com.lcyy.stock.vo.resp.PageResult;
import com.lcyy.stock.vo.resp.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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

    @PostMapping("role")
    public R addRolesAndPerms(@RequestBody AddRolesAndPermsReqVo reqVo){
        return roleService.addRolesAndPerms(reqVo);
    }
}
