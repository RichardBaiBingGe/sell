package com.core.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 *类目
 *  Created by Richard on 2017/11/30.
 */

@Entity
//设置为DynamicUpdate将会动态更新数据库的时间，如果不设置将不会动态更新。
@DynamicUpdate
//有这个注解可以自动生成Get and Set toString 等方法。
@Data
public class ProductCategory {

    @Id
    @GeneratedValue
    private Integer categoryId;

    private String categoryName;

    private Integer categoryType;

    private Date createTime;

    private Date updateTime;

    public ProductCategory() {
    }

    public ProductCategory(String categoryName, Integer categoryType) {
        this.categoryName = categoryName;
        this.categoryType = categoryType;
    }
}
