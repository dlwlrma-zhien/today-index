package com.lcyy.stock.service.impl;

import com.lcyy.stock.mapper.SysUserMapper;
import com.lcyy.stock.pojo.entity.SysUser;
import com.lcyy.stock.service.UserService;
import com.lcyy.stock.vo.req.LoginReqVo;
import com.lcyy.stock.vo.resp.LoginRespVo;
import com.lcyy.stock.vo.resp.R;
import com.lcyy.stock.vo.resp.ResponseCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author: dlwlrma
 * @data 2024年06月19日 16:29
 * @Description: TODO: 定义用户实现服务
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    /**
     *根据用户名查询用户信息，三层架构：service层调用mapper接口层
     * @author dlwlrma
     * @date 2024/6/19 16:32
     * @param null
     * @return null
     */
    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public SysUser findByUserName(String userName) {
        return sysUserMapper.findUserInfoByUserName(userName);
    }

    /**
     * TODO:用户登录功能 StringUtils来源于依赖导入的资源isBlank是判断用户输入的各个字段的值是否为空，返回值为布尔类型
     *
     * @author dlwlrma
     * @date 2024/6/19 20:27
     * @param vo
     * @return com.lcyy.stock.vo.resp.R<com.lcyy.stock.vo.resp.LoginRespVo>
     */
    @Override
    public R<LoginRespVo> login(LoginReqVo vo) {
        //1.判断参数是否合法
        if (vo==null || StringUtils.isBlank(vo.getUsername()) || StringUtils.isBlank(vo.getPassword()) || StringUtils.isBlank(vo.getCode())) {
            return R.error(ResponseCode.DATA_ERROR);
        }
        //2.根据用户名去数据库查询用户信息，获取密码的密文
        SysUser dbUser = sysUserMapper.findUserInfoByUserName(vo.getUsername());
        if (dbUser == null){
            return R.error(ResponseCode.ACCOUNT_NOT_EXISTS);
        }
        //3.根据获取到的密文利用密码匹配器匹配用户输入的名文是否一致
        if (!passwordEncoder.matches(vo.getPassword(),dbUser.getPassword())) {
            return R.error(ResponseCode.USERNAME_OR_PASSWORD_ERROR);
        }
        //4.响应给前端
        LoginRespVo respVo = new LoginRespVo();
//        respVo.setUsername(dbUser.getUsername());
//        respVo.setNickName(dbUser.getNickName());
//        respVo.setId(dbUser.getId());
//        respVo.setPhone(dbUser.getPhone());
        //发现：LoginRespVo 与 SysUser 的对象属性和类型名一致，便可以用spring底层提供的BeanUtils工具类将目标资源复制到当前对象
        //必须保证：属性名称与类型必须相同，否则属性值无法copy
        BeanUtils.copyProperties(dbUser,respVo);
        return R.ok(respVo);
    }
}
