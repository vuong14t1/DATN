package com.example.genealogy.test;

import com.example.genealogy.model.DescriptionMemberModel;
import com.example.genealogy.model.NodeMemberModel;
import com.example.genealogy.model.PedigreeModel;
import com.example.genealogy.service.GenealogyService;
import com.example.genealogy.service.NodeMemberService;
import com.example.genealogy.service.PedigreeService;
import jxl.Sheet;
import jxl.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.util.Optional;

@Controller
public class UnitNodeMember {
    @Autowired
    private GenealogyService genealogyService;

    @Autowired
    private PedigreeService pedigreeService;

    @Autowired
    private NodeMemberService nodeMemberService;


}
