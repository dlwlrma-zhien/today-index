package com.lcyy.stock.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.lcyy.stock.vo.resp.R;
import com.lcyy.stock.vo.resp.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 匿名用户(即未登录时访问资源为匿名访问)无权限处理器
 */
@Slf4j
public class AccessAnonymousEntryPoint implements AuthenticationEntryPoint {

    /**
     * 当用户请求了一个受保护的资源，但是用户没有通过认证，那么抛出异常，
     * AuthenticationEntryPoint. Commence(..)就会被调用。
     * @param request
     * @param response
     * @param authException
     * @throws IOException
     * @throws ServletException
     */
//    @Override
//    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
//        //设置响应数据格式
//        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
//        //构建结果
//        R result = R.error(ResponseCode.NOT_PERMISSION.getCode(),ResponseCode.NOT_PERMISSION.getMessage());
//        //将对象序列化为json字符串响应前台
//        response.getWriter().write(new Gson().toJson(result));

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        log.info("访问拒绝，异常信息：{}",authException.getMessage());
        //说明 票据解析出现异常，票据就失效了
        R<Object> r = R.error(ResponseCode.ANONMOUSE_NOT_PERMISSION);
        String respStr = new ObjectMapper().writeValueAsString(r);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(respStr);
    }
}
