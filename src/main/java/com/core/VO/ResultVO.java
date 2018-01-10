package com.core.VO;

import lombok.Data;

/**
 * Created by Richard on 2017/12/4.
 */
@Data
public class ResultVO<T> {

    //错误码
    private Integer code;
    //提示信息
    private String Message;
    //返回内容
    private T Date;
}
