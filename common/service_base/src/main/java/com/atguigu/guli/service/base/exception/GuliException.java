package com.atguigu.guli.service.base.exception;

import com.atguigu.guli.common.base.result.ResultCodeEnum;
import lombok.Data;

@Data
public class GuliException extends RuntimeException {//这里一定要是非检查性异常 RuntimeException

    //状态码
    private Integer code;

    /**
     * 接受状态码和消息
     *
     * @param code
     * @param message
     */
    public GuliException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    /**
     * 接收枚举类型
     *
     * @param resultCodeEnum
     */
    public GuliException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
    }

    @Override
    public String toString() {
        return "GuliException{" +
                "code=" + code +
                "," +
                "message=" + getMessage() +
                '}';
    }
}