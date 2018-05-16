package com.mmall.pojo;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

/**
 * OrderItem domain
 * @author liaocx on 2017/10/13.
 * @author liaocx 重写于2018/5/15 新增lombok注解
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
    private Integer id;

    private Integer userId;

    private Long orderNo;

    private Integer productId;

    private String productName;

    private String productImage;

    private BigDecimal currentUnitPrice;

    private Integer quantity;

    private BigDecimal totalPrice;

    private Date createTime;

    private Date updateTime;
}