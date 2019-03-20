package com.vuongpq2.datn.controller;

import com.vuongpq2.datn.repository.GenealogyRepository;
import com.vuongpq2.datn.repository.NodeMemberRepository;
import com.vuongpq2.datn.repository.PedigreeRepository;
import com.vuongpq2.datn.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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
    public String admin() {
        return "/admin/home";
    }
}
