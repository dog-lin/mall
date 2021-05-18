package com.lf.mall.controller;


import com.lf.mall.entity.User;
import com.lf.mall.service.ICartService;
import com.lf.mall.service.IProductCategoryService;
import com.lf.mall.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author doglin
 * @since 2021-04-21
 */
@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private IProductService service;
    @Autowired
    private IProductCategoryService cservice;
    @Autowired
    private ICartService cartService;

    @GetMapping("/list/{type}/{id}")
    public ModelAndView list(@PathVariable("type") String type, @PathVariable("id")Integer id, HttpSession session){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("productList");
        modelAndView.addObject("productList",service.findCategoryId(type,id));
        modelAndView.addObject("list",cservice.getAllProductCategoryVO());
        User user = (User) session.getAttribute("user");
        if (user == null) {
            modelAndView.addObject("cartList",new ArrayList<>());
        }else{
            modelAndView.addObject("cartList",cartService.findAllCartByUserId(user.getId()));
        }
        return modelAndView;
    }

    @GetMapping("/findById/{id}")
    public ModelAndView findById(@PathVariable("id")Integer id,HttpSession session){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("productDetail");
        modelAndView.addObject("product",service.getById(id));
        modelAndView.addObject("list",cservice.getAllProductCategoryVO());
        User user = (User) session.getAttribute("user");
        if (user == null) {
            modelAndView.addObject("cartList",new ArrayList<>());
        }else{
            modelAndView.addObject("cartList",cartService.findAllCartByUserId(user.getId()));
        }
        return modelAndView;
    }
}

