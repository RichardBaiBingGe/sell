package com.core.service.impl;

import com.core.dataobject.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Richard on 2017/11/30.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceImplTest {
    @Autowired
    private CategoryServiceImpl service;

    @Test
    public void findOne() throws Exception {

        ProductCategory category = service.findOne(1);
        Assert.assertEquals(new Integer(1) , category.getCategoryId());
    }

    @Test
    public void findAll() throws Exception {

        List<ProductCategory> categoryList=service.findAll();
        Assert.assertNotEquals(0,categoryList.size());

    }

    @Test
    public void findByCategoryTypeIn() throws Exception {
        List<Integer> categoryList= Arrays.asList(1,2,3,4);
        List<ProductCategory> result=service.findByCategoryTypeIn(categoryList);
        Assert.assertNotEquals(0,result.size());

    }

    @Test
    @Transactional
    public void save() throws Exception {

        ProductCategory productCategory = new ProductCategory("测试类",4);
        ProductCategory result = service.save(productCategory);
        Assert.assertNotNull(result);
    }

}