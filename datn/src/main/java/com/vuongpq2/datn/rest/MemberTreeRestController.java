package com.vuongpq2.datn.rest;

import com.vuongpq2.datn.config.ConfigFormat;
import com.vuongpq2.datn.config.tree.ChartConfig;
import com.vuongpq2.datn.config.tree.Child;
import com.vuongpq2.datn.config.tree.Text;
import com.vuongpq2.datn.data.Enum.Permission;
import com.vuongpq2.datn.data.Enum.Relation;
import com.vuongpq2.datn.data.GioiTinh;
import com.vuongpq2.datn.data.cachgoiten.CachGoiTen;
import com.vuongpq2.datn.data.model.DDetailNodeMember;
import com.vuongpq2.datn.data.model.DHusbandOrWife;
import com.vuongpq2.datn.data.model.DInfoFormAddChild;
import com.vuongpq2.datn.data.model.DMergeNodeMember;
import com.vuongpq2.datn.model.*;
import com.vuongpq2.datn.repository.UserPermissionRepository;
import com.vuongpq2.datn.repository.UserRepository;
import com.vuongpq2.datn.service.NodeMemberService;
import com.vuongpq2.datn.service.PedigreeService;
import com.vuongpq2.datn.utils.MyUltils;
import com.vuongpq2.datn.utils.PermissionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@Transactional
public class MemberTreeRestController {
    @Autowired
    private PedigreeService pedigreeService;
    @Autowired
    private NodeMemberService nodeMemberService;
    @Autowired
    private UserPermissionRepository userPermissionRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping(value = "/rest/genealogy/{idGenealogy}/pedigree/{idPedigree}/list-member-tree", produces = "application/json")
    public ChartConfig getListPeopleTree(Principal principal,
                                         @PathVariable(name = "idGenealogy") int idGenealogy,
                                         @PathVariable(name = "idPedigree") int idPedigree
    ) {
        Optional<PedigreeModel> pedigreeModel = pedigreeService.findById(idPedigree);
        List<NodeMemberModel> nodeMemberModels = nodeMemberService.findAllByPedigreeAndPatchKeyStartsWith(pedigreeModel.get(), "r");
        ChartConfig chartConfig = new ChartConfig();
        for (int i = 0; i < nodeMemberModels.size(); i++) {
            NodeMemberModel nodeMemberModel = nodeMemberModels.get(i);
            Child child = new Child();
            child.setHTMLid(nodeMemberModel.getId() + "");
            child.setId(nodeMemberModel.getId());
            child.setChildrenDropLevel(nodeMemberModel.getChildIndex());
            child.setRelation(nodeMemberModel.getRelation());
            child.setGender(nodeMemberModel.getGender());
            child.setImage(nodeMemberModel.getImage());
            child.setHTMLclass("people_chart_node");
            child.setPatchKey(nodeMemberModel.getPatchKey());
            child.setIdFather(nodeMemberModel.getParent() == null ? -1 : nodeMemberModel.getParent().getId());
            child.setIdMother(nodeMemberModel.getMotherFatherId() == null ? -1 : nodeMemberModel.getMotherFatherId());
            Text text = new Text();
            text.setTitle(MyUltils.getStringFromDate(nodeMemberModel.getDescriptionMemberModel().getBirthday()) + " - " + MyUltils.getStringFromDate(nodeMemberModel.getDescriptionMemberModel().getDeadDay()));
            text.setName(nodeMemberModel.getName());
            child.setText(text);
            chartConfig.addChild(child);
        }
        return chartConfig;
    }

    @PostMapping(value = "/rest/member-tree/add", produces = "application/json")
    public ResponseEntity<?> addMemberTree(Principal principal,
                                           @RequestParam(value = "idPedigree", required = true, defaultValue = "") int idPedigree,
                                           @RequestParam(value = "idGenealogy", required = true, defaultValue = "") int idGenealogy,
                                           @RequestParam(value = "addChildIdParent", required = true, defaultValue = "") String addChildIdParent,
                                           @RequestParam(value = "addChildInputRelation", required = true, defaultValue = "") String addChildInputRelation,
                                           @RequestParam(value = "addChildInputIdMotherFather", required = true, defaultValue = "") String addChildInputIdMotherFather,
                                           @RequestParam(value = "addChildInputConThu", required = true, defaultValue = "") String addChildInputConThu,
                                           @RequestParam(value = "addChildInputName", required = true, defaultValue = "") String addChildInputName,
                                           @RequestParam(value = "addChildInputNickName", required = true, defaultValue = "") String addChildInputNickName,
                                           @RequestParam(value = "addChildInputGender", required = true, defaultValue = "") String addChildInputGender,
                                           @RequestParam(value = "addChildInputAddress", required = true, defaultValue = "") String addChildInputAddress,
                                           @RequestParam(value = "addChildInputBirthday", required = false, defaultValue = "") String addChildInputBirthday,
                                           @RequestParam(value = "addChildInputDeadDay", required = false, defaultValue = "") String addChildInputDeadDay,
                                           @RequestParam(value = "addChildInputDegree", required = false, defaultValue = "") String addChildInputDegree,
                                           @RequestParam(value = "addChildInputDes", required = false, defaultValue = "") String addChildInputDes,
                                           @RequestParam(value = "addChildInputDataExtra", required = false, defaultValue = "") String addChildInputDataExtra,
                                           @RequestParam("addChildInputFileImg") MultipartFile addChildInputFileImg
    ) {
        UserModel userModel = userRepository.findByEmail(principal.getName());
        UserPermissionModel userPermissionModel = userPermissionRepository.findTopByUserAndGenealogy_Id(userModel, idGenealogy);
        if (userPermissionModel == null || !PermissionUtils.isCanAddMemberTree(Permission.byCode(userPermissionModel.getPermission().getCode()))) {
            return new ResponseEntity<>("-1", HttpStatus.NOT_FOUND);
        }

        Optional<PedigreeModel> pedigreeModel = pedigreeService.findById(idPedigree);
        NodeMemberModel nodeMemberModel = new NodeMemberModel();
        DescriptionMemberModel descriptionMemberModel = new DescriptionMemberModel();

        if (addChildInputIdMotherFather == null || addChildInputIdMotherFather.equals("")) {
            addChildInputIdMotherFather = "-1";
        }
        if (addChildIdParent == null || addChildIdParent.equals("")) {
            addChildIdParent = "-1";
        }
        if (addChildInputConThu.equals("")) {
            addChildInputConThu = "-1";
        }

        if (addChildInputRelation.equals("")) {
            addChildInputRelation = "-1";
        }
        Optional<NodeMemberModel> parent = null;
        parent = nodeMemberService.findById(Integer.parseInt(addChildIdParent));
        if (!parent.isPresent()) {
            nodeMemberModel.setParent(null);
            nodeMemberModel.setLifeIndex(0);
        } else {
            //neu truong hop them vao bang nut me hoac cha la con cua parent
            if (parent.get().getRelation() == Relation.VO.ordinal() || parent.get().getRelation() == Relation.CHONG.ordinal()) {
                Optional<NodeMemberModel> realParent = nodeMemberService.findById(Integer.parseInt(MyUltils.getIdParentByPathKey(parent.get().getPatchKey())));
                System.out.println("idRealParent " + MyUltils.getIdParentByPathKey(parent.get().getPatchKey()));
                if (realParent.isPresent()) {
                    addChildInputIdMotherFather = addChildIdParent;
                    parent = realParent;
                }
            }
            nodeMemberModel.setParent(parent.get());
            if(Integer.parseInt(addChildInputRelation) == Relation.VO.ordinal() || Integer.parseInt(addChildInputRelation) == Relation.CHONG.ordinal()) {
                nodeMemberModel.setLifeIndex(parent.get().getLifeIndex());
            }else {
                nodeMemberModel.setLifeIndex(parent.get().getLifeIndex() + 1);
            }
            System.out.println("id parent " + parent.get().getId());
        }
        System.out.println("id mother or father " + addChildInputIdMotherFather);
        nodeMemberModel.setMotherFatherId(Integer.parseInt(addChildInputIdMotherFather));
        nodeMemberModel.setRelation(Integer.parseInt(addChildInputRelation));
        nodeMemberModel.setChildIndex(Integer.parseInt(addChildInputConThu));
        nodeMemberModel.setGender(Integer.parseInt(addChildInputGender));
        nodeMemberModel.setName(addChildInputName);
        nodeMemberModel.setPedigree(pedigreeModel.get());
        nodeMemberModel.setPatchKey(NodeMemberModel.getPathkeyByParent(parent.isPresent() ? parent.get() : null));
        descriptionMemberModel.setNickName(addChildInputNickName);
        descriptionMemberModel.setAddress(addChildInputAddress);
        descriptionMemberModel.setBirthday(MyUltils.getDate(addChildInputBirthday));
        descriptionMemberModel.setDeadDay(MyUltils.getDate(addChildInputDeadDay));
        descriptionMemberModel.setDegree(addChildInputDegree);
        descriptionMemberModel.setDescription(addChildInputDes);
        descriptionMemberModel.setExtraData(addChildInputDataExtra);
        nodeMemberService.add(nodeMemberModel, descriptionMemberModel);
        return new ResponseEntity<>("1", HttpStatus.OK);

    }

    @GetMapping(value = "/rest/genealogy/{idGenealogy}/pedigree/{idPedigree}/member-tree/{idMemberTree}/get-info-add")
    public ResponseEntity<?> getInfoFormAddChild(Principal principal,
                                                 @PathVariable(value = "idGenealogy") int idGenealogy,
                                                 @PathVariable(value = "idPedigree") int idPedigree,
                                                 @PathVariable(value = "idMemberTree") int idMemberTree
    ) {
        UserModel userModel = userRepository.findByEmail(principal.getName());
        UserPermissionModel userPermissionModel = userPermissionRepository.findTopByUserAndGenealogy_Id(userModel, idGenealogy);
        if (!PermissionUtils.isCanAddMemberTree(Permission.byCode(userPermissionModel.getPermission().getCode()))) {
            return new ResponseEntity<>("", HttpStatus.EXPECTATION_FAILED);
        }
        Optional<NodeMemberModel> parent = nodeMemberService.findById(idMemberTree);
        if (!parent.isPresent()) {
            return new ResponseEntity<>(idMemberTree, HttpStatus.NOT_FOUND);
        }
        DInfoFormAddChild dInfoFormAddChild = new DInfoFormAddChild();
        dInfoFormAddChild.setId(parent.get().getId());
        dInfoFormAddChild.setName(parent.get().getName());
        int relationNeedFind = parent.get().getGender() == GioiTinh.NAM.ordinal() ? Relation.VO.ordinal() : Relation.CHONG.ordinal();
        Optional<PedigreeModel> pedigreeModel = pedigreeService.findById(idPedigree);
        List<NodeMemberModel> listHusbandWifes = nodeMemberService.findAllByPedigreeAndPatchKeyAndRelationEquals(pedigreeModel.get(), NodeMemberModel.getPathkeyByParent(parent.get()), relationNeedFind);

        List<DHusbandOrWife> husbandOrWifeList = new ArrayList<>(1);
        DHusbandOrWife unknown = new DHusbandOrWife();
        unknown.setName("Không rõ");
        unknown.setId(-1);
        husbandOrWifeList.add(unknown);
        for (NodeMemberModel nodeMemberModel : listHusbandWifes) {
            DHusbandOrWife hw = new DHusbandOrWife();
            hw.setId(nodeMemberModel.getId());
            hw.setName(nodeMemberModel.getName());
            husbandOrWifeList.add(hw);
        }
        dInfoFormAddChild.setHusbandOrWifes(husbandOrWifeList);
        return new ResponseEntity<>(dInfoFormAddChild, HttpStatus.OK);
    }

    @GetMapping(value = "/rest/member-tree/detail/{idMemberTree}")
    public ResponseEntity<?> viewNodeMember(Principal principal,
                                            @PathVariable(value = "idMemberTree") int idMemberTree
    ) {
        Optional<NodeMemberModel> nodeMemberModel = nodeMemberService.findById(idMemberTree);
        if (!nodeMemberModel.isPresent()) {
            return new ResponseEntity<>(idMemberTree, HttpStatus.NOT_FOUND);
        }
        Optional<NodeMemberModel> nodeParent = nodeMemberService.findById(nodeMemberModel.get().getParent() != null ? nodeMemberModel.get().getParent().getId() : -1);
        Optional<NodeMemberModel> nodeFatherOrMother = nodeMemberService.findById(nodeMemberModel.get().getMotherFatherId());
        DescriptionMemberModel descriptionMemberModel = nodeMemberModel.get().getDescriptionMemberModel();
        DDetailNodeMember dDetailNodeMember = new DDetailNodeMember();
        dDetailNodeMember.setId(idMemberTree);
        dDetailNodeMember.setImg(nodeMemberModel.get().getImage());
        dDetailNodeMember.setChildIndex(nodeMemberModel.get().getChildIndex());
        dDetailNodeMember.setName(nodeMemberModel.get().getName());
        dDetailNodeMember.setNameParent(nodeParent.isPresent() ? nodeParent.get().getName() : "Không rõ");
        dDetailNodeMember.setNameFatherOrMother(nodeFatherOrMother.isPresent() ? nodeFatherOrMother.get().getName() : "Không rõ");
        dDetailNodeMember.setNickname(descriptionMemberModel.getNickName());
        dDetailNodeMember.setGender(nodeMemberModel.get().getGender());
        dDetailNodeMember.setAddress(descriptionMemberModel.getAddress());
        dDetailNodeMember.setRelation(nodeMemberModel.get().getRelation());
        dDetailNodeMember.setBirthDay(MyUltils.getStringFromDate(descriptionMemberModel.getBirthday()));
        dDetailNodeMember.setDeadDay(MyUltils.getStringFromDate(descriptionMemberModel.getDeadDay()));
        dDetailNodeMember.setDegree(descriptionMemberModel.getDegree());
        dDetailNodeMember.setDes(descriptionMemberModel.getDescription());
        dDetailNodeMember.setExtraData(descriptionMemberModel.getExtraData());
        return new ResponseEntity<>(dDetailNodeMember, HttpStatus.OK);
    }

    @PostMapping(value = "/rest/member-tree/edit", produces = "application/json")
    public ResponseEntity<?> editNodeMemberTree(Principal principal,
                                                @RequestParam(value = "editChildId", defaultValue = "", required = true) String editChildId,
                                                @RequestParam(value = "editChildInputRelation", defaultValue = "", required = false) String editChildInputRelation,
                                                @RequestParam(value = "editChildInputIdMother", defaultValue = "", required = false) String editChildInputIdMother,
                                                @RequestParam(value = "editChildInputConThu", defaultValue = "", required = false) String editChildInputConThu,
                                                @RequestParam(value = "editChildInputName", defaultValue = "", required = false) String editChildInputName,
                                                @RequestParam(value = "editChildInputNickName", defaultValue = "", required = false) String editChildInputNickName,
                                                @RequestParam(value = "editChildInputGender", defaultValue = "", required = false) String editChildInputGender,
                                                @RequestParam(value = "editChildInputBirthday", defaultValue = "", required = false) String editChildInputBirthday,
                                                @RequestParam(value = "editChildInputDeadDay", defaultValue = "", required = false) String editChildInputDeadDay,
                                                @RequestParam(value = "editChildInputDegree", defaultValue = "", required = false) String editChildInputDegree,
                                                @RequestParam(value = "editChildInputDes", defaultValue = "", required = false) String editChildInputDes,
                                                @RequestParam(value = "editChildInputDataExtra", defaultValue = "", required = false) String editChildInputDataExtra,
                                                @RequestParam("editChildInputFileImg") MultipartFile editChildInputFileImg
    ) {
        Optional<NodeMemberModel> nodeMemberModel = nodeMemberService.findById(Integer.parseInt(editChildId));
        DescriptionMemberModel descriptionMemberModel = nodeMemberModel.get().getDescriptionMemberModel();
        if (!nodeMemberModel.isPresent()) {
            return new ResponseEntity<>("fail", HttpStatus.NOT_FOUND);
        }
        if (editChildInputIdMother == null || editChildInputIdMother.equals("")) {
            editChildInputIdMother = "-1";
        }
        if (editChildInputConThu.equals("")) {
            editChildInputConThu = "-1";
        }

        if (editChildInputRelation.equals("")) {
            editChildInputRelation = "-1";
        }
        if (editChildInputConThu.equals("")) {
            editChildInputConThu = "-1";
        }

        nodeMemberModel.get().setName(editChildInputName);
        nodeMemberModel.get().setRelation(Integer.parseInt(editChildInputRelation));
        nodeMemberModel.get().setMotherFatherId(Integer.parseInt(editChildInputIdMother));
        nodeMemberModel.get().setChildIndex(Integer.parseInt(editChildInputConThu));
        descriptionMemberModel.setNickName(editChildInputNickName);
        nodeMemberModel.get().setGender(Integer.parseInt(editChildInputGender));
        descriptionMemberModel.setBirthday(MyUltils.getDate(editChildInputBirthday));
        descriptionMemberModel.setDeadDay(MyUltils.getDate(editChildInputDeadDay));
        descriptionMemberModel.setDegree(editChildInputDegree);
        descriptionMemberModel.setDescription(editChildInputDes);
        descriptionMemberModel.setExtraData(editChildInputDataExtra);
        nodeMemberService.add(nodeMemberModel.get(), descriptionMemberModel);
        return new ResponseEntity<>("1", HttpStatus.OK);
    }

    @PostMapping(value = "/rest/genealogy/{idGenealogy}/pedigree/{idPedigree}/people/{idMember}/delete", produces = "application/json")
    public ResponseEntity<?> deleteMember (Principal principal,
                                           @PathVariable(value = "idGenealogy") int idGenealogy,
                                           @PathVariable(value = "idPedigree") int idPedigree,
                                           @PathVariable(value = "idMember") int idMember
                                           ) {
        Optional<NodeMemberModel> parent = nodeMemberService.findById(idMember);
        if(parent.isPresent()) {
            //xoa cac key con cua no
            String childPatchKey = NodeMemberModel.getPathkeyByParent(parent.get());
            Optional<PedigreeModel> pedigreeModel = pedigreeService.findById(idPedigree);
            //xoa chinh no
            nodeMemberService.deleteById(parent.get().getId());

            //TH co moi quan he la vo hoac chong(no co patch key giong thang con) thi chi xoa nhung thang co patch key giong no
            if(parent.get().getRelation() == Relation.VO.ordinal() || parent.get().getRelation() == Relation.CHONG.ordinal()) {
                childPatchKey = parent.get().getPatchKey();
            }

            nodeMemberService.deleteAllByPedigreeAndPatchKeyStartsWith(pedigreeModel.get(), childPatchKey);
            return new ResponseEntity<>("1", HttpStatus.OK);
        }
        return new ResponseEntity<>("1", HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/rest/genealogy/{idGenealogy}/pedigree/{idPedigree}/get-all-node-parent")
    public ResponseEntity<?> getAllNodeParent(Principal principal,
                                              @PathVariable(value = "idGenealogy") int idGenealogy,
                                              @PathVariable(value = "idPedigree") int idPedigree
                                              ){
        List<DMergeNodeMember> dMergeNodeMembers = new ArrayList<>();
        Optional<PedigreeModel> pedigreeModel = pedigreeService.findById(idPedigree);
        List<NodeMemberModel> nodeMemberModels = nodeMemberService.findAllByPedigreeAndPatchKeyStartsWith(pedigreeModel.get(), "r");
        for (NodeMemberModel nodeMemberModel: nodeMemberModels) {
            DMergeNodeMember dMergeNodeMember = new DMergeNodeMember();
            dMergeNodeMember.setId(nodeMemberModel.getId());
            dMergeNodeMember.setName(nodeMemberModel.getName());
            dMergeNodeMembers.add(dMergeNodeMember);
        }
        return new ResponseEntity<>(dMergeNodeMembers, HttpStatus.OK);
    }

    @GetMapping(value = "/rest/people/relation/{idXemQuanHe1}/{idXemQuanHe2}")
    public ResponseEntity<?> viewRelationBetweenMember (Principal principal,
                                                        @PathVariable(value = "idXemQuanHe1") int idXemQuanHe1,
                                                        @PathVariable(value = "idXemQuanHe2") int idXemQuanHe2
                                                        ) {
        Optional<NodeMemberModel> member1 = nodeMemberService.findById(idXemQuanHe1);
        Optional<NodeMemberModel> member2 = nodeMemberService.findById(idXemQuanHe2);
        if(!member1.isPresent() || !member2.isPresent()) {
            return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
        }
        int level = Math.abs(member1.get().getLifeIndex() - member2.get().getLifeIndex());
        boolean higher1 = false, higher2 = false, isParent;
        if(member1.get().getParent() == null || member2.get().getParent() == null) {
            isParent = false;
        }else {
            isParent = member1.get().getParent().getId() == member2.get().getParent().getId();
        }
        if(level == 0) {
            //TH cung cha or me
            if(isParent) {
                higher1 = member1.get().getChildIndex() < member2.get().getChildIndex();
                higher2 = member2.get().getChildIndex() < member1.get().getChildIndex();
            }else {
                //TH khac cha hoac me thi thi so sanh  child index cua cha hoac me
                NodeMemberModel parent1 = member1.get().getParent();
                if(parent1 == null) {
                    higher1 = false;
                    higher2 = false;
                }else {
                    /*if(member2.get().getParent().getId() == member1.get().getId()) {
                        parent1 = member1.get();
                    }*/
                    /*if(parent1.getRelation() == Relation.VO.ordinal() || parent1.getRelation() == Relation.CHONG.ordinal()) {
                        parent1 = nodeMemberService.findById(Integer.parseInt(MyUltils.getIdParentByPathKey(parent1.getPatchKey()))).get();
                    }

                    if(member1.get().getRelation() == Relation.VO.ordinal() || member1.get().getRelation() == Relation.CHONG.ordinal()) {
                        parent1 = member1.get().getParent();
                    }*/
                }

                NodeMemberModel parent2 = member2.get().getParent();
                if(parent2 == null) {
                    higher1 = false;
                    higher2 = false;
                } else {
                    /*if(member1.get().getParent().getId() == member2.get().getId()) {
                        parent2 = member1.get();
                    }*/
                    /*if(parent2.getRelation() == Relation.VO.ordinal() || parent2.getRelation() == Relation.CHONG.ordinal()) {
                        parent2 = nodeMemberService.findById(Integer.parseInt(MyUltils.getIdParentByPathKey(parent2.getPatchKey()))).get();
                    }

                    if(member2.get().getRelation() == Relation.VO.ordinal() || member2.get().getRelation() == Relation.CHONG.ordinal()) {
                        parent2 = member2.get().getParent();
                    }*/
                }

                if(parent1 != null && parent1.getLifeIndex() < member1.get().getLifeIndex()) {
                    parent1 = member1.get();
                }

                if(parent2 != null && parent2.getLifeIndex() < member2.get().getLifeIndex()) {
                    parent2 = member2.get();
                }

                if(parent1 != null && parent2 != null) {
                    higher1 = parent1.getChildIndex() < parent2.getChildIndex();
                    higher2 = parent2.getChildIndex() < parent1.getChildIndex();
                }
            }

        }else {
            higher1 = member1.get().getLifeIndex() < member2.get().getLifeIndex();
            higher2 = member2.get().getLifeIndex() < member1.get().getLifeIndex();
        }
        boolean isOutSide1 = false, isOutSide2 = false;
        if(member1.get().getRelation() == Relation.VO.ordinal() || member1.get().getRelation() == Relation.CHONG.ordinal()) {
            isOutSide1 = true;
        }

        if(member2.get().getRelation() == Relation.VO.ordinal() || member2.get().getRelation() == Relation.CHONG.ordinal()) {
            isOutSide2 = true;
        }

        int isHigherParent1 = 0, isHigherParent2 = 0;
        Relation sideRelation1 = Relation.NONE;
        Relation sideRelation2 = Relation.NONE;
        if(higher1 && level == 1) {
            NodeMemberModel parent22 = member2.get().getParent();
            /*if(parent22.getRelation() == Relation.VO.ordinal() || parent22.getRelation() == Relation.CHONG.ordinal()) {
                parent22 = nodeMemberService.findById(Integer.parseInt(MyUltils.getIdParentByPathKey(parent22.getPatchKey()))).get();
            }*/
            NodeMemberModel parent11 = member1.get();
            if(parent11.getRelation() == Relation.VO.ordinal() || parent11.getRelation() == Relation.CHONG.ordinal()) {
                parent11 = member1.get().getParent();
            }
            if(parent11.getChildIndex() > parent22.getChildIndex()) {
                isHigherParent1 = - 1;
            }else if(parent11.getChildIndex() < parent22.getChildIndex()){
                isHigherParent1 = 1;
            }

            sideRelation1 = parent22.getGender() == GioiTinh.NAM.ordinal() ? Relation.CHA : Relation.ME;
        }

        if(higher2 && level == 1) {
            NodeMemberModel parent11 = member1.get().getParent();
            /*if(parent11.getRelation() == Relation.VO.ordinal() || parent11.getRelation() == Relation.CHONG.ordinal()) {
                parent11 = nodeMemberService.findById(Integer.parseInt(MyUltils.getIdParentByPathKey(parent11.getPatchKey()))).get();
            }*/
            NodeMemberModel parent22 = member2.get();
            if(parent22.getRelation() == Relation.VO.ordinal() || parent22.getRelation() == Relation.CHONG.ordinal()) {
                parent22 = member2.get().getParent();
            }
            if(parent22.getChildIndex() > parent11.getChildIndex()) {
                isHigherParent2 = 1;
            }else if(parent22.getChildIndex() < parent11.getChildIndex()){
                isHigherParent2 = -1;
            }
            sideRelation2 = parent11.getGender() == GioiTinh.NAM.ordinal() ? Relation.CHA : Relation.ME;
        }

        String relation1 = CachGoiTen.getInstance().getName(member1.get().getGender(), level, higher1, isOutSide1, isParent, member1.get().getRelation(), isHigherParent1, sideRelation1);
        String relation2 = CachGoiTen.getInstance().getName(member2.get().getGender(), level, higher2, isOutSide2, isParent, member2.get().getRelation(), isHigherParent2, sideRelation2);
        System.out.println("member 1 goi 2 la " + relation1);
        System.out.println("member 2 goi 1 la " + relation2);
        return new ResponseEntity<>("", HttpStatus.OK);
    }
}
