package com.core.controller;

import com.core.VO.ProductInfoVO;
import com.core.VO.ProductVO;
import com.core.VO.ResultVO;
import com.core.dataobject.ProductCategory;
import com.core.dataobject.ProductInfo;
import com.core.service.CategoryService;
import com.core.service.ProductService;
import com.core.utils.ResultVOUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Richard on 2017/12/4.
 */

@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public ResultVO list(){

        //1.查询所有的上架商品。
        List<ProductInfo> productInfoList=productService.findUpAll();

        //2.查询类目
        //传统方法
/*
        List<Integer> categoryTypeList = new ArrayList<Integer>();
        for(ProductInfo productInfo : productInfoList){
            categoryTypeList.add(productInfo.getCategoryType());
        }
        List<ProductCategory> productCategoryList=categoryService.findByCategoryTypeIn(categoryTypeList);
*/
        //精简做法(java8, lambda)
        List<Integer> categoryTypeList=productInfoList.stream().map(e->e.getCategoryType()).collect(Collectors.toList());


        List<ProductCategory> productCategoryList= categoryService.findByCategoryTypeIn(categoryTypeList);

        //3.数据拼装

        ResultVO resultVO = new ResultVO();

        List<ProductVO> ProductVOList= new ArrayList<ProductVO>();
        for(ProductCategory productCategory: productCategoryList){

            ProductVO productVO = new ProductVO();
            productVO.setCategoryType(productCategory.getCategoryType());
            productVO.setCategoryName(productCategory.getCategoryName());

            List<ProductInfoVO>  productInfoVOList= new ArrayList<ProductInfoVO>();

            for(ProductInfo productInfo : productInfoList){

                if(productInfo.getCategoryType().equals(productCategory.getCategoryType())){

                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    BeanUtils.copyProperties(productInfo,productInfoVO);
                    productInfoVOList.add(productInfoVO);
                }
            }
            productVO.setProductInfoVOList(productInfoVOList);
            ProductVOList.add(productVO);
        }
        return ResultVOUtil.success(ProductVOList);
    }

}
