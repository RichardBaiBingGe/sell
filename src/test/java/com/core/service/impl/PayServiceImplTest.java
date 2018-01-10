package com.core.service.impl;

import com.core.dto.OrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created by Richard on 2017/12/22.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class PayServiceImplTest {

    @Autowired
    private OrderServiceImpl orderService;

    @Autowired
    private PayServiceImpl payService;
    @Test
    public void create() throws Exception {

        OrderDTO orderDTO = orderService.findOne("1513674212258693663");

        payService.create(orderDTO);

    }

}