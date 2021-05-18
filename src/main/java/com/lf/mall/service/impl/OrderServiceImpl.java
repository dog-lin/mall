package com.lf.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lf.mall.entity.*;
import com.lf.mall.mapper.CartMapper;
import com.lf.mall.mapper.OrderDetailMapper;
import com.lf.mall.mapper.OrderMapper;
import com.lf.mall.mapper.UserAddressMapper;
import com.lf.mall.service.IOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Random;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author doglin
 * @since 2021-04-21
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order_copy> implements IOrderService {

    @Autowired
    private UserAddressMapper userAddressMapper;
    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Override
    public boolean save(Order_copy orderCopy, User user,String address,String remark) {
        //判断是否是新增地址
        if("newAddress".equals(orderCopy.getUserAddress())){
            UserAddress userAddress = new UserAddress();
            userAddress.setAddress(address);
            userAddress.setRemark(remark);
            userAddress.setIsdefault(1);
            userAddress.setUserId(user.getId());

            QueryWrapper wrapper = new QueryWrapper();
            wrapper.eq("isdefault",1);
            wrapper.eq("user_id",user.getId());
            UserAddress oldDefault = userAddressMapper.selectOne(wrapper);
            oldDefault.setIsdefault(0);
            userAddressMapper.updateById(oldDefault);
            userAddressMapper.insert(userAddress);
            orderCopy.setUserAddress(address);
        }
        orderCopy.setUserId(user.getId());
        orderCopy.setLoginName(user.getLoginName());
        String seriaNumber = null;
        try {
            StringBuffer result = new StringBuffer();
            for (int i = 0; i < 32; i++) {
                result.append(Integer.toHexString(new Random().nextInt(16)));
            }
            seriaNumber = result.toString().toUpperCase();
        }catch (Exception e){
            e.printStackTrace();
        }
        orderCopy.setSerialnumber(seriaNumber);
        orderMapper.insert(orderCopy);

        //存储orderdetail
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("user_id",user.getId());
        List<Cart> cartList = cartMapper.selectList(wrapper);
        for (Cart cart:cartList){
            OrderDetail orderDetail = new OrderDetail();
            BeanUtils.copyProperties(cart,orderDetail);
            orderDetail.setId(null);
            orderDetail.setOrderId(orderCopy.getId());
            orderDetailMapper.insert(orderDetail);
        }

        //清空购物车
        QueryWrapper wrapper1 = new QueryWrapper();
        wrapper.eq("user_id",user.getId());
        cartMapper.delete(wrapper1);
        return true;
    }
}
