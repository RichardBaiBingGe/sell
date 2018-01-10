package com.core.repository;

import com.core.dataobject.OrderDetail;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Richard on 2017/12/7.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailRepositoryTest {

    @Autowired
    private OrderDetailRepository repository;

    @Test
    public void saveTest(){

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId("123");
        orderDetail.setOrderId("123");
        orderDetail.setProductId("1");
        orderDetail.setProductName("紫菜蛋花汤");
        orderDetail.setProductPrice(new BigDecimal(3.5));
        orderDetail.setProductQuantity(2);
        orderDetail.setProductIcon("http://XXXX.jpg");

        OrderDetail result=repository.save(orderDetail);
        Assert.assertNotNull(result);

    }

    @Test
    public void findByOrderId() throws Exception {

        List<OrderDetail> result =repository.findByOrderId("123");
        Assert.assertNotEquals(0,result.size());
    }

}