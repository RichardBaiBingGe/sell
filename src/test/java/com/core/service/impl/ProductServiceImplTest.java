package com.core.service.impl;

import com.core.dataobject.ProductInfo;
import com.core.enums.ProductStatusEnums;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Richard on 2017/12/4.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceImplTest {

    @Autowired
    private ProductServiceImpl productService;

    @Test
    public void saveProduct() throws Exception {

        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("2");
        productInfo.setProductName("回锅肉");
        productInfo.setProductPrice(new BigDecimal(15));
        productInfo.setProductStock(10);
        productInfo.setProductDescription("very nice");
        productInfo.setProductStatus(ProductStatusEnums.DOWN.getCode());
        productInfo.setProductIcon("http://256.jpg");
        productInfo.setCategoryType(2);

        ProductInfo result=productService.saveProduct(productInfo);

        Assert.assertNotNull(result);

    }

    @Test
    public void findOne() throws Exception {

        ProductInfo ProductInfo=productService.findOne("1");

        Assert.assertNotEquals(0,ProductInfo.toString());
    }

    @Test
    public void findAll() throws Exception {

        PageRequest request =new PageRequest(0,10);

        Page<ProductInfo> page =productService.findAll(request);

        System.out.println(page.getTotalElements());

    }

    @Test
    public void findUpAll() throws Exception {
        List<ProductInfo> UPResult=productService.findUpAll();
        System.out.println(UPResult.size());
        Assert.assertNotEquals(0,UPResult.size());
    }

}