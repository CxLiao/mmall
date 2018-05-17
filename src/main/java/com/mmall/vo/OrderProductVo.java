package com.mmall.vo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author liaocx on 2018/1/7.
 * @author liaocx on 2018/5/17 新增lombok注解.
 */
@Getter
@Setter
public class OrderProductVo {
    private List<OrderItemVo> orderItemVoList;
    private BigDecimal productTotalPrice;
    private String imageHost;
}
