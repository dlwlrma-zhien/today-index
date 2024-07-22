package com.lcyy.stock.execption;

/**
 * @author: dlwlrma
 * @data 2024年07月22日 13:46
 * @Description: TODO:定义业务类异常
 */
public class BusinessException extends RuntimeException{
    /**
     * 错误码 项目中一般使用约定好的枚举类
     */
    private int code;
    /**
     * 错误信息
     */
    private String message;

    public BusinessException(String message) {
        super(message);
        this.message = message;
    }

    public BusinessException(String message, int code) {
        super(message);
        this.code=code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
