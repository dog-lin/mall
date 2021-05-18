package com.lf.mall.controller;


import com.lf.mall.entity.User;
import com.lf.mall.service.ICartService;
import com.lf.mall.service.IProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
@RestController
@RequestMapping("/productCategory")
public class ProductCategoryController {

    @Autowired
    private IProductCategoryService service;
    @Autowired
    private ICartService cartService;

    @GetMapping("/list")
    public ModelAndView list(HttpSession session){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("main");//跳去main页面
        modelAndView.addObject("list",service.getAllProductCategoryVO());
        User user = (User) session.getAttribute("user");
        if (user == null) {
            modelAndView.addObject("cartList",new ArrayList<>());
        }else{
            modelAndView.addObject("cartList",cartService.findAllCartByUserId(user.getId()));
        }

        return  modelAndView;
    }
}

