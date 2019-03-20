package com.vuongpq2.datn.rest;

import com.vuongpq2.datn.data.Enum.Permission;
import com.vuongpq2.datn.data.model.DGenealogyModel;
import com.vuongpq2.datn.model.GenealogyModel;
import com.vuongpq2.datn.model.UserModel;
import com.vuongpq2.datn.model.UserPermissionModel;
import com.vuongpq2.datn.repository.PedigreeRepository;
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

import javax.jws.WebParam;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

    @GetMapping(value = "/rest/genealogy/list", produces = "application/json")
    public Collection<DGenealogyModel> getAll(Principal principal) {
        System.out.println("username" + principal.getName());
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
            item.setHistory(HtmlUtils.htmlEscape(his));
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
        if(getPermission(principal.getName(), id) == Permission.ADMIN.getCode()) {
            userPermissionRepository.deleteAllByGenealogyId(id);
            pedigreeRepository.deleteAllByGenealogyModel(genealogyService.findById(id).get());
            genealogyService.delete(id);

        }
        return new ResponseEntity<>("success" , HttpStatus.OK);
    }

    @PostMapping(value = "/rest/genealogy/unregister", produces = "application/json")
    public ResponseEntity<?> unRegisterGenealogy(@RequestParam(value = "idGenealogy", required = false, defaultValue = "") int idGenealogy, Principal principal) {
        UserModel userModel = userRepository.findByEmail(principal.getName());
        List<UserPermissionModel> userPermissionModels = userPermissionRepository.findByUserAndGenealogy_Id(userModel, idGenealogy);
        userPermissionRepository.deleteAll(userPermissionModels);
        return new ResponseEntity<>("success" , HttpStatus.OK);
    }

    public int getPermission(String email, int idGenealogy) {
        UserModel userModel = userRepository.findByEmail(email);
        UserPermissionModel userPermissionModel = userPermissionRepository.findTopByUserAndGenealogy_Id(userModel, idGenealogy);
        if(userPermissionModel != null) {
            return userPermissionModel.getPermission().getCode();
        }
        return -1;
    }
}
