package com.lcyy.stock.pojo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
/**
 * 测试EasyExcel
 * @author dlwlrma
 * @date 2024/6/23 19:25
 * @return null
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements Serializable {
    @ExcelProperty(value ={"用户信息", "姓名"},index = 0)
    private String userName;
    @ExcelProperty(value = {"用户信息","年龄"},index = 1)
    private Integer age;
    @ExcelProperty(value = {"用户信息","地址"},index = 2)
    private String address;
    @ExcelProperty(value ={"用户信息", "生日"},index = 3)
    //该注解是Excel的注解，不是Springmvc的注解
    @DateTimeFormat("yyyy-MM-dd")
    private Date birthday;
}