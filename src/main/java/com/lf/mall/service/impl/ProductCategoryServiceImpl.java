package com.lf.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lf.mall.controller.ProductController;
import com.lf.mall.entity.Product;
import com.lf.mall.entity.ProductCategory;
import com.lf.mall.mapper.ProductCategoryMapper;
import com.lf.mall.mapper.ProductMapper;
import com.lf.mall.service.IProductCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lf.mall.vo.ProductCategoryVO;
import com.lf.mall.vo.ProductVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author doglin
 * @since 2021-04-21
 */
@Service
public class ProductCategoryServiceImpl extends ServiceImpl<ProductCategoryMapper, ProductCategory> implements IProductCategoryService {

    @Autowired
    private ProductCategoryMapper productCategoryMapper;

    @Autowired
    private ProductMapper productMapper;
    @Override
    public List<ProductCategoryVO> getAllProductCategoryVO() {

        //一级分类
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("type",1);
        List<ProductCategory> levelOne = productCategoryMapper.selectList(wrapper);
        List<ProductCategoryVO> levelOneVO = levelOne.stream().map(e -> new ProductCategoryVO(e.getId(),e.getName())).collect(Collectors.toList());
        for (int i = 0; i < levelOneVO.size(); i++) {
            levelOneVO.get(i).setBannerImg("/images/banner"+i+".png");
            levelOneVO.get(i).setTopImg("/images/top"+i+".png");//图片赋值

            wrapper = new QueryWrapper();
            wrapper.eq("categorylevelone_id",levelOneVO.get(i).getId());
            List<Product> productList = productMapper.selectList(wrapper);
            List<ProductVO> productVOList = productList.stream().map(e -> new ProductVO(e.getId(),e.getName(),e.getPrice(),e.getFileName())).collect(Collectors.toList());
            levelOneVO.get(i).setProductVOList(productVOList);
        }
        for(ProductCategoryVO lone : levelOneVO){
            wrapper = new QueryWrapper();
            wrapper.eq("type",2);
            wrapper.eq("parent_id",lone.getId());
            List<ProductCategory> levelTwo = productCategoryMapper.selectList(wrapper);
            List<ProductCategoryVO> levelTwoVO = levelTwo.stream().map(e -> new ProductCategoryVO(e.getId(),e.getName())).collect(Collectors.toList());
            lone.setChildren(levelTwoVO);
            for(ProductCategoryVO ltwo : levelTwoVO){
                wrapper = new QueryWrapper();
                wrapper.eq("type",3);
                wrapper.eq("parent_id",ltwo.getId());
                List<ProductCategory> levelTh = productCategoryMapper.selectList(wrapper);
                List<ProductCategoryVO> levelThVO = levelTh.stream().map(e -> new ProductCategoryVO(e.getId(),e.getName())).collect(Collectors.toList());
                ltwo.setChildren(levelThVO);
            }
        }
        return levelOneVO;
    }
}
