package com.core.enums;

import lombok.Getter;

/**
 * Created by Richard on 2017/12/7.
 */
@Getter
public enum PayStatusEnums {

    WAIT(0,"未支付"),
    SUCCESS(1,"完成支付");

    private Integer code;
    private String Message;

    PayStatusEnums(Integer code, String message) {
        this.code = code;
        Message = message;
    }
}
