package com.vuongpq2.datn.controller;

import com.vuongpq2.datn.data.Enum.Permission;
import com.vuongpq2.datn.model.DescriptionMemberModel;
import com.vuongpq2.datn.model.PermissionModel;
import com.vuongpq2.datn.model.UserModel;
import com.vuongpq2.datn.model.UserPermissionModel;
import com.vuongpq2.datn.repository.DescriptionMemberRepository;
import com.vuongpq2.datn.repository.UserPermissionRepository;
import com.vuongpq2.datn.repository.UserRepository;
import com.vuongpq2.datn.service.GenealogyService;
import com.vuongpq2.datn.service.NodeMemberService;
import com.vuongpq2.datn.utils.PermissionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class MemberController {
    @Autowired
    private NodeMemberService nodeMemberService;

    @Autowired
    private DescriptionMemberRepository descriptionMemberRepository;

    @Autowired
    private GenealogyService genealogyService;

    @Autowired
    private UserPermissionRepository userPermissionRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping(value = "/genealogy/{idGenealogy}/pedigree/{idPedigree}/member-tree")
    public ModelAndView getMemberTree(Principal principal,
                                      @PathVariable(value = "idGenealogy") int idGenealogy,
                                      @PathVariable(value = "idPedigree") int idPedigree
                                      ) {
        ModelAndView mv;
        UserModel userModel = userRepository.findByEmail(principal.getName());
        UserPermissionModel userPermissionModel = userPermissionRepository.findTopByUserAndGenealogy_Id(userModel, idGenealogy);
        if(userPermissionModel != null ) {
            PermissionModel permission = userPermissionModel.getPermission();
            if(PermissionUtils.isCanViewPedigree(Permission.byCode(permission.getCode()))) {
                mv = new ModelAndView("/genealogy/pedigree-tree-people");
                mv.addObject("idGenealogy", userPermissionModel.getGenealogyModel().getId());
                mv.addObject("idPedigree", idPedigree);
                mv.addObject("idPermission", permission.getCode());
                return mv;
            }
        }
        mv = new ModelAndView();
        mv.setViewName("redirect:/genealogy");
        return mv;
    }
}
