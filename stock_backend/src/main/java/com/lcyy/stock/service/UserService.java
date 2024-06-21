package com.lcyy.stock.service;

import com.lcyy.stock.pojo.entity.SysUser;
import com.lcyy.stock.vo.req.LoginReqVo;
import com.lcyy.stock.vo.resp.LoginRespVo;
import com.lcyy.stock.vo.resp.R;

import java.util.Map;

/**
 * @author: dlwlrma
 * @data 2024年06月19日 16:26
 */
public interface UserService {
    /**
     * 根据用户名称查询用户信息
     *
     * @param userName
     * @return java.lang.System
     * @author dlwlrma
     * @date 2024/6/19 16:28
     */
    SysUser findByUserName(String userName);

    /**
     * todo:用户登陆功能
     * @author dlwlrma
     * @date 2024/6/19 20:26
     * @param vo
     * @return com.lcyy.stock.vo.resp.R<com.lcyy.stock.vo.resp.LoginRespVo>
     */
    R<LoginRespVo> login(LoginReqVo vo);

    /**
     * TODO: 实现图片验证码功能
     * @author dlwlrma
     * @date 2024/6/20 15:49
     * @return com.lcyy.stock.vo.resp.R<java.util.Map>
     */
    R<Map> getCaptchaCode();
}
