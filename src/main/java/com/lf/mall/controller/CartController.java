package com.lf.mall.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lf.mall.entity.Cart;
import com.lf.mall.entity.User;
import com.lf.mall.service.ICartService;
import com.lf.mall.service.IUserAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private ICartService service;
    @Autowired
    IUserAddressService userAddressService;

    @GetMapping("/add/{productId}/{price}/{quantity}")
    public String add(@PathVariable("productId")Integer productId,
                            @PathVariable("price") Float price,
                            @PathVariable("quantity")Integer quantity,
                            HttpSession session){
        Cart cart = new Cart();
        cart.setProductId(productId);
        cart.setQuantity(quantity);
        cart.setCost((price*quantity));
        User user = (User) session.getAttribute("user");
        cart.setUserId(user.getId());
        try {
            if (service.save(cart)) {
                return "redirect:/cart/findAllCart";
            }
        }catch (Exception e){
            return "redirect:/";
        }
        return null;
    }

    @GetMapping("/findAllCart")
    public ModelAndView findAllCart(HttpSession session){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("settlement1");
        User user = (User) session.getAttribute("user");
        modelAndView.addObject("cartList",service.findAllCartByUserId(user.getId()));
        return modelAndView;
    }

    @GetMapping("/deleteById/{id}")
    public String deleteById(@PathVariable("id")Integer id){
        if(service.removeById(id)){

        }
        return "redirect:/cart/findAllCart";
    }

    @GetMapping("/settlement2")
    public ModelAndView settlement2(HttpSession session){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("settlement2");
        User user = (User) session.getAttribute("user");
        modelAndView.addObject("cartList",service.findAllCartByUserId(user.getId()));
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("user_id",user.getId());
        modelAndView.addObject("addressList",userAddressService.list(wrapper));
        return modelAndView;
    }

    @PostMapping("/update/{id}/{quantity}/{cost}")
    @ResponseBody
    public String update(@PathVariable("id")Integer id,@PathVariable("quantity")Integer quantity,@PathVariable("cost")Float cost){
        Cart cart = service.getById(id);
        cart.setQuantity(quantity);
        cart.setCost(cost);
        if(service.updateById(cart)){
            return "success";
        }else{
            return "fail";
        }
    }
}

