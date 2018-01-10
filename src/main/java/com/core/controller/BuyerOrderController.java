package com.core.controller;

import com.core.Conversion.OrderForm2OrderDTOConversion;
import com.core.VO.ResultVO;
import com.core.dto.OrderDTO;
import com.core.enums.ResultEnums;
import com.core.exception.SellException;
import com.core.form.OrderForm;
import com.core.service.BuyerService;
import com.core.service.OrderService;
import com.core.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Richard on 2017/12/18.
 */
@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {

    @Autowired
    private OrderService orderServer;

    @Autowired
    private BuyerService buyerService;

    //创建订单
    @PostMapping("/create")
    public ResultVO<Map<String,String>> create(@Valid OrderForm orderForm,
                                                      BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            log.error("【创建订单】 参数不正确 , orderForm={}",orderForm);
            throw new SellException(ResultEnums.PARAM_ERROR.getCode(),
                                    bindingResult.getFieldError().getDefaultMessage());
        }
        OrderDTO orderDTO = OrderForm2OrderDTOConversion.convert(orderForm);
        if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("【创建订单】 购物车不能为空");
            throw new SellException(ResultEnums.CART_EMPTY);
        }
        OrderDTO result=orderServer.create(orderDTO);

        Map<String,String> map = new HashMap<String,String>();
        map.put("orderId",result.getOrderId());

        return ResultVOUtil.success(map);
    }

    //订单列表
    @GetMapping("/list")
    public ResultVO<List<OrderDTO>> List(@RequestParam("openid") String openid,
                                         @RequestParam(value = "page", defaultValue = "0") Integer page,
                                         @RequestParam(value = "size", defaultValue = "10") Integer size){
        if(StringUtils.isEmpty(openid)){
            log.error("【查询订单列表】openid 不能为空 openid={}",openid);
            throw new SellException(ResultEnums.PARAM_ERROR);
        }
        PageRequest pageRequest = new PageRequest(page,size);
        Page<OrderDTO> orderDTOPage=orderServer.findList(openid,pageRequest);
        return ResultVOUtil.success(orderDTOPage.getContent());
    }

    //订单详情
    @GetMapping("/detail")
    public ResultVO<OrderDTO> detail(@RequestParam("openid") String openid,
                                     @RequestParam("orderId") String orderId){

        if(StringUtils.isEmpty(openid)){
            log.error("【订单详情列表】openid 不能为空 openid={}",openid);
            throw new SellException(ResultEnums.PARAM_ERROR);
        }
        if (StringUtils.isEmpty(orderId)){
            log.error("【订单详情列表】orderId 不能为空 orderId={}",orderId);
            throw new SellException(ResultEnums.PARAM_ERROR);
        }
        OrderDTO orderDTO = buyerService.findOrderOne(openid,orderId);

        return ResultVOUtil.success(orderDTO);
    }
    //取消订单
    @PostMapping("/cancel")
    public ResultVO cancel(@RequestParam("openid") String openid,
                           @RequestParam("orderId") String orderId){
        buyerService.cancelOrder(openid,orderId);
        return ResultVOUtil.success();
    }
}
