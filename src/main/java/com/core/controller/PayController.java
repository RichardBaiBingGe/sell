package com.core.controller;

import com.core.dto.OrderDTO;
import com.core.enums.ResultEnums;
import com.core.exception.SellException;
import com.core.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Richard on 2017/12/22.
 */
@Controller
@Slf4j
@RequestMapping("/pay")
public class PayController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/create")
    public String create(@RequestParam("orderId") String orderId,
                         @RequestParam("returnUrl") String returnUrl){

        //1.查询订单
        OrderDTO orderDTO=orderService.findOne(orderId);
        if(orderDTO == null){
            log.error("【查询订单】 订单编号不正确 orderId={}",orderId);
            throw new SellException(ResultEnums.ORDER_NOT_EXIST);
        }
        //2.发起支付

        return null;
    }
}
