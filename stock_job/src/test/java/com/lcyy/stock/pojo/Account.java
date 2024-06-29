package com.lcyy.stock.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.InvocationHandler;

/**
 * 测试数据封装实体类
 * @author dlwlrma
 * @date 2024/6/29 15:56
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account{

    private Integer id;

    private String userName;

    private String address;

}
