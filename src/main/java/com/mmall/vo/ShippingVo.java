package com.mmall.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author liaocx on 2018/1/6.
 * @author liaocx on 2018/5/17 新增lombok注解.
 */
@Getter
@Setter
public class ShippingVo {
    private String receiverName;

    private String receiverPhone;

    private String receiverMobile;

    private String receiverProvince;

    private String receiverCity;

    private String receiverDistrict;

    private String receiverAddress;

    private String receiverZip;
}
