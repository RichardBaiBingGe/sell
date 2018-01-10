package com.core.enums;

import lombok.Getter;

/**
 * 商品状态
 * Created by Richard on 2017/12/4.
 */
@Getter
public enum ProductStatusEnums {

    UP(0,"上架"),
    DOWN(1,"下架");

    private Integer code;
    private String message;

    ProductStatusEnums(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
