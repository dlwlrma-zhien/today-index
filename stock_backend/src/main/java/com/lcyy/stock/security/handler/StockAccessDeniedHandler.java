package com.lcyy.stock.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.lcyy.stock.vo.resp.R;
import com.lcyy.stock.vo.resp.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证用户无权限访问资源时处理器
 */
@Slf4j
public class StockAccessDeniedHandler implements AccessDeniedHandler {

//    @Override
//    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
//        //设置响应数据格式
//        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
//        //构建结果
//        R result = R.error(ResponseCode.NOT_PERMISSION.getCode(),ResponseCode.NOT_PERMISSION.getMessage());
//        //将对象序列化为json字符串响应前台
//        response.getWriter().write(new Gson().toJson(result));

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException ex) throws IOException, ServletException {
        log.info("访问拒绝，异常信息：{}",ex.getMessage());
        //说明 票据解析出现异常，票据就失效了
        R<Object> r = R.error(ResponseCode.NOT_PERMISSION);
        String respStr = new ObjectMapper().writeValueAsString(r);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(respStr);
    }
}
