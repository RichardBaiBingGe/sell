package com.core.service;

import com.core.dto.OrderDTO;

/**
 * 查询买家
 * Created by Richard on 2017/12/19.
 */
public interface BuyerService {

    //查询一个订单
    OrderDTO findOrderOne(String openid,String orderId);

    //取消订单
    OrderDTO cancelOrder(String openid,String orderId);
}
