package com.lf.mall.service;

import com.lf.mall.entity.Product;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author doglin
 * @since 2021-04-21
 */
public interface IProductService extends IService<Product> {
    public List<Product> findCategoryId(String type,Integer id);
}
