package com.lf.mall.service;

import com.lf.mall.entity.Order_copy;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lf.mall.entity.User;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author doglin
 * @since 2021-04-21
 */
public interface IOrderService extends IService<Order_copy> {
    public boolean save(Order_copy orderCopy, User user,String address,String remark);
}
