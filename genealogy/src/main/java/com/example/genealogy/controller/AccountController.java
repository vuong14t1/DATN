package com.example.genealogy.controller;

import com.example.genealogy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccountController {

    @Autowired
    private UserService userService;

//    @Autowired
//    private BCryptPasswordEncoder bCryptPasswordEncoder;

    //    @RequestMapping(value = "/login", method = RequestMethod.GET)
//    public ModelAndView login(@ModelAttribute(value = "user")UserModel user, HttpServletRequest request) {
//        return new ModelAndView("account/login");
//    }
    @GetMapping("/")
    public String index() {
        return "home";
    }

    @GetMapping("/admin")
    public String admin() {
        System.out.println("abc");
        return "home";
    }

    @GetMapping("/403")
    public String accessDenied() {
        return "403";
    }

    @GetMapping("/login")
    public String getLogin() {
        return "home";
    }

}
