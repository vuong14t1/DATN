package com.vuongpq2.datn.rest;

import com.vuongpq2.datn.data.Enum.Relation;
import com.vuongpq2.datn.data.GioiTinh;
import com.vuongpq2.datn.data.model.DInfoMerger;
import com.vuongpq2.datn.data.model.DMergerInfoPedigree;
import com.vuongpq2.datn.model.*;
import com.vuongpq2.datn.repository.NodeMemberRepository;
import com.vuongpq2.datn.repository.PedigreeRepository;
import com.vuongpq2.datn.repository.UserPermissionRepository;
import com.vuongpq2.datn.repository.UserRepository;
import com.vuongpq2.datn.service.GenealogyService;
import com.vuongpq2.datn.service.NodeMemberService;
import com.vuongpq2.datn.service.PedigreeService;
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
    GenealogyService genealogyService;
    @Autowired
    PedigreeService pedigreeService;
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

    @GetMapping(value = "/rest/pedigree/list/{idGenealogy}/{idParentMerger}", produces = "application/json")
    public ResponseEntity<?> getInfoMergerPedigree(Principal principal, @PathVariable(value = "idGenealogy", required = false) int idGenealogy, @PathVariable(value = "idParentMerger", required = false) int idParentMerger) {
        if (principal == null) {
            return null;
        }
        DInfoMerger dInfoMerger = new DInfoMerger();
        List<PedigreeModel> pedigreeModels = pedigreeRepository.findAllByGenealogyModel_Id(idGenealogy);
        for(PedigreeModel pedigreeModel: pedigreeModels) {
            DMergerInfoPedigree dMergerInfoPedigree = new DMergerInfoPedigree();
            dMergerInfoPedigree.setId(pedigreeModel.getId());
            dMergerInfoPedigree.setName(pedigreeModel.getName());
            dInfoMerger.getInfoPedigreeList().add(dMergerInfoPedigree);
        }
        //tim con
        Optional<NodeMemberModel> parent = nodeMemberService.findById(idParentMerger);
        List<NodeMemberModel> nodeMemberModels;
        if(parent.get().getRelation() == Relation.VO.ordinal() || parent.get().getRelation() == Relation.CHONG.ordinal()) {
            nodeMemberModels = nodeMemberService.findAllByPedigreeAndPatchKeyAndMotherFatherId(parent.get().getPedigree(), parent.get().getPatchKey(), parent.get().getId());
        }else {
            nodeMemberModels = nodeMemberService.findAllByPedigreeAndPatchKey(parent.get().getPedigree(), NodeMemberModel.getPathkeyByParent(parent.get()));
        }

        for(NodeMemberModel nodeMemberModel: nodeMemberModels) {
            if(nodeMemberModel.getRelation() != Relation.CHONG.ordinal() && nodeMemberModel.getRelation() != Relation.VO.ordinal()) {
                dInfoMerger.getListChildIndex().add(nodeMemberModel.getChildIndex());
            }
        }
        return new ResponseEntity<>(dInfoMerger, HttpStatus.OK);
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
        //set lai life index la  the he tiep theo
        parentSelect.setLifeIndex(parent.get().getLifeIndex() + 1);
        System.out.println("parent select idx " + parentSelect.getLifeIndex());
        String keyParentSelectRoot = NodeMemberModel.getPathkeyByParent(parentSelect);
        for (NodeMemberModel nodeMemberModel: nodeMemberModelListSelect) {
            if(nodeMemberModel.getId() == parentSelect.getId()) {
                continue;
            }
            String key = nodeMemberModel.getPatchKey();
            key = key.replace(keyParentSelectRoot, keyParent + "_" + parentSelect.getId());
            nodeMemberModel.setPatchKey(key);
            System.out.println("pre child select idx " + nodeMemberModel.getLifeIndex());
            nodeMemberModel.setLifeIndex(nodeMemberModel.getLifeIndex() + parentSelect.getLifeIndex() - 1);
            System.out.println("child select idx " + nodeMemberModel.getLifeIndex());
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

    @PostMapping(value = "/rest/pedigree/curPedigree", produces = "application/json")
    public ResponseEntity<?> cutPedigree (Principal principal,
                                            @RequestParam(value = "idGenealogy") String idGenealogy,
                                            @RequestParam(value = "idPedigree") String idPedigree,
                                            @RequestParam(value = "cutPedigreeName") String cutPedigreeName,
                                            @RequestParam(value = "cutPedigreeHistory") String cutPedigreeHistory,
                                            @RequestParam(value = "idMember") String idMember

    ) {
        Optional<NodeMemberModel> nodeParent = nodeMemberService.findById(Integer.parseInt(idMember));
        if(nodeParent == null) {
            return new ResponseEntity<>("-1", HttpStatus.OK);
        }
        if(nodeParent.get().getRelation() == Relation.VO.ordinal() || nodeParent.get().getRelation() == Relation.CHONG.ordinal()) {
            return new ResponseEntity<>("-1", HttpStatus.OK);
        }
        if(nodeParent.get().getPatchKey().equals("r")) {
            return new ResponseEntity<>("-1", HttpStatus.OK);
        }
        Optional<PedigreeModel> pedigreeModelSelect = pedigreeRepository.findById(Integer.parseInt(idPedigree));
        List<NodeMemberModel> listChild = nodeMemberService.findAllByPedigreeAndPatchKeyStartsWith(pedigreeModelSelect.get(), nodeParent.get().getPatchKey()+ "_" + nodeParent.get().getId());
        if(listChild.size() <= 0) {
            return new ResponseEntity<>("-1", HttpStatus.OK);
        }
        PedigreeModel pedigreeModel = new PedigreeModel();
        pedigreeModel.setName(cutPedigreeName);
        pedigreeModel.setHistory(cutPedigreeHistory);
        pedigreeService.add(pedigreeModel, Integer.parseInt(idGenealogy));
        System.out.println("idPedigree " + pedigreeModel.getId());

        int lifeIdxParentBefore = nodeParent.get().getLifeIndex();
        String keyParentSelectRoot = NodeMemberModel.getPathkeyByParent(nodeParent.get());
        nodeParent.get().setPatchKey("r");
        nodeParent.get().setParent(null);
        nodeParent.get().setChildIndex(-1);
        nodeParent.get().setRelation(-1);
        nodeParent.get().setPedigree(pedigreeModel);
        System.out.println("life idx before parent " + nodeParent.get().getLifeIndex());
        int ofsLifeIdx = lifeIdxParentBefore - 1;
        nodeParent.get().setLifeIndex(1);
        nodeMemberRepository.save(nodeParent.get());

        for (NodeMemberModel child: listChild) {
            if(child.getId() == nodeParent.get().getId()) {
                continue;
            }
            String key = child.getPatchKey();
            key = key.replace(keyParentSelectRoot, "r_" + nodeParent.get().getId());
            child.setPatchKey(key);
            child.setPedigree(pedigreeModel);
            System.out.println("life idx before child " + child.getLifeIndex());
            child.setLifeIndex(child.getLifeIndex() - ofsLifeIdx);
            System.out.println("life idx after child " + child.getLifeIndex());
            nodeMemberRepository.save(child);
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
