package com.core.service.impl;

import com.core.dataobject.ProductInfo;
import com.core.dto.CartDTO;
import com.core.enums.ProductStatusEnums;
import com.core.enums.ResultEnums;
import com.core.exception.SellException;
import com.core.repository.ProductInfoRepository;
import com.core.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Richard on 2017/12/4.
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductInfoRepository repository;

    @Override
    public ProductInfo saveProduct(ProductInfo ProductInfo) {
        return repository.save(ProductInfo);
    }

    @Override
    public ProductInfo findOne(String productId) {
        return repository.findOne(productId);
    }

    @Override
    public Page<ProductInfo> findAll(Pageable Pageable) {
        return repository.findAll(Pageable);
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return repository.findByProductStatus(ProductStatusEnums.UP.getCode());
    }


    @Override
    public void increaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO: cartDTOList) {

            ProductInfo productInfo = repository.findOne(cartDTO.getProductId());
            if(productInfo == null){
                throw new SellException(ResultEnums.PRODUCT_NOT_EXIST);
            }

            Integer result = productInfo.getProductStock()+cartDTO.getProductQuantity();
            productInfo.setProductStock(result);

            repository.save(productInfo);
        }

    }

    @Override
    @Transactional
    public void decreaseStock(List<CartDTO> cartDTOList) {

        for (CartDTO cartDTO: cartDTOList) {
            ProductInfo productInfo = repository.findOne(cartDTO.getProductId());
            if(productInfo == null ){
                throw new SellException(ResultEnums.PRODUCT_NOT_EXIST);
            }

            Integer result = productInfo.getProductStock()-cartDTO.getProductQuantity();
            if (result<0){
                throw new SellException(ResultEnums.PRODUCT_STOCK_ERROR);
            }

            productInfo.setProductStock(result);
            repository.save(productInfo);
        }

    }
}
