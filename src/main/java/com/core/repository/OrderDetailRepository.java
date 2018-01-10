package com.core.repository;

import com.core.dataobject.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Richard on 2017/12/7.
 */
public interface OrderDetailRepository extends JpaRepository<OrderDetail, String> {

      List<OrderDetail> findByOrderId(String orderId);
}
