package com.mmall.vo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author liaocx on 2017/12/22.
 * @author liaocx on 2018/5/17 新增lombok注解.
 */
@Getter
@Setter
public class CartVo {
    private List<CartProductVo> cartProductVoList;
    private BigDecimal cartTotalPrice;
    /**
     * 是否已经都勾选
     */
    private Boolean allChecked;
    private String imageHost;
}
