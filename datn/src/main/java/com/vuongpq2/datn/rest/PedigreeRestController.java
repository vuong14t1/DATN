package com.vuongpq2.datn.rest;

import com.vuongpq2.datn.data.Enum.Relation;
import com.vuongpq2.datn.data.GioiTinh;
import com.vuongpq2.datn.model.NodeMemberModel;
import com.vuongpq2.datn.model.PedigreeModel;
import com.vuongpq2.datn.model.UserModel;
import com.vuongpq2.datn.model.UserPermissionModel;
import com.vuongpq2.datn.repository.NodeMemberRepository;
import com.vuongpq2.datn.repository.PedigreeRepository;
import com.vuongpq2.datn.repository.UserPermissionRepository;
import com.vuongpq2.datn.repository.UserRepository;
import com.vuongpq2.datn.service.NodeMemberService;
import com.vuongpq2.datn.utils.MyUltils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
public class PedigreeRestController {
    @Autowired
    PedigreeRepository pedigreeRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserPermissionRepository userPermissionRepository;
    @Autowired
    NodeMemberService nodeMemberService;
    @Autowired
    NodeMemberRepository nodeMemberRepository;


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
                                            @RequestParam(value = "idGenealogy") String idGenealogy,
                                            @RequestParam(value = "inputSelectPedigree") String inputSelectPedigree,
                                            @RequestParam(value = "inputPedigreeTo") String inputPedigreeTo,
                                            @RequestParam(value = "inputParent") String inputParent,
                                            @RequestParam(value = "inputMotherOrFather") String inputMotherOrFather,
                                            @RequestParam(value = "inputIndexChild") String inputIndexChild

                                            ) {
        if (inputMotherOrFather.equals("")) {
            inputMotherOrFather = "-1";
        }

        if (inputIndexChild.equals("")) {
            inputIndexChild = "-1";
        }
        //kiem tra neu parentTo la vo hoac chong (co nghia la parent la con cua 1 nut nua) -> chuyen parent la nut cao nhat -> motherorfather auto la nut parentTo do
        Optional<NodeMemberModel> parent = nodeMemberService.findById(Integer.parseInt(inputParent));
        if(parent.get().getRelation() == Relation.VO.ordinal() || parent.get().getRelation() == Relation.CHONG.ordinal()) {
            Optional<NodeMemberModel> realParent = nodeMemberService.findById(Integer.parseInt(MyUltils.getIdParentByPathKey(parent.get().getPatchKey())));
            if(realParent.isPresent()) {
                inputMotherOrFather = parent.get().getId().toString();
                parent = realParent;
            }
        }
        Optional<NodeMemberModel> motherOrFather = nodeMemberService.findById(Integer.parseInt(inputMotherOrFather));
        Optional<PedigreeModel> pedigreeModelSelect = pedigreeRepository.findById(Integer.parseInt(inputSelectPedigree));
        Optional<PedigreeModel> pedigreeModelTo = pedigreeRepository.findById(Integer.parseInt(inputPedigreeTo));
        //lay tat ca node cua select
        List<NodeMemberModel> nodeMemberModelListSelect = nodeMemberService.findAllByPedigreeAndPatchKeyStartsWith(pedigreeModelSelect.get(), "r");
        String keyParent = "r";
        if(parent.isPresent()) {
            keyParent = NodeMemberModel.getPathkeyByParent(parent.get());
        }

        List<NodeMemberModel> parentSelectList = nodeMemberService.findAllByPedigreeAndPatchKey(pedigreeModelSelect.get(), "r");
        NodeMemberModel parentSelect = parentSelectList.get(0);
        String keyParentSelectRoot = NodeMemberModel.getPathkeyByParent(parentSelect);
        for (NodeMemberModel nodeMemberModel: nodeMemberModelListSelect) {
            String key = nodeMemberModel.getPatchKey();
            key = key.replace(keyParentSelectRoot, keyParent + "_" + parentSelect.getId());
            nodeMemberModel.setPatchKey(key);
            nodeMemberModel.setPedigree(pedigreeModelTo.get());
            nodeMemberRepository.save(nodeMemberModel);
        }
        //update parent Select root
        parentSelect.setPatchKey(keyParent);
        parentSelect.setMotherFatherId(motherOrFather.isPresent()? motherOrFather.get().getId(): -1);
        parentSelect.setChildIndex(Integer.parseInt(inputIndexChild));
        parentSelect.setPedigree(pedigreeModelTo.get());
        parentSelect.setRelation(parent.get().getGender() == GioiTinh.NAM.ordinal() ? Relation.CHA.ordinal(): Relation.ME.ordinal());
        parentSelect.setParent(parent.isPresent()? parent.get(): null);
        nodeMemberRepository.save(parentSelect);
        //xoa pedigree cu
        pedigreeRepository.deleteById(Integer.parseInt(inputSelectPedigree));
        //update permission cua pedigree cu
        List<UserPermissionModel> userPermissionModels  = userPermissionRepository.findByGenealogy_IdAndPedigree_Id(Integer.parseInt(idGenealogy), Integer.parseInt(inputSelectPedigree));
        for(UserPermissionModel userPermissionModel: userPermissionModels) {
            userPermissionModel.setPedigreeModel(pedigreeModelTo.get());
            userPermissionRepository.save(userPermissionModel);
        }
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
