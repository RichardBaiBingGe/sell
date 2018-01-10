package com.core.repository;


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
public class ProductCategoryRepositoryTest {

    @Autowired
    private ProductCategoryRepository repository;

    @Test
    public void findOneTest(){

        ProductCategory category=repository.findOne(1);

        System.out.println(category.toString());
    }

    @Test
    @Transactional
    public void saveTest(){

        ProductCategory category = new ProductCategory("男生最爱",4);
        ProductCategory result=repository.save(category);
        Assert.assertNotNull(result);
    }

    @Test
    public void updateTimeTest(){
        ProductCategory category = repository.findOne(2);
        category.setCategoryType(3);
        repository.save(category);
    }

    @Test
    public void updateTest(){
        ProductCategory category = new ProductCategory("男生喜欢",3);
        repository.save(category);
    }

    @Test
    public void findByCategoryListTest(){

        List<Integer> categoryList = Arrays.asList(1,2,3,4);

        List<ProductCategory> result=repository.findByCategoryTypeIn(categoryList);

        //Assert.assertNotNull(result);
        Assert.assertNotEquals(0,result.size());

    }

}