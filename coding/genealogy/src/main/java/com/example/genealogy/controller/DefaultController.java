//package com.example.genealogy.controller;
//
//import com.example.genealogy.model.UserModel;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.servlet.ModelAndView;
//
//import java.util.Collection;
//
//@Controller
//public class DefaultController {
//    @GetMapping("/")
//    public String dashboard(Authentication authentication, @ModelAttribute(value="user") UserModel user) {
//        String result = "/account/login";
//        if(authentication != null) {
//            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//            for (GrantedAuthority authority : authorities) {
//                if (authority.getAuthority()
//                        .equals("ROLE_ADMIN")) {
//                    result = "/admin/home";
//                    break;
//                }
//                else if (authority.getAuthority()
//                        .startsWith("ROLE_USER")) {
//                    result = "/genealogy/home";
//                    break;
//                }
//            }
//        }
//        return result;
//    }
//
//}
