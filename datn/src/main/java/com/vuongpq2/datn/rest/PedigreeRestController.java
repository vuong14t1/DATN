package com.vuongpq2.datn.rest;

import com.vuongpq2.datn.model.PedigreeModel;
import com.vuongpq2.datn.model.UserModel;
import com.vuongpq2.datn.model.UserPermissionModel;
import com.vuongpq2.datn.repository.PedigreeRepository;
import com.vuongpq2.datn.repository.UserPermissionRepository;
import com.vuongpq2.datn.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collection;

@RestController
public class PedigreeRestController {
    @Autowired
    PedigreeRepository pedigreeRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserPermissionRepository userPermissionRepository;


    @GetMapping(value = "/rest/pedigree/list/{idGenealogy}", produces = "application/json")
    public Collection<PedigreeModel> getListPedigreeByIdGenealogy(Principal principal, @PathVariable(value = "idGenealogy", required = false) int idGenealogy) {
        if (principal == null) {
            return null;
        }
        return pedigreeRepository.findAllByGenealogyModel_Id(idGenealogy);
    }

    @PostMapping(value = "/rest/genealogy/{idGenealogy}/pedigree/{idPedigree}/delete", produces = "application/json")
    public ResponseEntity<?> deletePedigree(
            @PathVariable(value = "idGenealogy") int idGenealogy,
            @PathVariable(value = "idPedigree") int idPedigree,
            Principal principal
    ) {
        if (!isAdminPermission(principal, idGenealogy)) return notHavePermisstion();
        pedigreeRepository.deleteById(idPedigree);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @PostMapping(value = "/rest/pedigree/merge-pedigree", produces = "application/json")
    public ResponseEntity<?> mergePedigree (Principal principal,
                                            @RequestParam(value = "idGenealogy") int idGenealogy,
                                            @RequestParam(value = "inputSelectPedigree") int inputSelectPedigree,
                                            @RequestParam(value = "inputPedigreeTo") int inputPedigreeTo

                                            ) {

        return new ResponseEntity<>("1", HttpStatus.OK);
    }

    private boolean isAdminPermission(Principal principal, int idGenealogy) {
        UserModel byEmail = userRepository.findByEmail(principal.getName());
        if (byEmail == null) return false;
        UserPermissionModel byUserAndGenealogy_id = userPermissionRepository.findTopByUserAndGenealogy_Id(byEmail, idGenealogy);
        return byUserAndGenealogy_id.getPermission().getName().toLowerCase().equals("admin");
    }

    private ResponseEntity<?> notHavePermisstion() {
        return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
    }

}
