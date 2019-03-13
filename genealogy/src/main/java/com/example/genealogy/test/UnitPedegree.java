package com.example.genealogy.test;

import com.example.genealogy.model.GenealogyModel;
import com.example.genealogy.model.PedigreeModel;
import com.example.genealogy.service.GenealogyService;
import com.example.genealogy.service.PedigreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class UnitPedegree {
    @Autowired
    private GenealogyService genealogyService;
    @Autowired
    private PedigreeService pedigreeService;

    @GetMapping("/pe/create")
    public ModelAndView create() {
        Optional<GenealogyModel> genealogyModel = genealogyService.findById(4);
        PedigreeModel pedigreeModel = new PedigreeModel();
        pedigreeModel.setHistory("CHI PHAN QUANG");
        pedigreeModel.setName("PHAN QUANG");
        pedigreeService.add(pedigreeModel, genealogyModel.get().getId());
        return new ModelAndView("home");
    }
}
