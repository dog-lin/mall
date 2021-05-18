package com.lf.mall.service;

import com.lf.mall.entity.Cart;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lf.mall.vo.CartVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author doglin
 * @since 2021-04-21
 */
public interface ICartService extends IService<Cart> {
    public List<CartVO> findAllCartByUserId(Integer id);
}
