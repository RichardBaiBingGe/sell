package com.core.service;

import com.core.dto.OrderDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

/**
 * Created by Richard on 2017/12/11.
 */
public interface OrderService {

    //创建订单
    OrderDTO create(OrderDTO orderDTO);

    //查询单个订单详情
    OrderDTO findOne(String orderId);

    //查询订单总列表(买家用)
    Page<OrderDTO> findList(String buyerOpenid, Pageable pageable);

    //取消订单
    OrderDTO cancel(OrderDTO orderDTO);

    //完结订单
    OrderDTO finish(OrderDTO orderDTO);

    //支付订单
    OrderDTO paid(OrderDTO orderDTO);

}
