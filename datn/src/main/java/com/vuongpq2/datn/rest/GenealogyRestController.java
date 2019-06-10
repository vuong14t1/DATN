package com.vuongpq2.datn.rest;

import com.vuongpq2.datn.data.Enum.Permission;
import com.vuongpq2.datn.data.model.DGenealogyModel;
import com.vuongpq2.datn.model.*;
import com.vuongpq2.datn.repository.PedigreeRepository;
import com.vuongpq2.datn.repository.PermissionRepository;
import com.vuongpq2.datn.repository.UserPermissionRepository;
import com.vuongpq2.datn.repository.UserRepository;
import com.vuongpq2.datn.service.GenealogyService;
import com.vuongpq2.datn.service.PedigreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.HtmlUtils;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
@Transactional
public class GenealogyRestController {
    @Autowired
    GenealogyService genealogyService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserPermissionRepository userPermissionRepository;
    @Autowired
    PedigreeRepository pedigreeRepository;
    @Autowired
    PermissionRepository permissionRepository;

    @GetMapping(value = "/rest/genealogy/list", produces = "application/json")
    public Collection<DGenealogyModel> getAll(Principal principal) {
        UserModel userModel = userRepository.findByEmail(principal.getName());
        List<GenealogyModel> all = genealogyService.findAllByEmailUser(principal.getName());
        List<DGenealogyModel> result = new ArrayList<>();
        for (GenealogyModel gene : all) {
            DGenealogyModel item = new DGenealogyModel();
            item.setId(gene.getId());
            item.setName(gene.getName());
            String his = gene.getHistory();
            int length = Math.min(his.length(), 50);
            his = his.substring(0, length);
            item.setHistory(his);
            UserPermissionModel userPermissionModel = userPermissionRepository.findTopByUserAndGenealogy_Id(userModel, gene.getId());
            if (userPermissionModel != null) {
                item.setPermission(userPermissionModel.getPermission().getCode());
            }
            result.add(item);
        }
        return result;
    }
    @PostMapping(value = "/rest/genealogy/delete/{id}", produces = "application/json")
    public ResponseEntity<?> deleteGenealogy(@PathVariable(value = "id", required = false) int id, Principal principal){
        if(principal == null) {
            return new ResponseEntity<>("success" , HttpStatus.NOT_FOUND);
        }

        userPermissionRepository.deleteAllByGenealogyId(id);
        pedigreeRepository.deleteAllByGenealogyModel(genealogyService.findById(id).get());
        genealogyService.delete(id);
        return new ResponseEntity<>("success" , HttpStatus.OK);
    }

    @PostMapping(value = "/rest/genealogy/find", produces = "application/json")
    public Collection<DGenealogyModel> findGenealogy(Principal principal, @RequestParam(value = "textSearch", required = false, defaultValue = "") String strSearch) {
        UserModel userModel = userRepository.findByEmail(principal.getName());
        List<GenealogyModel> genealogyModels = genealogyService.findAllByLike("%" + strSearch + "%");
        List<DGenealogyModel> result = new ArrayList<>(1);
        for(GenealogyModel g: genealogyModels) {
            DGenealogyModel dGenealogyModel = new DGenealogyModel();
            dGenealogyModel.setId(g.getId());
            String his = g.getHistory();
            int length = Math.min(his.length(), 50);
            his = his.substring(0, length);
            dGenealogyModel.setHistory(his);
            dGenealogyModel.setName(g.getName());
            UserPermissionModel userPermissionModel = userPermissionRepository.findTopByUserAndGenealogy_Id(userModel, g.getId());
            if(userPermissionModel != null) {
                dGenealogyModel.setPermission(userPermissionModel.getPermission().getCode());
            }
            result.add(dGenealogyModel);
        }
        return result;
    }

    @PostMapping(value = "/rest/genealogy/register", produces = "application/json")
    public ResponseEntity<?> registerGenealogy (Principal principal, @RequestParam(value = "idGenealogy", required = false) int idGenealogy) {
        Optional<GenealogyModel> genealogyModel = genealogyService.findById(idGenealogy);
        UserModel userModel = userRepository.findByEmail(principal.getName());
        PermissionModel permissionModel = permissionRepository.findByCode(Permission.REGISTERED.getCode());
        if(permissionModel == null) {
            PermissionModel newPer = new PermissionModel();
            newPer.setCode(Permission.REGISTERED.getCode());
            newPer.setName("REGISTER");
            permissionRepository.save(newPer);
        }
        permissionModel = permissionRepository.findByCode(Permission.REGISTERED.getCode());
        UserPermissionModel userPermissionModel = new UserPermissionModel();
        userPermissionModel.setPermission(permissionModel);
        userPermissionModel.setUser(userModel);
        userPermissionModel.setGenealogyModel(genealogyModel.get());
        userPermissionRepository.save(userPermissionModel);
        return new ResponseEntity<>("success" , HttpStatus.OK);
    }

    @PostMapping(value = "/rest/genealogy/unregister", produces = "application/json")
    public ResponseEntity<?> unRegisterGenealogy (Principal principal, @RequestParam(value = "idGenealogy", required = false) int idGenealogy) {
        UserModel userModel = userRepository.findByEmail(principal.getName());
        UserPermissionModel userPermissionModel = userPermissionRepository.findTopByUserAndGenealogy_Id(userModel, idGenealogy);
        userPermissionRepository.delete(userPermissionModel);
        return new ResponseEntity<>("success" , HttpStatus.OK);
    }

    public  int getPermission(String email, int idGenealogy) {
        UserModel userModel = userRepository.findByEmail(email);
        UserPermissionModel userPermissionModel = userPermissionRepository.findTopByUserAndGenealogy_Id(userModel, idGenealogy);
        if(userPermissionModel != null) {
            return userPermissionModel.getPermission().getCode();
        }
        return -1;
    }


}
