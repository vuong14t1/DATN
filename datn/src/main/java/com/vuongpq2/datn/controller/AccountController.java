package com.vuongpq2.datn.controller;

import com.vuongpq2.datn.model.UserModel;
import com.vuongpq2.datn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AccountController {

    @Autowired
    private UserService userService;



    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @GetMapping("/confirm")
    public String confirm() {
        return "/account/confirm";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(
            @ModelAttribute(value = "user")
                    UserModel user, HttpServletRequest request) {
        System.out.println(user.toString());
        ModelAndView mv;
        mv = new ModelAndView("/account/login");
//        }
        return mv;
    }

}
