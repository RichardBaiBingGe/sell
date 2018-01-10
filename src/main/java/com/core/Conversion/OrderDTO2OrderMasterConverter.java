package com.core.Conversion;

import com.core.dataobject.OrderMaster;
import com.core.dto.OrderDTO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Richard on 2017/12/15.
 */
public class OrderDTO2OrderMasterConverter {

    public static OrderMaster convert(OrderDTO orderDTO) {

        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        return orderMaster;
    }

    public static List<OrderMaster> convert(List<OrderDTO> orderDTOList) {

        return orderDTOList.stream()
                .map(e -> convert(e))
                .collect(Collectors.toList());
    }
}
