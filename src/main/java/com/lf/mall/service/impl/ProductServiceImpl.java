package com.lf.mall.service.impl;

import com.lf.mall.entity.Product;
import com.lf.mall.mapper.ProductMapper;
import com.lf.mall.service.IProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author doglin
 * @since 2021-04-21
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {

    @Autowired
    private ProductMapper mapper;

    @Override
    public List<Product> findCategoryId(String type,Integer id) {
        Map<String,Object> map = new HashMap<>();
        map.put("categorylevel"+type+"_id",id);
        return mapper.selectByMap(map);
    }
}
