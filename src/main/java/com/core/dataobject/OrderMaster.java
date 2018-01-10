package com.core.dataobject;

import com.core.enums.OrderStatusEnums;
import com.core.enums.PayStatusEnums;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Richard on 2017/12/6.
 */
@Entity
@DynamicUpdate
@Data
public class OrderMaster {

    @Id
    private String orderId;
    private String buyerName;
    private String buyerPhone;
    private String buyerAddress;
    private String buyerOpenid;
    private BigDecimal orderAmount;
    private Integer orderStatus= OrderStatusEnums.NEW.getCode();
    private Integer payStatus= PayStatusEnums.WAIT.getCode();
    private Date createTime;
    private Date updateTime;
}
