package com.example.genealogy.controller;

import com.example.genealogy.model.UserModel;
import com.example.genealogy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AccountController {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/genealogy/user")
    public void index1() {
        System.out.println("/genealogy/user");
    }

    @GetMapping("/admin")
    public String admin() {
        System.out.println("admin/genealogy");
        return "admin/genealogy";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(
            @ModelAttribute(value = "user")
                    UserModel user, HttpServletRequest request) {
        System.out.println(user.toString());
        ModelAndView mv;
        mv = new ModelAndView("account/login");
        return mv;
    }

    @RequestMapping(value = "/account/profile", method = RequestMethod.GET)
    public ModelAndView profile(HttpServletRequest request) {
        UserModel userModel = userService.findUserByEmail(request.getUserPrincipal().getName());
        ModelAndView mv = new ModelAndView("account/profile");
        mv.addObject("user", userModel);
        return mv;
    }
}
