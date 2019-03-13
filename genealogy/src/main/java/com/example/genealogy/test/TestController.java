package com.example.genealogy.test;

import com.example.genealogy.model.GenealogyModel;
import com.example.genealogy.service.GenealogyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class TestController {
    @Autowired
    private GenealogyService genealogyService;
    @GetMapping("/create")
    public ModelAndView create () {
        GenealogyModel genealogyModel = new GenealogyModel();
        genealogyModel.setHistory("Day la gi pha ho Phan");
        genealogyModel.setName("DONG HO PHAN");
        genealogyModel.setThuyTo("ABC");
        genealogyService.create(genealogyModel, "vuongpq2");
        return new ModelAndView("home");
    }

    @GetMapping("/update")
    public ModelAndView update () {
        Optional<GenealogyModel> genealogyModel = genealogyService.findById(1);
        genealogyModel.get().setName("DONG TOC PHAN 1");
        genealogyService.update(genealogyModel.get());
        return new ModelAndView("home");
    }

    @GetMapping("/delete")
    public ModelAndView delete () {
        Optional<GenealogyModel> genealogyModel = genealogyService.findById(1);
        genealogyService.delete(genealogyModel.get());
        return new ModelAndView("home");
    }

    @GetMapping("/confirm")
    public String confirm() {
        return "/admin/home";
    }

}
