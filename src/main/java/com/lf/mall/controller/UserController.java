package com.lf.mall.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lf.mall.entity.User;
import com.lf.mall.service.ICartService;
import com.lf.mall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService service;
    @Autowired
    private ICartService cartService;

    @PostMapping("/register")
    public String register(User user, Model model){
        boolean result;
        try {
            result = service.save(user);
        }catch (Exception e){
            model.addAttribute("error",user.getLoginName()+"已存在");
            return "register";
        }
        return result ? "login" : "register";
    }
    /**
     * 登录
     * @param loginName
     * @param password
     * @param session
     * @return
     */
    @PostMapping("/login")
    public String login(String loginName, String password, HttpSession session){
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("login_name",loginName);
        wrapper.eq("password",password);
        User user = service.getOne(wrapper);
        if (user == null) {
            return "login";
        }else{
            session.setAttribute("user",user);
            return "redirect:/productCategory/list";
        }
    }

    /**
     * 注销
     * @param session
     * @return
     */
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "login";
    }

    @GetMapping("/userInfo")
    public ModelAndView userInfo(HttpSession session){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("userInfo");
        User user = (User)session.getAttribute("user");
        modelAndView.addObject("cartList",cartService.findAllCartByUserId(user.getId()));
        return  modelAndView;
    }
}

