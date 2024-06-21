package com.lcyy.stock.controller;

import com.lcyy.stock.pojo.entity.SysUser;
import com.lcyy.stock.service.UserService;
import com.lcyy.stock.vo.req.LoginReqVo;
import com.lcyy.stock.vo.resp.LoginRespVo;
import com.lcyy.stock.vo.resp.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author: dlwlrma
 * @data 2024年06月19日 16:34
 * @Description: TODO: 定义用户web层接口管理bean
 * @ResponseBody:将实体对象转换位json格式字符串响应给前端：为序列化过程
 * @RequestBodty:将json格式字符串转换为实体对象：为反序列化过程
 */
@RestController
@RequestMapping("/api")
@Api(tags = "用户相关接口处理器")
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
    @GetMapping("/user/{userName}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name",value = "用户名",type = "String",required = true,paramType = "path")
    })
    @ApiOperation(value = "根据用户名查询用户信息")
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
    @ApiOperation(value = "用户登录功能")
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
    @ApiOperation(value = "生成图片验证码")
    @GetMapping("/captcha")
    public R<Map> getCaptchaCode(){
        return userService.getCaptchaCode();
    }

}
