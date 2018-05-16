package com.mmall.service;

import com.github.pagehelper.PageInfo;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Shipping;

/**
 * @author liaocx on 2017/12/24.
 */
public interface IShippingService {
    ServerResponse add(Integer userId, Shipping shipping);

    ServerResponse<String> delete(Integer userId,Integer shippingId);

    ServerResponse<String> update(Integer userId, Shipping shipping);

    ServerResponse<Shipping> select(Integer userId,Integer shippingId);

    ServerResponse<PageInfo> list(Integer userId, int pageNum, int pageSize);
}
