package com.core.Conversion;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.core.dataobject.OrderDetail;
import com.core.dto.OrderDTO;
import com.core.enums.ResultEnums;
import com.core.exception.SellException;
import com.core.form.OrderForm;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Richard on 2017/12/18.
 */
@Slf4j
public class OrderForm2OrderDTOConversion {

    public static OrderDTO convert(OrderForm orderForm){

        Gson gson = new Gson();

        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setBuyerOpenid(orderForm.getOpenid());

        List<OrderDetail> orderDetailList = new ArrayList<OrderDetail>();

        try {
            orderDetailList=gson.fromJson(orderForm.getItems(), new TypeToken<List<OrderDetail>>() {}.getType());
        }catch (Exception e){
            log.error("【对象转换错误】, String={}" ,orderForm.getItems());
            throw new SellException(ResultEnums.PARAM_ERROR);
        }
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;

    }

}
