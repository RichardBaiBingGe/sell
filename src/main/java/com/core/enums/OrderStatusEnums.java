package com.core.enums;

import lombok.Getter;

/**
 * Created by Richard on 2017/12/6.
 */
@Getter
public enum OrderStatusEnums {

    NEW(0,"新订单"),
    FINISHED(1,"完成"),
    CANCLE(2,"取消")
    ;

    private Integer code;

    private String message;

    OrderStatusEnums(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
