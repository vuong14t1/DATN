package com.vuongpq2.datn.controller;

import com.vuongpq2.datn.data.Enum.Permission;
import com.vuongpq2.datn.model.GenealogyModel;
import com.vuongpq2.datn.model.PedigreeModel;
import com.vuongpq2.datn.model.UserModel;
import com.vuongpq2.datn.model.UserPermissionModel;
import com.vuongpq2.datn.repository.UserPermissionRepository;
import com.vuongpq2.datn.repository.UserRepository;
import com.vuongpq2.datn.service.GenealogyService;
import com.vuongpq2.datn.service.PedigreeService;
import com.vuongpq2.datn.utils.PermissionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.swing.text.html.Option;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

@Controller
public class PedigreeController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PedigreeService pedigreeService;
    @Autowired
    UserPermissionRepository userPermissionRepository;
    @Autowired
    GenealogyService genealogyService;

    @GetMapping(value = "/genealogy/{idGenealogy}/pedigree")
    public ModelAndView getListPedigreeByGenealogyId(Principal principal, @PathVariable(value = "idGenealogy", required = false) int idGenealogy) {
        UserModel userModel = userRepository.findByEmail(principal.getName());
        if (userModel == null) {
            return new ModelAndView("/genealogy");
        }
        UserPermissionModel userPermissionModel = userPermissionRepository.findTopByUserAndGenealogy_Id(userModel, idGenealogy);
        if (userPermissionModel != null) {
            Permission permission = Permission.byCode(userPermissionModel.getPermission().getCode());
            if (PermissionUtils.isCanViewPedigree(permission)) {
                ModelAndView mv = new ModelAndView("/genealogy/pedigree");
                mv.addObject("genealogy", userPermissionModel.getGenealogyModel());
                mv.addObject("idGenealogy", idGenealogy);
                mv.addObject("idPermission", permission.getCode());
                return mv;
            } else {
                return new ModelAndView("/genealogy");
            }
        }
        return new ModelAndView("/genealogy");
    }

    @GetMapping(value = "/genealogy/{idGenealogy}/pedigree/add")
    public ModelAndView getAddPedigree(Principal principal, @PathVariable(value = "idGenealogy", required = false) int idGenealogy) {
        if (principal == null) {
            return new ModelAndView("/genealogy");
        }
        PedigreeModel pedigreeModel = new PedigreeModel();
        pedigreeModel.setName("");
        pedigreeModel.setHistory("");
        ModelAndView mv = new ModelAndView("/genealogy/pedigree-add");
        mv.addObject("idGenealogy", idGenealogy);
        mv.addObject("pedigree", pedigreeModel);
        return mv;
    }

    @PostMapping(value = "/genealogy/{idGenealogy}/pedigree/add", produces = "application/json")
    public ModelAndView postAddPedigree(Principal principal, @Valid @ModelAttribute(value = "pedigree") PedigreeModel pedigreeModel, @PathVariable(name = "idGenealogy") int idGenealogy) {
        ModelAndView mv = new ModelAndView("/genealogy/pedigree-detail");
        Optional<GenealogyModel> genealogyModel = genealogyService.findById(idGenealogy);
        pedigreeService.add(pedigreeModel, idGenealogy);

        UserModel user = userRepository.findByEmail(principal.getName());
        UserPermissionModel userPermissionModel = userPermissionRepository.findTopByUserAndGenealogy_Id(user, idGenealogy);
        mv.addObject("idPermission", userPermissionModel.getPermission().getCode());
        mv.addObject("genealogy", genealogyModel.get());
        mv.addObject("pedigree", pedigreeModel);
        return mv;
    }

    @GetMapping(value = "/genealogy/{idGenealogy}/pedigree/{idPedigree}/detail", produces = "application/json")
    public ModelAndView getDetailPedigree(Principal principal, @PathVariable(value = "idGenealogy", required = false) int idGenealogy, @PathVariable(value = "idPedigree", required = false) int idPedigree) {
        ModelAndView mv = new ModelAndView("/genealogy/pedigree-detail");
        if (principal == null) {
            return new ModelAndView("/genealogy");
        }
        UserModel userModel = userRepository.findByEmail(principal.getName());
        Optional<GenealogyModel> genealogyModel = genealogyService.findById(idGenealogy);
        Optional<PedigreeModel> pedigreeModel = pedigreeService.findById(idPedigree);
        UserPermissionModel userPermissionModel = userPermissionRepository.findTopByUserAndGenealogy_Id(userModel, idGenealogy);
        mv.addObject("idPermission", userPermissionModel.getPermission().getCode());
        mv.addObject("pedigree", pedigreeModel.get());
        mv.addObject("genealogy", genealogyModel.get());
        return mv;
    }

    @GetMapping(value = "/genealogy/{idGenealogy}/pedigree/{idPedigree}/edit", produces = "application/json")
    public ModelAndView getEditPedigree(Principal principal, @PathVariable(value = "idGenealogy", required = false) int idGenealogy, @PathVariable(value = "idPedigree", required = false) int idPedigree) {
        if (principal == null) {
            return new ModelAndView("/genealogy");
        }
        ModelAndView mv = new ModelAndView("/genealogy/pedigree-edit");
        UserModel userModel = userRepository.findByEmail(principal.getName());
        UserPermissionModel userPermissionModel = userPermissionRepository.findTopByUserAndGenealogy_Id(userModel, idGenealogy);
        Permission permission = Permission.byCode(userPermissionModel.getPermission().getCode());
        Optional<GenealogyModel> genealogyModel = genealogyService.findById(idGenealogy);
        Optional<PedigreeModel> pedigreeModel = pedigreeService.findById(idPedigree);
        if (PermissionUtils.isCanEditPedigree(permission)) {
            mv.addObject("genealogy", genealogyModel.get());
            mv.addObject("pedigree", pedigreeModel.get());
            return mv;
        } else {
            return new ModelAndView("/genealogy");
        }
    }

    @PostMapping(value = "/genealogy/{idGenealogy}/pedigree/{idPedigree}/edit", produces = "application/json")
    public ModelAndView postEditPedigree(Principal principal, @Valid @ModelAttribute(value = "pedigree") PedigreeModel pedigreeModel, @PathVariable(value = "idGenealogy", required = false) int idGenealogy, @PathVariable(value = "idPedigree", required = false) int idPedigree) {
        if(principal == null) {
            return new ModelAndView("/genealogy");
        }
        Optional<PedigreeModel> pedigreeModel1 = pedigreeService.findById(idPedigree);
        pedigreeModel1.get().setHistory(pedigreeModel.getHistory());
        pedigreeModel1.get().setName(pedigreeModel.getName());
        pedigreeService.update(pedigreeModel1.get());
        UserModel userModel = userRepository.findByEmail(principal.getName());
        UserPermissionModel userPermissionModel = userPermissionRepository.findTopByUserAndGenealogy_Id(userModel, idGenealogy);
        if (userPermissionModel != null) {
            Permission permission = Permission.byCode(userPermissionModel.getPermission().getCode());
            if (PermissionUtils.isCanViewPedigree(permission)) {
                ModelAndView mv = new ModelAndView("/genealogy/pedigree");
                mv.addObject("genealogy", userPermissionModel.getGenealogyModel());
                mv.addObject("idGenealogy", idGenealogy);
                mv.addObject("idPermission", permission.getCode());
                return mv;
            } else {
                return new ModelAndView("/genealogy");
            }
        }
        return new ModelAndView("/genealogy");
    }
}
