package com.core.service.impl;

import com.core.Conversion.OrderDTO2OrderMasterConverter;
import com.core.Conversion.OrderMaster2OrderDTOConverter;
import com.core.dataobject.OrderDetail;
import com.core.dataobject.OrderMaster;
import com.core.dataobject.ProductInfo;
import com.core.dto.CartDTO;
import com.core.dto.OrderDTO;
import com.core.enums.OrderStatusEnums;
import com.core.enums.PayStatusEnums;
import com.core.enums.ResultEnums;
import com.core.exception.SellException;
import com.core.repository.OrderDetailRepository;
import com.core.repository.OrderMasterRepository;
import com.core.service.OrderService;
import com.core.service.ProductService;
import com.core.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Richard on 2017/12/11.
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderMasterRepository orderMasterRepository;


    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {

        String orderId = KeyUtil.genUniqueKey();
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);
        //List<CartDTO> cartDTOList = new ArrayList<CartDTO>();

        //1.查询商品的数量，价格
        for (OrderDetail orderDetail : orderDTO.getOrderDetailList()) {

            ProductInfo productInfo = productService.findOne(orderDetail.getProductId());

            if (productInfo == null) {
                throw new SellException(ResultEnums.PRODUCT_NOT_EXIST);
            }
            //2.计算订单总价
            orderAmount = productInfo.getProductPrice()
                    .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                    .add(orderAmount);

            //订单详情插入数据库
            BeanUtils.copyProperties(productInfo,orderDetail);
            orderDetail.setDetailId(KeyUtil.genUniqueKey());
            orderDetail.setOrderId(orderId);
            orderDetailRepository.save(orderDetail);

            //CartDTO cartDTO = new CartDTO(orderDetail.getProductId(),orderDetail.getProductQuantity());
            //cartDTOList.add(cartDTO);

        }
        //3.写入数据库
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO,orderMaster);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnums.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnums.WAIT.getCode());
        orderMaster.setCreateTime(new Date());
        orderMaster.setUpdateTime(new Date());
        orderMasterRepository.save(orderMaster);

        //4.扣库存
        //lambda 表达式
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream()
                .map(e -> new CartDTO(e.getProductId(),e.getProductQuantity()))
                .collect(Collectors.toList());
        productService.decreaseStock(cartDTOList);


        return orderDTO;
    }

    @Override
    public OrderDTO findOne(String orderId) {
        OrderMaster orderMaster = orderMasterRepository.findOne(orderId);
        if (orderMaster == null ){
            throw new SellException(ResultEnums.ORDER_NOT_EXIST);
        }

        List<OrderDetail> orderDetail =  orderDetailRepository.findByOrderId(orderId);
        if(orderDetail == null){
            throw new SellException(ResultEnums.ORDERDETAIL_NOT_EXIST);
        }

        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        orderDTO.setOrderDetailList(orderDetail);
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {

        Page<OrderMaster> orderMasterPage=orderMasterRepository.findByBuyerOpenid(buyerOpenid,pageable);
        List<OrderDTO> orderDTOList=OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent());
        Page<OrderDTO> orderDTOPage = new PageImpl<OrderDTO>(orderDTOList,pageable,orderMasterPage.getTotalElements());
        return orderDTOPage;
    }

    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {

        OrderMaster orderMaster = OrderDTO2OrderMasterConverter.convert(orderDTO);

        //判断订单的状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnums.NEW.getCode())){
            log.error("【取消订单】 订单状态不正确 ,orderId={},orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnums.ORDER_STATUS_ERROR);
        }
        //修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnums.CANCLE.getCode());
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster updateResult=orderMasterRepository.save(orderMaster);
        if(updateResult == null){
            log.error("【取消订单】 更新失败 orderMaster={}" ,orderMaster);
            throw new SellException(ResultEnums.ORDER_UPDATE_FAIL);
        }
        //返还库存
        if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){

            log.error("【取消订单】 订单中无商品 orderDTO={}",orderDTO);
            throw new SellException(ResultEnums.ORDER_DETAIL_EMPTY);
        }
        /* 原始的写法
        List<CartDTO> cartDTOList = new ArrayList<CartDTO>();

        for (OrderDetail OrderDetail: orderDTO.getOrderDetailList()) {
            CartDTO cartDTO = new CartDTO(OrderDetail.getProductId(),OrderDetail.getProductQuantity());
            cartDTOList.add(cartDTO);
        }*/
        //collect 收集
        //Collectors 收集器
        //lambda 表达式
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList()
                .stream()
                .map(e -> new CartDTO(e.getProductId(),e.getProductQuantity()))
                .collect(Collectors.toList());
        productService.increaseStock(cartDTOList);
        //如果已经支付，需要退款
        if(orderDTO.getPayStatus().equals(PayStatusEnums.SUCCESS.getCode())){
            //TODO
        }

        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO finish(OrderDTO orderDTO) {
        //判断订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnums.NEW.getCode())){
            log.error("【完结订单】 订单状态不正确,orderId={},orderStatus={}", orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnums.ORDER_STATUS_ERROR);
        }
        //修改状态
        orderDTO.setOrderStatus(OrderStatusEnums.FINISHED.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster result = orderMasterRepository.save(orderMaster);

        if(result == null){
            log.error("【订单状态更新失败】,orderId={},orderStatus={}",result.getOrderId(),result.getOrderStatus());
            throw new SellException(ResultEnums.ORDER_UPDATE_FAIL);
        }
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO paid(OrderDTO orderDTO) {
        //判断订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnums.NEW.getCode())){
            log.error("【支付订单】 订单状态不正确,orderId={},orderStatus={}", orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnums.ORDER_STATUS_ERROR);
        }
        //判断支付状态
        if(!orderDTO.getPayStatus().equals(PayStatusEnums.WAIT.getCode())){
            log.error("【支付状态】 支付状态不正确, orderDTO={}",orderDTO);
            throw new SellException(ResultEnums.ORDER_PAY_STATUS_ERROR);
        }
        //修改订单状态
        orderDTO.setPayStatus(PayStatusEnums.SUCCESS.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster result=orderMasterRepository.save(orderMaster);

        if(result == null){
            log.error("【订单支付状态更新失败】,orderMaster={}",orderMaster);
            throw new SellException(ResultEnums.ORDER_UPDATE_FAIL);
        }

        return orderDTO;
    }

}
