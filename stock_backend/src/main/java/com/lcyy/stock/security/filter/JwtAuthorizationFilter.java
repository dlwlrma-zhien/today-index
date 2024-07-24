package com.lcyy.stock.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lcyy.stock.constant.StockConstant;
import com.lcyy.stock.security.utils.JwtTokenUtil;
import com.lcyy.stock.vo.resp.R;
import com.lcyy.stock.vo.resp.ResponseCode;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author by itheima
 * @Date 2021/12/26
 * @Description 自定义鉴权过滤器
 */
public class JwtAuthorizationFilter extends OncePerRequestFilter {
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
//        //判断是否已经有token
//        //获取token
//        String tokenStr = request.getHeader(JwtTokenUtil.TOKEN_HEADER);
//        // 若请求头中没有Authorization信息 则直接放行
//        if (tokenStr == null || tokenStr.trim() == ""){
//            //放行到的登录页面登录操作
//            chain.doFilter(request,response);
//            //停止当前逻辑判断
//            return;
//        }
//        // 从Token中解密获取用户名
//        String username = JwtTokenUtil.getUsername(tokenStr);
//        // 从Token中解密获取用户角色和权限集合字符串
//        String role = JwtTokenUtil.getUserRole(tokenStr);
//        //获取以逗号间隔的权限字符串
//        String rolesNames = StringUtils.strip(role, "[]");
//        //获取权限集合
//        List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(rolesNames);
//        if (username== null){
//            throw new RuntimeException("token无效");
//        }
//        //生成授权token
//        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, null, authorities);
//        //认证token对象存入security上下文中,其它security过滤器都可以直接在上下文对象中获取该token
//        SecurityContextHolder.getContext().setAuthentication(token);
//        chain.doFilter(request,response);

    /**
     * 访问过滤的方法
     * @param request
     * @param response
     * @param filterChain 过滤器链
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        //1.从request对象下获取token数据，约定key：Authorization
        String tokenStr = request.getHeader(StockConstant.TOKEN_HEADER);
        //判断token字符串是否存在
        if (tokenStr==null) {
            //如果票据为null，可能用户还没有认证，正准备去认证,所以放行请求
            //放行后，会不会访问当受保护的资源呢？不会，因为没有生成UsernamePassowrdAuthentionToken
            filterChain.doFilter(request,response);
            return;
        }
        //2.解析tokenStr,获取用户详情信息
        Claims claims = JwtTokenUtil.checkJWT(tokenStr);
        //token字符串失效的情况
        if (claims==null) {
            //说明 票据解析出现异常，票据就失效了
            R<Object> r = R.error(ResponseCode.INVALID_TOKEN);
            String respStr = new ObjectMapper().writeValueAsString(r);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(respStr);
            return;
        }
        //获取用户名和权限信息
        String userName = JwtTokenUtil.getUsername(tokenStr);
        //生成token时，权限字符串的格式是：[P8,ROLE_ADMIN]
        String perms = JwtTokenUtil.getUserRole(tokenStr);
        //生成权限集合对象
        //P8,ROLE_ADMIN
        String strip = StringUtils.strip(perms, "[]");
        List<GrantedAuthority> authorityList = AuthorityUtils.commaSeparatedStringToAuthorityList(strip);
        //将用户名和权限信息封装到token对象下
        UsernamePasswordAuthenticationToken token=new UsernamePasswordAuthenticationToken(userName,null,authorityList);
        //将token对象存入安全上限文，这样，线程无论走到哪里，都可以获取token对象，验证当前用户访问对应资源是否被授权
        SecurityContextHolder.getContext().setAuthentication(token);
        //资源发行
        filterChain.doFilter(request,response);
    }
}
