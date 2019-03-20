package com.example.genealogy.controller;

import com.example.genealogy.model.GenealogyModel;
import com.example.genealogy.model.UserModel;
import com.example.genealogy.repository.UserPermissionRepository;
import com.example.genealogy.repository.UserRepository;
import com.example.genealogy.service.GenealogyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
@Controller
public class GenealogyController {
    @Autowired
    GenealogyService genealogyService;

    @Autowired
    UserPermissionRepository userPermissionRepository;

    @Autowired
    UserRepository userRepository;
    @RequestMapping(value = "/genealogy", method = RequestMethod.GET)
    public ModelAndView home(Principal principal) {
        ModelAndView mv;
        if (principal == null) {
            return new ModelAndView("/account/login");
        } else {
            UserModel userModel = userRepository.findByEmail(principal.getName());
            return new ModelAndView("/genealogy/home");
        }
    }

    @GetMapping(value = "/genealogy/add")
    public ModelAndView add(Principal principal, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("/genealogy/detail-add");
        return mv;
    }

    @PostMapping(value = "/genealogy/add")
    public ModelAndView createGenealogy (Principal principal,
                                         @RequestParam(name = "name", required = true, defaultValue = "")String name,
                                         @RequestParam(name = "history", required = true, defaultValue = "")String history,
                                         @RequestParam(name = "thuyTo", required = true, defaultValue = "")String thuyTo) {
        GenealogyModel genealogyModel = new GenealogyModel();
        genealogyModel.setName(name);
        genealogyModel.setHistory(history);
        genealogyModel.setThuyTo(thuyTo);
        genealogyService.create(genealogyModel, principal.getName());
        ModelAndView mv = new ModelAndView("/genealogy/detail");
        mv.addObject("genealogy", genealogyModel);
        return mv;
    }
}
