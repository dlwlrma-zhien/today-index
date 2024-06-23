package com.lcyy.stock;

import com.alibaba.excel.EasyExcel;
import com.lcyy.stock.pojo.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: dlwlrma
 * @data 2024年06月23日 19:24
 * @Description: TODO: 测试
 */
public class TestEasyExcel {
    public List<User> init(){
        //组装数据
        ArrayList<User> users = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setAddress("上海"+i);
            user.setUserName("张三"+i);
            user.setBirthday(new Date());
            user.setAge(10+i);
            users.add(user);
        }
        return users;
    }

    @Test
    public void test02(){
        List<User> users = init();
        //不做任何注解处理时，表头名称与实体类属性名称一致
        EasyExcel.write("D:\\Java\\idea\\项目一：今日指数\\data\\test.xls",User.class).sheet("用户信息").doWrite(users);
    }
}
