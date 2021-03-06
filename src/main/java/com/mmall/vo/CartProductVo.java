package com.mmall.vo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 结合了产品和购物车的一个抽象对象
 * @author liaocx on 2017/12/22.
 * @author liaocx on 2018/5/17 新增lombok注解.
 */
@Getter
@Setter
public class CartProductVo {
    private Integer id;
    private Integer userId;
    private Integer productId;
    /**
     * 购物车中此商品的数量
     */
    private Integer quantity;
    private String productName;
    private String productSubtitle;
    private String productMainImage;
    private BigDecimal productPrice;
    private Integer productStatus;
    private BigDecimal productTotalPrice;
    private Integer productStock;
    /**
     * 此商品是否勾选
     */
    private Integer productChecked;
    /**
     * 限制数量的一个返回结果
     */
    private String limitQuantity;
}
