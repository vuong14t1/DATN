package com.example.genealogy.test;

import com.example.genealogy.model.GenealogyModel;
import com.example.genealogy.service.GenealogyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class TestController {
    @Autowired
    private GenealogyService genealogyService;


    @GetMapping("/confirm")
    public String confirm() {
        return "/admin/home";
    }

}
