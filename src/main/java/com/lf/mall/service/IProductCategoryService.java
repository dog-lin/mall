package com.lf.mall.service;

import com.lf.mall.entity.ProductCategory;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lf.mall.vo.ProductCategoryVO;
import org.omg.PortableServer.LIFESPAN_POLICY_ID;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author doglin
 * @since 2021-04-21
 */
public interface IProductCategoryService extends IService<ProductCategory> {
    public List<ProductCategoryVO> getAllProductCategoryVO();
}
