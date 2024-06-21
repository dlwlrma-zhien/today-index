package com.lcyy.stock.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import com.lcyy.stock.constant.StockConstant;
import com.lcyy.stock.mapper.SysUserMapper;
import com.lcyy.stock.pojo.entity.SysUser;
import com.lcyy.stock.service.UserService;
import com.lcyy.stock.utils.IdWorker;
import com.lcyy.stock.vo.req.LoginReqVo;
import com.lcyy.stock.vo.resp.LoginRespVo;
import com.lcyy.stock.vo.resp.R;
import com.lcyy.stock.vo.resp.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author: dlwlrma
 * @data 2024年06月19日 16:29
 * @Description: TODO: 定义用户实现服务
 */
@Service("userService")
@Slf4j
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

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private RedisTemplate redisTemplate;

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
        if (vo==null || StringUtils.isBlank(vo.getUsername()) || StringUtils.isBlank(vo.getPassword())) {
            return R.error(ResponseCode.DATA_ERROR);
        }
        //判断输入的验证码是否为空
        if(StringUtils.isBlank(vo.getCode()) || StringUtils.isBlank(vo.getSessionId())){
            return R.error(ResponseCode.CHECK_CODE_ERROR);
        }
        //判断redis中的验证码与用户输入的验证码是否相同（忽略大小写）
        String redisCode = (String)redisTemplate.opsForValue().get(StockConstant.CHECK_PREFIX + vo.getSessionId());
        //会话id存在，但redis中不存在，说明已经过期了
        if(StringUtils.isBlank(redisCode)){
            return R.error(ResponseCode.CHECK_CODE_TIMEOUT);
        }
        //验证码错误
        if(!redisCode.equalsIgnoreCase(vo.getCode())){
            return R.error(ResponseCode.CHECK_CODE_ERROR);
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

    /**
     * TODO: 实现图片验证码功能
     * @author dlwlrma
     * @date 2024/6/20 15:48
     * @return com.lcyy.stock.vo.resp.R<java.util.Map>
     */
    @Override
    public R<Map> getCaptchaCode() {
        //1.生成图片验证码,参数1：图片宽度，参数二:高度，参数三：图片中包含的验证码长度，参数四:干扰线数量
        LineCaptcha captcha = CaptchaUtil.createLineCaptcha(250, 40, 4, 5);
        captcha.setBackground(Color.LIGHT_GRAY);

        //获取校验码
        String checkCode = captcha.getCode();
        String imageData = captcha.getImageBase64();

        //2.生成sessionID,转换成string类型，防止精度丢失
        String sessionId = String.valueOf(idWorker.nextId());
        //方便调试
        log.info("当前生成的图片校验码：{}，会话Id：{}",checkCode,sessionId);

        //3.后台将sessionId作为key，检验码作为value，保存在redis中，用redis模拟session行为，设置过期时间
        //方便后期维护加上的CK为业务前缀，便于统计后期有多少人访问
        redisTemplate.opsForValue().set(StockConstant.CHECK_PREFIX +sessionId,checkCode,5, TimeUnit.MINUTES);

        //4.组装数据,放到map集合中去
        Map<String, String> data = new HashMap<>();
        data.put("imageData",imageData);
        data.put("sessionId",sessionId);

        //5.返回给前端
        return R.ok(data);
    }
}
