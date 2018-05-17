package com.mmall.vo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author liaocx on 2018/1/6.
 * @author liaocx on 2018/5/17 新增lombok注解.
 */
@Getter
@Setter
public class OrderVo {
    private Long orderNo;

    private BigDecimal payment;

    private Integer paymentType;

    private String paymentTypeDesc;

    private Integer postage;

    private Integer status;

    private String statusDesc;

    private String paymentTime;

    private String sendTime;

    private String endTime;

    private String closeTime;

    private String createTime;
    /**
     * 订单明细
     */
    private List<OrderItemVo> orderItemVoList;

    private String imageHost;

    private Integer shippingId;

    private String receiverName;
    /**
     * 具体的收货地址
     */
    private ShippingVo shippingVo;
}




