package com.lf.mall.controller;


import com.lf.mall.entity.Order_copy;
import com.lf.mall.entity.User;
import com.lf.mall.service.ICartService;
import com.lf.mall.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author doglin
 * @since 2021-04-21
 */
@Controller
@RequestMapping("/orderCopy")
public class OrderController {
    @Autowired
    private IOrderService service;
    @Autowired
    private ICartService cartService;

    @PostMapping("/settlement3")
    public ModelAndView settlement3(Order_copy orderCopy, HttpSession session,String address,String remark){
        User user = (User) session.getAttribute("user");
        service.save(orderCopy,user,address,remark);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("settlement3");
        modelAndView.addObject("cartList",cartService.findAllCartByUserId(user.getId()));
        modelAndView.addObject("orderCopy",orderCopy);
        return modelAndView;
    }
}

