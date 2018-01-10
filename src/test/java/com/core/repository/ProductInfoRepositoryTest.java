package com.core.repository;

import com.core.dataobject.ProductInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;


/**
 * Created by Richard on 2017/12/4.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoRepositoryTest {

    @Autowired
    private ProductInfoRepository repository;

    @Test
    public void saveTest(){

        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("1");
        productInfo.setProductName("皮蛋瘦肉粥");
        productInfo.setProductPrice(new BigDecimal(3.2));
        productInfo.setProductStock(10);
        productInfo.setProductDescription("很好喝的粥");
        productInfo.setProductStatus(0);
        productInfo.setProductIcon("http://123.jpg");
        productInfo.setCategoryType(2);

        ProductInfo  ProductInfo=repository.save(productInfo);
        Assert.assertNotNull(ProductInfo);
    }

    @Test
    public void findByProductStatus() throws Exception {

        List<ProductInfo> status=repository.findByProductStatus(0);

        Assert.assertNotEquals(0,status.size());
    }

}