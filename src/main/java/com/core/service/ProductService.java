package com.core.service;

import com.core.dataobject.ProductInfo;
import com.core.dto.CartDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by Richard on 2017/12/4.
 */
public interface ProductService {

    ProductInfo saveProduct(ProductInfo ProductInfo);

    ProductInfo findOne(String productId);

    Page<ProductInfo> findAll(Pageable Pageable);

    //查询上架的商品
    List<ProductInfo> findUpAll();

    //加库存
    void increaseStock(List<CartDTO> cartDTOList);


    //减库存
    void decreaseStock(List<CartDTO> cartDTOList);
}
