package com.mmall.vo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * value object
 * @author liaocx on 2017/12/15.
 * @author liaocx on 2018/5/17 新增lombok注解.
 */
@Getter
@Setter
public class ProductDetailVo {
    private Integer  id;

    private Integer categoryId;

    private String name;

    private String subtitle;

    private String mainImage;

    private String subImages;

    private String detail;

    private BigDecimal price;

    private Integer stock;

    private Integer status;

    private String createTime;

    private String updateTime;
    /**
     * 相比pojo中Product类多出的私有属性
     */
    private String imageHost;

    private Integer parentCategoryId;
}
