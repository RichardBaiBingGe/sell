package com.core.service;

import com.core.dataobject.ProductCategory;

import java.util.List;

/**
 * Created by Richard on 2017/11/30.
 */
public interface CategoryService {

    ProductCategory findOne(Integer categoryId);
    List<ProductCategory> findAll();
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
    ProductCategory save(ProductCategory ProductCategory);
}
