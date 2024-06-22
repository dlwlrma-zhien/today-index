package com.lcyy.stock;

/**
 * @author: dlwlrma
 * @data 2024年06月22日 15:01
 * @Description: TODO: 测试分页
 */

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcyy.stock.mapper.SysUserMapper;
import com.lcyy.stock.pojo.entity.SysUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class TestPageHelper {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Test
    public void test01(){
        //当前页
        Integer page = 2;
        //当前页大小
        Integer PageSize = 5;
        PageHelper.startPage(page,PageSize);
        List<SysUser> all1 = sysUserMapper.findAll();
        //将查询到的all1对象封装到pageInfo中，可以查看分页的各种数据
        PageInfo<SysUser> pageInfo = new PageInfo<>(all1);
        int pages = pageInfo.getPages();
        int pageSize = pageInfo.getPageSize();
        int size = pageInfo.getSize();
        System.out.println(pages+"------"+pageSize+"----"+size);
        List<SysUser> all = all1;
        System.out.println(all);
    }

}
