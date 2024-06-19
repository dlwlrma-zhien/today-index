package com.lcyy.stock;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author: dlwlrma
 * @data 2024年06月19日 18:41
 * @Description: TODO: 测试类
 */
@SpringBootTest
public class TestPasswordEncoder {

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * TODO：测试明文加密为密文，在数据库中存储的就是密文
     * @author dlwlrma
     * @date 2024/6/19 18:45
     */
    @Test
    public void test01(){
        String pwd = "123456";
        String encode = passwordEncoder.encode(pwd);
        //输出：encode = $2a$10$6ooKnkWRHtrLbHEl8z.z9uDcvuxE1C7nIC4V8b6XNDmMf474Y7PR2
        System.out.println("encode = " + encode);
    }

    /**
     * TODO：测试密码是否可以匹配
     * @author dlwlrma
     * @date 2024/6/19 18:47
     */
    @Test
    public void test02(){
        String pwd = "123456";
        String enPwd = "$2a$10$6ooKnkWRHtrLbHEl8z.z9uDcvuxE1C7nIC4V8b6XNDmMf474Y7PR2";
        //使用matches将明文与密文进行匹配验证
        boolean isSuccess = passwordEncoder.matches(pwd, enPwd);
        System.out.println(isSuccess?"密码匹配成功":"密码匹配失败");
    }
}
