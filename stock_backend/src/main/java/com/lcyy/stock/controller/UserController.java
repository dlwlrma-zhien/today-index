package com.lcyy.stock.controller;

import com.lcyy.stock.pojo.domain.SysUserDomain;
import com.lcyy.stock.pojo.entity.SysUser;
import com.lcyy.stock.service.UserService;
import com.lcyy.stock.vo.req.*;
import com.lcyy.stock.vo.resp.LoginRespVo;
import com.lcyy.stock.vo.resp.PageResult;
import com.lcyy.stock.vo.resp.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author: dlwlrma
 * @data 2024年06月19日 16:34
 * @Description: TODO: 定义用户web层接口管理bean
 * @ResponseBody: 将实体对象转换位json格式字符串响应给前端：为序列化过程
 * @RequestBodty: 将json格式字符串转换为实体对象：为反序列化过程
 */
@Api(value = "/api", tags = {": TODO: 定义用户web层接口管理bean"})
@RestController
@RequestMapping("/api")
public class UserController {

    /**
     *三层架构：controller层调用service层
     * @author dlwlrma
     * @date 2024/6/19 16:46
     * @param null
     * @return null
     */
    @Autowired
    private UserService userService;


    /**
     *根据用户名称查询用户信息 {userName}：表示可能有多个参数，@PathVariable 为参数路径说明
     * @author dlwlrma
     * @date 2024/6/19 16:43
     * @param userName
     * @return com.lcyy.stock.pojo.entity.SysUser
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "string", name = "userName", value = "", required = true)
    })
    @ApiOperation(value = "根据用户名查询用户信息", notes = "根据用户名称查询用户信息 {userName}：表示可能有多个参数，@PathVariable 为参数路径说明", httpMethod = "GET")
    @GetMapping("/user/{userName}")
    public SysUser getUserByUserName(@PathVariable String userName){
        return userService.findByUserName(userName);
    }

    /**
     * TODO： 用户登录功能，用泛型将response封装到R中，@RequestBody 是将前端传来的json格式转换为实体对象
     * @author dlwlrma
     * @date 2024/6/19 20:23
     * @param vo
     * @return com.lcyy.stock.vo.resp.R<com.lcyy.stock.vo.resp.LoginRespVo>
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "LoginReqVo", name = "vo", value = "", required = true)
    })
    @ApiOperation(value = "用户登录功能", notes = "TODO： 用户登录功能，用泛型将response封装到R中，@RequestBody 是将前端传来的json格式转换为实体对象", httpMethod = "POST")
    @PostMapping("/login")
    public R<LoginRespVo> login(@RequestBody LoginReqVo vo){
        return userService.login(vo);
    }

    /**
     * TODO: 生成图片验证码功能
     * @author dlwlrma
     * @date 2024/6/20 15:47
     * @return com.lcyy.stock.vo.resp.R<java.util.Map>
     */
    @ApiOperation(value = "生成图片验证码", notes = "TODO: 生成图片验证码功能", httpMethod = "GET")
    @GetMapping("/captcha")
    public R<Map> getCaptchaCode(){
        return userService.getCaptchaCode();
    }

    /**
     * 多条件综合查询分页信息，条件包含：分页信息，用户创建日期范围
     * @author dlwlrma
     * @date 2024/7/12 12:20
     * @param reqvo
     * @return com.lcyy.stock.vo.resp.R<com.lcyy.stock.vo.resp.PageResult<com.lcyy.stock.pojo.domain.SysUserDomain>>
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "UserMconditionReqvo", name = "reqvo", value = "", required = true)
    })
    @ApiOperation(value = "多条件综合查询分页信息，条件包含：分页信息，用户创建日期范围", notes = "多条件综合查询分页信息，条件包含：分页信息，用户创建日期范围", httpMethod = "POST")
    @PreAuthorize("hasAuthority('sys:user:list')")
    @PostMapping("/users")
    public R<PageResult<SysUserDomain>> getUsersInfoByMCondition(@RequestBody UserMconditionReqvo reqvo){
        return userService.getUsersInfoByMCondition(reqvo);
    }

    /**
     * 添加用户
     * @author dlwlrma
     * @date 2024/7/12 14:16
     * @param reqVo
     * @return com.lcyy.stock.vo.resp.R
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "AddUserReqVo", name = "reqVo", value = "", required = true)
    })
    @ApiOperation(value = "添加用户", notes = "添加用户", httpMethod = "POST")
    @PostMapping("/user")
    public R addUser(@RequestBody AddUserReqVo reqVo){
        return userService.AddUserReqVo(reqVo);
    }

    /**
     * 更新用户角色信息
     * @author dlwlrma
     * @date 2024/7/12 15:16
     * @param reqVo
     * @return com.lcyy.stock.vo.resp.R
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "UpdateUserRoleReqVo", name = "reqVo", value = "", required = true)
    })
    @ApiOperation(value = "更新用户角色信息", notes = "更新用户角色信息", httpMethod = "PUT")
    @PutMapping("/user/roles")
    public R updateUserRole(@RequestBody UpdateUserRoleReqVo reqVo){
        return userService.updateUserRole(reqVo);
    }

    /**
     * 更新用户信息
     * @author dlwlrma
     * @date 2024/7/12 15:50
     * @return com.lcyy.stock.vo.resp.R
     */
    @PutMapping("/user")
    public R updateUser(@RequestBody UpdateUserInfoReqVo reqVo){
        return userService.updateUser(reqVo);
    }

}
