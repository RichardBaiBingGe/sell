package com.core.repository;

import com.core.dataobject.OrderMaster;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Richard on 2017/12/7.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterRepositoryTest {

    @Autowired
    private OrderMasterRepository repository;

    private final String OPENID="110110";

    @Test
    public void saveTest(){

        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("123457");
        orderMaster.setBuyerName("师兄");
        orderMaster.setBuyerPhone("13811111111111");
        orderMaster.setBuyerAddress("myhome");
        orderMaster.setBuyerOpenid(OPENID);
        orderMaster.setOrderAmount(new BigDecimal(3.5));
        orderMaster.setCreateTime(new Date());
        orderMaster.setUpdateTime(new Date());
        OrderMaster result=repository.save(orderMaster);
        Assert.assertNotNull(result);

    }

    @Test
    public void findByBuyerOpenid() throws Exception {

        PageRequest page = new PageRequest(0,1);

        Page<OrderMaster> result=repository.findByBuyerOpenid(OPENID,page);

        System.out.println(result.getTotalElements());
    }

}