package com.example.genealogy.controller;

import com.example.genealogy.repository.GenealogyRepository;
import com.example.genealogy.repository.NodeMemberRepository;
import com.example.genealogy.repository.PedigreeRepository;
import com.example.genealogy.repository.UserRepository;
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
