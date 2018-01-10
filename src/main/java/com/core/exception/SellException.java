package com.core.exception;

import com.core.enums.ResultEnums;

/**
 * Created by Richard on 2017/12/7.
 */
public class SellException extends RuntimeException {

    private Integer code;

    public SellException(ResultEnums resultEnums) {
        super(resultEnums.getMessage());
        this.code = resultEnums.getCode();
    }

    public SellException(Integer code,String message) {
        super(message);
        this.code=code;
    }
}
