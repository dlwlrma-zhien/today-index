package com.lcyy.stock.security.config;

import com.lcyy.stock.security.filter.JwtAuthorizationFilter;
import com.lcyy.stock.security.filter.JwtLoginAuthenticationFilter;
import com.lcyy.stock.security.handler.AccessAnonymousEntryPoint;
import com.lcyy.stock.security.handler.StockAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author by itheima
 * @Date 2021/12/24
 * @Description
 */
@Configuration
@EnableWebSecurity//开启web安全
@EnableGlobalMethodSecurity(prePostEnabled=true)//开启SpringSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private RedisTemplate redisTemplate;

    private String[] getPubPath(){
        //公共访问资源
        String[] urls = {
                "/**/*.css","/**/*.js","/favicon.ico",
                "/druid/**","/webjars/**","/v2/api-docs","/api/captcha",
                "/swagger/**","/swagger-resources/**","/swagger-ui.html"
        };
        return urls;
    }

    /**
     *http安全配置
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //设置登录规则
        http.formLogin().permitAll();
        //登出功能,指定的路径，invalidateHttpSession使session失效
        http.logout().logoutUrl("/api/logout").invalidateHttpSession(true);
        //允许跨域共享
        http.csrf().disable().cors();
        //开启允许iframe 嵌套。security默认禁用firam跨域与缓存
        http.headers().frameOptions().disable().cacheControl().disable();
        //session禁用（固定写法）
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        //授权策略
        http.authorizeRequests()//设置需要认证才能访问的请求
                .antMatchers(getPubPath()).permitAll()
                .anyRequest()
                .authenticated();//其他所有请求都需要认证
        //认证和授权设置(登录认证和授权检查)
        http
                //自定义的登录认证过滤器在内置认证过滤之前，这样认证生成token对象后，就不会走默认认证过滤器
                .addFilterBefore(jwtCustomLoginFilter(),UsernamePasswordAuthenticationFilter.class)
                //授权过滤要在登录认证过滤之前，保证认证通过的资源无需经过登录认证过滤器
                .addFilterBefore(jwtCustomAuthorizationFilter(),JwtLoginAuthenticationFilter.class);
        //注册匿名访问拒绝处理器和认证失败处理器
        http.exceptionHandling()
                //未经过认证的匿名用户处理
                .authenticationEntryPoint(new AccessAnonymousEntryPoint())
                //权限不足的处理
                .accessDeniedHandler(new StockAccessDeniedHandler());
    }

    /**
     * 定义加密方式
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * 登录认证拦截过滤器bean
     * @return
     */
    @Bean
    public JwtLoginAuthenticationFilter jwtCustomLoginFilter() throws Exception {
        //指定登录路径
        JwtLoginAuthenticationFilter jwtCustomLoginFilter = new JwtLoginAuthenticationFilter("/api/login");
        //注册认证管理器
        jwtCustomLoginFilter.setAuthenticationManager(authenticationManagerBean());
        //注入redis模板对象
        jwtCustomLoginFilter.setRedisTemplate(redisTemplate);
        return jwtCustomLoginFilter;
    }

    /**
     * 自定义授权过滤器bean
     * @return
     */
    @Bean
    public JwtAuthorizationFilter jwtCustomAuthorizationFilter(){
        JwtAuthorizationFilter jwtCustomAuthorizationFilter = new JwtAuthorizationFilter();
        return jwtCustomAuthorizationFilter;
    }

}
