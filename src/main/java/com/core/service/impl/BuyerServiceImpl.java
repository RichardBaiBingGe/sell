package com.core.service.impl;

import com.core.dto.OrderDTO;
import com.core.enums.ResultEnums;
import com.core.exception.SellException;
import com.core.service.BuyerService;
import com.core.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Richard on 2017/12/19.
 */

@Service
@Slf4j
public class BuyerServiceImpl implements BuyerService {

    @Autowired
    private OrderService orderService;

    @Override
    public OrderDTO findOrderOne(String openid, String orderId) {
        return checkOrderOwner(openid, orderId);
    }

    @Override
    public OrderDTO cancelOrder(String openid, String orderId) {

        OrderDTO orderDTO = checkOrderOwner(openid, orderId);
        if (orderDTO == null){
            log.error("【取消订单】 订单不存在, orderId={}",orderId);
            throw new SellException(ResultEnums.ORDER_NOT_EXIST);
        }
        return orderService.cancel(orderDTO);
    }

    private OrderDTO checkOrderOwner(String openid, String orderId){

        OrderDTO orderDTO=orderService.findOne(orderId);
        if(orderDTO== null){
            return null;
        }
        //判断订单是否属于当前用户
        if(!orderDTO.getBuyerOpenid().equalsIgnoreCase(openid)){
            log.error("【查询订单】 订单的openid不同, openid={},orderDTO={}",openid,orderDTO);
            throw new SellException(ResultEnums.ORDER_OWNER_ERROR);
        }
        return orderDTO;
    }
}
