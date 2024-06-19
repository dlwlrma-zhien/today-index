package com.lcyy.stock.vo.resp;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: dlwlrma
 * @data 2024年06月19日 18:18
 * @Description: TODO: 登陆响应给前端
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRespVo {

    /**
     * TODO：若将long类型的数据传递给前端，浏览器解析会出现精度是真的问题（例如：若是19位的id,则在前端收到时只会有16位）因此使用
     *       Json底层的注解，即 @JsonSerialize(using = ToStringSerializer.class)
     *       将long类型的id转换为string类型的数据就不会出现精度是真
     * @author dlwlrma
     * @date 2024/6/19 18:21
     * @param null
     * @return null
     */
    //用户id
    @JsonSerialize(using = ToStringSerializer.class)
    private long id;

    //用户名称
    private String username;

    //用户昵称
    private String nickName;

    //用户手机号
    private String phone;
}
