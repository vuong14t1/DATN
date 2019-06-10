package com.vuongpq2.datn.controller;

import com.vuongpq2.datn.data.Enum.Permission;
import com.vuongpq2.datn.model.*;
import com.vuongpq2.datn.repository.PermissionRepository;
import com.vuongpq2.datn.repository.UserPermissionRepository;
import com.vuongpq2.datn.repository.UserRepository;
import com.vuongpq2.datn.service.GenealogyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Set;

@Controller
public class GenealogyController {
    @Autowired
    GenealogyService genealogyService;

    @Autowired
    UserPermissionRepository userPermissionRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private PermissionRepository permissionRepository;

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
        GenealogyModel genealogy = new GenealogyModel();
        genealogy.setName(".");
        genealogy.setHistory(".");
        genealogy.setThuyTo(".");
        mv.addObject("genealogy", genealogy);
        return mv;
    }

    @PostMapping(value = "/genealogy/add")
    public ModelAndView createGenealogy(Principal principal,
                                        @RequestParam(name = "name", required = true, defaultValue = "") String name,
                                        @RequestParam(name = "history", required = true, defaultValue = "") String history,
                                        @RequestParam(name = "thuyTo", required = true, defaultValue = "") String thuyTo) {
        GenealogyModel genealogyModel = new GenealogyModel();
        genealogyModel.setName(name);
        genealogyModel.setHistory(history);
        genealogyModel.setThuyTo(thuyTo);
        genealogyService.create(genealogyModel, principal.getName());
        ModelAndView mv = new ModelAndView("/genealogy/detail");
        mv.addObject("genealogy", genealogyModel);
        List<UserModel> userModels = userRepository.findAll();
        for(UserModel userModel: userModels) {
            if(isAdminHeThong(userModel)) {
                UserPermissionModel userPermissionModel = new UserPermissionModel();
                PermissionModel permissionModel = permissionRepository.findByCode(Permission.ADMIN.getCode());
                if(permissionModel == null) {
                    permissionModel = new PermissionModel();
                    permissionModel.setName("ADMIN");
                    permissionModel.setCode(Permission.ADMIN.getCode());
                    permissionRepository.save(permissionModel);
                }
                userPermissionModel.setGenealogyModel(genealogyModel);
                userPermissionModel.setUser(userModel);
                userPermissionModel.setPermission(permissionModel);
                userPermissionRepository.save(userPermissionModel);
            }
        }
        return mv;
    }

    public boolean isAdminHeThong (UserModel userModel) {
        Set<RoleModel> roleModelSet = userModel.getRoles();
        for(RoleModel roleModel: roleModelSet) {
            if(roleModel.getName().equals("ADMIN")) {
                return true;
            }
        }
        return false;
    }

    @GetMapping(value = "/genealogy/{id}")
    public ModelAndView showDetail(Principal principal, @PathVariable(value = "id", required = false) int id) {
        ModelAndView mv;
        if (principal == null) {
            System.out.println("principal null show detail");
            return new ModelAndView("/account/login");
        } else {
            UserModel userModel = userRepository.findByEmail(principal.getName());
            UserPermissionModel userPermissionModel = userPermissionRepository.findTopByUserAndGenealogy_Id(userModel, id);
            if (userPermissionModel != null) {
                Permission permission = Permission.byCode(userPermissionModel.getPermission().getCode());
                if (permission == Permission.ADMIN || permission == Permission.MOD || permission == Permission.REGISTERED || permission == Permission.MEMBER) {
                    mv = new ModelAndView("/genealogy/detail");
                    mv.addObject("idPermission", permission.getCode());
                    mv.addObject("genealogy", userPermissionModel.getGenealogyModel());
                    return mv;
                }
            }
        }
        mv = new ModelAndView();
        mv.setViewName("redirect:/genealogy");
        return mv;
    }

    @GetMapping(value = "/genealogy/{id}/edit")
    public ModelAndView getEdit(Principal principal, @PathVariable(value = "id", required = false) int id) {
        ModelAndView mv;
        if (principal == null) {
            new ModelAndView("/genealogy");
        }
        UserModel userModel = userRepository.findByEmail(principal.getName());
        UserPermissionModel userPermissionModel = userPermissionRepository.findTopByUserAndGenealogy_Id(userModel, id);
        if (userPermissionModel != null) {
            Permission permission = Permission.byCode(userPermissionModel.getPermission().getCode());
            if (permission == Permission.ADMIN || permission == Permission.MOD) {
                mv = new ModelAndView("/genealogy/detail-edit");
                mv.addObject("genealogy", userPermissionModel.getGenealogyModel());
                return mv;
            }
        }
        mv = new ModelAndView();
        mv.setViewName("redirect:/genealogy");
        return mv;
    }

    @PostMapping(value = "/genealogy/{id}/edit")
    public ModelAndView postEdit(Principal principal, @PathVariable(value = "id", required = false) int id, @Valid @ModelAttribute(value = "genealogy")
            GenealogyModel genealogy) {
        ModelAndView mv;
        if (principal == null) {
            new ModelAndView("/genealogy");
        }
        UserModel userModel = userRepository.findByEmail(principal.getName());
        UserPermissionModel userPermissionModel = userPermissionRepository.findTopByUserAndGenealogy_Id(userModel, id);
        if (userPermissionModel != null) {
            Permission permission = Permission.byCode(userPermissionModel.getPermission().getCode());
            if (permission == Permission.ADMIN || permission == Permission.MEMBER || permission == Permission.MOD) {
                genealogy.setId(id);
                genealogyService.update(genealogy);
                mv = new ModelAndView("/genealogy/detail");
                mv.addObject("genealogy", genealogy);
                return mv;
            }
        }
        mv = new ModelAndView();
        mv.setViewName("redirect:/genealogy");
        return mv;
    }

    @GetMapping(value = "/genealogy/find")
    public ModelAndView getFindGenealogy(Principal principal) {
        if(principal == null) {
            return new ModelAndView("/genealogy");
        }
        return new ModelAndView("/genealogy/find");
    }

    @RequestMapping(value = "/genealogy/detail", method = RequestMethod.GET)
    public ModelAndView detail(
            Principal principal,
            HttpServletRequest request) {
        ModelAndView mv;
        if (principal == null) {
            mv = new ModelAndView("/account/login");
            return mv;
        } else {
            mv = new ModelAndView("/genealogy/home");
            return mv;
        }
    }

    @GetMapping(value = "/genealogy/{idGenealogy}/member-user")
    public ModelAndView getViewMemberInGenealogy (Principal principal, @PathVariable(value = "idGenealogy") int idGenealogy) {
        UserModel userModel = userRepository.findByEmail(principal.getName());
        UserPermissionModel userPermissionModel = userPermissionRepository.findTopByUserAndGenealogy_Id(userModel, idGenealogy);
        PermissionModel permissionModel = userPermissionModel.getPermission();
        ModelAndView mv = new ModelAndView("/genealogy/member-user");
        mv.addObject("idGenealogy", idGenealogy);
        mv.addObject("idPermission", permissionModel.getCode());
        mv.addObject("idUser", userModel.getId());
        return mv;
    }


}
