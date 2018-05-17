package com.mmall.vo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author liaocx on 2017/12/15.
 * @author liaocx on 2018/5/17 新增lombok注解.
 */
@Getter
@Setter
public class ProductListVo {
    private Integer id;

    private Integer categoryId;

    private String name;

    private String subtitle;

    private String mainImage;

    private BigDecimal price;

    private Integer status;

    private String imageHost;
}
