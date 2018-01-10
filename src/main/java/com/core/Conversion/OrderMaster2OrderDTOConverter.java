package com.core.Conversion;

import com.core.dataobject.OrderMaster;
import com.core.dto.OrderDTO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Richard on 2017/12/15.
 */
public class OrderMaster2OrderDTOConverter {

    public static OrderDTO convert(OrderMaster orderMaster) {

        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        return orderDTO;
    }

    public static List<OrderDTO> convert(List<OrderMaster> orderMasterList) {

/*        List<OrderDTO> orderDTOList = new ArrayList<OrderDTO>();

        for (OrderMaster orderMaster: orderMasterList) {
            OrderDTO orderDTO = convert(orderMaster);
            orderDTOList.add(orderDTO);
        }*/

        return orderMasterList.stream()
                .map(e -> convert(e))
                .collect(Collectors.toList());
    }
}
