package com.mmall.service;

import com.github.pagehelper.PageInfo;
import com.mmall.common.ServerResponse;

import java.util.Map;

/**
 * @author liaocx on 2018/1/3.
 */
public interface IOrderService {
    ServerResponse createOrder(Integer userId,Integer shippingId);

    ServerResponse<String> cancel(Integer userId,Long orderNo);

    ServerResponse getOrderCartProduct(Integer userId);

    ServerResponse getOrderDetail(Integer userId,Long orderNo);

    ServerResponse<PageInfo> getOrderList(Integer userId, int pageNum, int pageSize);

    ServerResponse pay(Long orderNo, Integer userId, String path);

    ServerResponse aliCallback(Map<String,String> params);

    ServerResponse queryOrderPayStatus(Integer userId,Long orderNo);

    //backend
    ServerResponse<PageInfo> manageList(int pageNum, int pageSize);

    ServerResponse manageDetail(Long orderNo);

    ServerResponse<PageInfo> manageSearch(Long orderNo,int pageNum, int pageSize);

    ServerResponse<String> manageSendGoods(Long orderNo);
}
