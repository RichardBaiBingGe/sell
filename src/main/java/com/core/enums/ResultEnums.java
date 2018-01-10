package com.core.enums;

import lombok.Getter;

/**
 * Created by Richard on 2017/12/7.
 */
@Getter
public enum ResultEnums {

    PARAM_ERROR(1,"参数不正确"),
    PRODUCT_NOT_EXIST(10,"商品不存在"),
    PRODUCT_STOCK_ERROR(11,"库存不正确"),
    ORDER_NOT_EXIST(12,"订单不存在"),
    ORDERDETAIL_NOT_EXIST(13,"订单详情不存在"),
    ORDER_STATUS_ERROR(14,"订单状态不正确"),
    ORDER_UPDATE_FAIL(15,"订单更新失败"),
    ORDER_DETAIL_EMPTY(16,"订单详情为空"),
    ORDER_PAY_STATUS_ERROR(17,"订单支付状态不正确"),
    CART_EMPTY(18,"购物车不能为空"),
    ORDER_OWNER_ERROR(19,"该订单不属于当前用户"),
    WZX_MP_ERROR(20,"有关微信公众账号错误");

    private Integer Code;
    private String Message;


    ResultEnums(Integer code, String message) {
        Code = code;
        Message = message;
    }
}
