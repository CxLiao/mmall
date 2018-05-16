package com.mmall.pojo;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Order domain
 * @author liaocx on 2017/10/13.
 * @author liaocx 重写于2018/5/15 新增lombok注解
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private Integer id;

    private Long orderNo;

    private Integer userId;

    private Integer shippingId;

    private BigDecimal payment;

    private Integer paymentType;

    private Integer postage;

    private Integer status;

    private Date paymentTime;

    private Date sendTime;

    private Date endTime;

    private Date closeTime;

    private Date createTime;

    private Date updateTime;
}