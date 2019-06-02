package com.vuongpq2.datn.controller;

import com.vuongpq2.datn.model.UserModel;
import com.vuongpq2.datn.repository.GenealogyRepository;
import com.vuongpq2.datn.repository.NodeMemberRepository;
import com.vuongpq2.datn.repository.PedigreeRepository;
import com.vuongpq2.datn.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

@Controller
public class AdminController {

    @Autowired
    GenealogyRepository genealogyRepository;

    @Autowired
    PedigreeRepository pedigreeRepository;

    @Autowired
    NodeMemberRepository nodeMemberRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/admin")
    public ModelAndView admin() {
        return new ModelAndView("/admin/home");
    }

    @RequestMapping(value = "/admin/account", method = RequestMethod.GET)
    public ModelAndView login(Authentication authentication , @ModelAttribute(value = "user") UserModel user, HttpServletRequest request) {
        if(!isHavePermission(authentication)) return notHavePermisstion();
        ModelAndView mv;
        mv = new ModelAndView("/admin/account");
        mv.addObject("adminEmail", user.getEmail());
        mv.addObject("idGenealogy", user.getEmail());
        return mv;
    }

    @RequestMapping(value = "/admin/genealogy", method = RequestMethod.GET)
    public ModelAndView genealogy(Authentication authentication ,@ModelAttribute(value = "user") UserModel user, HttpServletRequest request) {
        if(!isHavePermission(authentication)) return notHavePermisstion();
        ModelAndView mv;
        mv = new ModelAndView("/admin/genealogy");
        return mv;
    }
    private boolean isHavePermission( Authentication authentication){
        if(authentication != null){
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            for (GrantedAuthority authority : authorities) {
                if (authority.getAuthority()
                        .equals("ROLE_ADMIN")) {
                    return true;
                }
                else if (authority.getAuthority()
                        .startsWith("ROLE_USER")) {
                    return false;
                }
            }
        }
        return false;
    }

    private ModelAndView notHavePermisstion() {
        ModelAndView mv;
        String result ="redirect:/";
        mv = new ModelAndView(result);
        return mv;
    }

}
