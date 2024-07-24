package com.lcyy.stock.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lcyy.stock.constant.StockConstant;
import com.lcyy.stock.security.user.LoginUserDetail;
import com.lcyy.stock.security.utils.JwtTokenUtil;
import com.lcyy.stock.vo.req.LoginReqVo;
import com.lcyy.stock.vo.resp.R;
import com.lcyy.stock.vo.resp.ResponseCode;
import com.lcyy.stock.vo.resp.accessTokenLoginRespVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author by itheima
 * @Date 2022/7/14
 * @Description
 */
public class JwtLoginAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private RedisTemplate redisTemplate;

    /**
     * 通过setter方法注解redis模板对象
     * @param redisTemplate
     */
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 通过构造器传入自定义的登录地址
     * @param loginUrl
     */
    public JwtLoginAuthenticationFilter(String loginUrl) {
        super(loginUrl);
    }

    /**
     * 用户认证处理的方法
     * @param request
     * @param response
     * @return
     * @throws AuthenticationException
     * @throws IOException
     * @throws ServletException
     * 我们约定请求方式必须是post方式，且请求的数据时json格式
     *              约定请求是账户key：username  密码：password
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
//        //判断请求方法必须是post提交，且提交的数据的内容必须是application/json格式的数据
//        if (!request.getMethod().equals("POST") ||
//                ! (request.getContentType().equalsIgnoreCase(MediaType.APPLICATION_JSON_VALUE) || request.getContentType().equalsIgnoreCase(MediaType.APPLICATION_JSON_UTF8_VALUE))) {
//            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
//        }
//        //获取请求参数
//        //获取reqeust请求对象的发送过来的数据流
//        ServletInputStream in = request.getInputStream();
//        //将数据流中的数据反序列化成Map
//        LoginReqVo vo = new ObjectMapper().readValue(in, LoginReqVo.class);
//        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//        response.setCharacterEncoding("utf-8");
//        //1.判断参数是否合法
//        if (vo==null || StringUtils.isBlank(vo.getUsername())
//                || StringUtils.isBlank(vo.getPassword())
//                || StringUtils.isBlank(vo.getSessionId()) || StringUtils.isBlank(vo.getCode())) {
//            R<Object> resp = R.error(ResponseCode.USERNAME_OR_PASSWORD_ERROR.getMessage());
//            response.getWriter().write(new ObjectMapper().writeValueAsString(resp));
//            return null;
//        }
//        //从程序执行的效率看，先进行校验码的校验，成本较低
//        String rCheckCode =(String) redisTemplate.opsForValue().get(StockConstant.CHECK_PREFIX + vo.getSessionId());
//        if (rCheckCode==null || ! rCheckCode.equalsIgnoreCase(vo.getCode())) {
//            //响应验证码输入错误
//            R<Object> resp = R.error(ResponseCode.CHECK_CODE_ERROR.getMessage());
//            response.getWriter().write(new ObjectMapper().writeValueAsString(resp));
//            return null;
//        }
//        String username = vo.getUsername();
//        //username = (username != null) ? username : "";
//        username = username.trim();
//        String password = vo.getPassword();
//        //password = (password != null) ? password : "";
//        //将用户名和密码信息封装到认证票据对象下
//        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
//        // Allow subclasses to set the "details" property
//        //setDetails(request, authRequest);
//        //调用认证管理器认证指定的票据对象
//        return this.getAuthenticationManager().authenticate(authRequest);

        //判断请求方法必须是post提交，且提交的数据的内容必须是application/json格式的数据
        if (!request.getMethod().equals("POST") ||
                ! (request.getContentType().equalsIgnoreCase(MediaType.APPLICATION_JSON_VALUE) || request.getContentType().equalsIgnoreCase(MediaType.APPLICATION_JSON_UTF8_VALUE))) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        //获取请求参数
        //获取reqeust请求对象的发送过来的数据流
        ServletInputStream in = request.getInputStream();
        //将数据流中的数据反序列化成Map
        LoginReqVo vo = new ObjectMapper().readValue(in, LoginReqVo.class);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");
        //1.判断参数是否合法
        if (vo==null || StringUtils.isBlank(vo.getUsername())
                || StringUtils.isBlank(vo.getPassword())
                || StringUtils.isBlank(vo.getSessionId()) || StringUtils.isBlank(vo.getCode())) {
            R<Object> resp = R.error(ResponseCode.USERNAME_OR_PASSWORD_ERROR.getMessage());
            response.getWriter().write(new ObjectMapper().writeValueAsString(resp));
            return null;
        }
        //从程序执行的效率看，先进行校验码的校验，成本较低
        String rCheckCode =(String) redisTemplate.opsForValue().get(StockConstant.CHECK_PREFIX + vo.getSessionId());
        if (rCheckCode==null || ! rCheckCode.equalsIgnoreCase(vo.getCode())) {
            //响应验证码输入错误
            R<Object> resp = R.error(ResponseCode.CHECK_CODE_ERROR.getMessage());
            response.getWriter().write(new ObjectMapper().writeValueAsString(resp));
            return null;
        }
        String username = vo.getUsername();
        //username = (username != null) ? username : "";
        username = username.trim();
        String password = vo.getPassword();
        //password = (password != null) ? password : "";
        //将用户名和密码信息封装到认证票据对象下
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
        // Allow subclasses to set the "details" property
        //setDetails(request, authRequest);
        //调用认证管理器认证指定的票据对象
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    /**
     * 用户认证成功后回调的方法
     * 认证成功后，响应前端token信息
     * @param request
     * @param response
     * @param chain security的过滤器链
     * @param authResult
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        //获取用户的详情信息
        LoginUserDetail userDetail = (LoginUserDetail) authResult.getPrincipal();
        //组装LoginRespVoExt
        //获取用户名称
        String username = userDetail.getUsername();
        //获取权限集合对象
        List<GrantedAuthority> authorities = userDetail.getAuthorities();
        String auStrList = authorities.toString();
        //复制userDetail属性值到LoginRespVoExt对象即可
        accessTokenLoginRespVo resp = new accessTokenLoginRespVo();
        BeanUtils.copyProperties(userDetail,resp);
        //生成token字符串:将用户名称和权限信息价格生成token字符串
        String tokenStr = JwtTokenUtil.createToken(username, auStrList);
        resp.setAccessToken(tokenStr);
        //封装统一响应结果
        R<accessTokenLoginRespVo> r = R.ok(resp);
        String respStr = new ObjectMapper().writeValueAsString(r);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(respStr);
    }

    /**
     * 认证失败后，回调的方法
     * @param request
     * @param response
     * @param failed
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        R<Object> r = R.error(ResponseCode.USERNAME_OR_PASSWORD_ERROR);
        String respStr = new ObjectMapper().writeValueAsString(r);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(respStr);
    }
}