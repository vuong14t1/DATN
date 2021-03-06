package com.vuongpq2.datn.rest;

import com.vuongpq2.datn.config.tree.ChartConfig;
import com.vuongpq2.datn.config.tree.Child;
import com.vuongpq2.datn.config.tree.Text;
import com.vuongpq2.datn.data.Enum.Permission;
import com.vuongpq2.datn.data.Enum.Relation;
import com.vuongpq2.datn.data.GioiTinh;
import com.vuongpq2.datn.data.cachgoiten.CachGoiTen;
import com.vuongpq2.datn.data.model.*;
import com.vuongpq2.datn.model.*;
import com.vuongpq2.datn.repository.UserPermissionRepository;
import com.vuongpq2.datn.repository.UserRepository;
import com.vuongpq2.datn.service.NodeMemberService;
import com.vuongpq2.datn.service.PedigreeService;
import com.vuongpq2.datn.service.StorageService;
import com.vuongpq2.datn.utils.MyUltils;
import com.vuongpq2.datn.utils.PermissionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.*;

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
    @Autowired
    private StorageService storageService;

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
            nodeMemberModel.setLifeIndex(1);
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
            if (Integer.parseInt(addChildInputRelation) == Relation.VO.ordinal() || Integer.parseInt(addChildInputRelation) == Relation.CHONG.ordinal()) {
                nodeMemberModel.setLifeIndex(parent.get().getLifeIndex());
            } else {
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
        Random rd = new Random();
        String img = nodeMemberModel.getGender() == GioiTinh.NAM.ordinal()? "/img/avatar-default-nam_"+ (rd.nextInt(3) + 1)+ ".png" : "/img/avatar-default-nu_" + (rd.nextInt(4) + 1) + ".png";
        nodeMemberModel.setImage(img);
        nodeMemberModel.setPatchKey(NodeMemberModel.getPathkeyByParent(parent.isPresent() ? parent.get() : null));
        descriptionMemberModel.setNickName(addChildInputNickName);
        descriptionMemberModel.setAddress(addChildInputAddress);
        descriptionMemberModel.setBirthday(MyUltils.getDate(addChildInputBirthday));
        descriptionMemberModel.setDeadDay(MyUltils.getDate(addChildInputDeadDay));
        descriptionMemberModel.setDegree(addChildInputDegree);
        descriptionMemberModel.setDescription(addChildInputDes);
        descriptionMemberModel.setExtraData(addChildInputDataExtra);
        //file
        String uploadedFileName = addChildInputFileImg.getOriginalFilename();
        if (!uploadedFileName.isEmpty()) {
            uploadedFileName = + System.currentTimeMillis() + uploadedFileName.substring(uploadedFileName.lastIndexOf("."));
            String fileImg = "img/" + uploadedFileName;
            nodeMemberModel.setImage("/image/" + uploadedFileName);
            storageService.store(addChildInputFileImg, fileImg);
        }
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
        dInfoFormAddChild.setRelation(parent.get().getRelation());
        dInfoFormAddChild.setGender(parent.get().getGender());
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
        //tim con
        List<NodeMemberModel> nodeMemberModels;
        if(parent.get().getRelation() == Relation.VO.ordinal() || parent.get().getRelation() == Relation.CHONG.ordinal()) {
            nodeMemberModels = nodeMemberService.findAllByPedigreeAndPatchKeyAndMotherFatherId(pedigreeModel.get(), parent.get().getPatchKey(), parent.get().getId());
        }else {
            nodeMemberModels = nodeMemberService.findAllByPedigreeAndPatchKey(pedigreeModel.get(), NodeMemberModel.getPathkeyByParent(parent.get()));
        }

        for(NodeMemberModel nodeMemberModel: nodeMemberModels) {
            if(nodeMemberModel.getRelation() != Relation.CHONG.ordinal() && nodeMemberModel.getRelation() != Relation.VO.ordinal()) {
                dInfoFormAddChild.getListChildIndex().add(nodeMemberModel.getChildIndex());
            }
        }
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
            editChildInputIdMother = String.valueOf(nodeMemberModel.get().getMotherFatherId());
        }
        if (editChildInputConThu.equals("")) {
            editChildInputConThu = String.valueOf(nodeMemberModel.get().getChildIndex());
        }

        if (editChildInputRelation.equals("")) {
            editChildInputRelation = String.valueOf(nodeMemberModel.get().getRelation());
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
        String uploadedFileName = editChildInputFileImg.getOriginalFilename();
        if (!uploadedFileName.isEmpty()) {
            uploadedFileName = + System.currentTimeMillis() + uploadedFileName.substring(uploadedFileName.lastIndexOf("."));
            String fileImg = "img/" + uploadedFileName;
            nodeMemberModel.get().setImage("/image/" + uploadedFileName);
            storageService.store(editChildInputFileImg, fileImg);
        }
        nodeMemberService.add(nodeMemberModel.get(), descriptionMemberModel);
        return new ResponseEntity<>("1", HttpStatus.OK);
    }

    @PostMapping(value = "/rest/genealogy/{idGenealogy}/pedigree/{idPedigree}/people/{idMember}/delete", produces = "application/json")
    public ResponseEntity<?> deleteMember(Principal principal,
                                          @PathVariable(value = "idGenealogy") int idGenealogy,
                                          @PathVariable(value = "idPedigree") int idPedigree,
                                          @PathVariable(value = "idMember") int idMember
    ) {
        Optional<NodeMemberModel> parent = nodeMemberService.findById(idMember);
        if (parent.isPresent()) {
            //xoa cac key con cua no
            String childPatchKey = NodeMemberModel.getPathkeyByParent(parent.get());
            Optional<PedigreeModel> pedigreeModel = pedigreeService.findById(idPedigree);

            //TH co moi quan he la vo hoac chong(no co patch key giong thang con) thi chi xoa nhung thang co patch key giong no
            if (parent.get().getRelation() == Relation.VO.ordinal() || parent.get().getRelation() == Relation.CHONG.ordinal()) {
                childPatchKey = parent.get().getPatchKey();
            }

            nodeMemberService.deleteAllByPedigreeAndPatchKeyStartsWith(pedigreeModel.get(), childPatchKey);
            if (parent.get().getRelation() != Relation.VO.ordinal() && parent.get().getRelation() != Relation.CHONG.ordinal()) {
                //xoa chinh no
                nodeMemberService.deleteById(parent.get().getId());
            }

            return new ResponseEntity<>("1", HttpStatus.OK);
        }
        return new ResponseEntity<>("1", HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/rest/genealogy/{idGenealogy}/pedigree/{idPedigree}/get-all-node-parent")
    public ResponseEntity<?> getAllNodeParent(Principal principal,
                                              @PathVariable(value = "idGenealogy") int idGenealogy,
                                              @PathVariable(value = "idPedigree") int idPedigree
    ) {
        List<DMergeNodeMember> dMergeNodeMembers = new ArrayList<>();
        Optional<PedigreeModel> pedigreeModel = pedigreeService.findById(idPedigree);
        List<NodeMemberModel> nodeMemberModels = nodeMemberService.findAllByPedigreeAndPatchKeyStartsWith(pedigreeModel.get(), "r");
        for (NodeMemberModel nodeMemberModel : nodeMemberModels) {
            DMergeNodeMember dMergeNodeMember = new DMergeNodeMember();
            dMergeNodeMember.setId(nodeMemberModel.getId());
            dMergeNodeMember.setName(nodeMemberModel.getName());
            dMergeNodeMembers.add(dMergeNodeMember);
        }
        return new ResponseEntity<>(dMergeNodeMembers, HttpStatus.OK);
    }

    @GetMapping(value = "/rest/people/relation/{idXemQuanHe1}/{idXemQuanHe2}")
    public ResponseEntity<?> viewRelationBetweenMember(Principal principal,
                                                       @PathVariable(value = "idXemQuanHe1") int idXemQuanHe1,
                                                       @PathVariable(value = "idXemQuanHe2") int idXemQuanHe2
    ) {
        Optional<NodeMemberModel> member1 = nodeMemberService.findById(idXemQuanHe1);
        Optional<NodeMemberModel> member2 = nodeMemberService.findById(idXemQuanHe2);
        if (!member1.isPresent() || !member2.isPresent()) {
            return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
        }
        int level = Math.abs(member1.get().getLifeIndex() - member2.get().getLifeIndex());
        boolean higher1 = false, higher2 = false, isParent;
        if (member1.get().getParent() == null || member2.get().getParent() == null) {
            isParent = false;
        } else {
            isParent = member1.get().getParent().getId() == member2.get().getParent().getId();
        }
        if (level == 0) {
            //TH cung cha or me
            if (isParent) {
                //xet TH khac me or cha
                if(member1.get().getMotherFatherId() != member2.get().getMotherFatherId()) {
                    Optional<NodeMemberModel> motherOrFather1 = nodeMemberService.findById(member1.get().getMotherFatherId());
                    Optional<NodeMemberModel> motherOrFather2 = nodeMemberService.findById(member2.get().getMotherFatherId());
                    higher1 = motherOrFather1.get().getChildIndex() < motherOrFather2.get().getChildIndex();
                    higher2 = motherOrFather2.get().getChildIndex() < motherOrFather1.get().getChildIndex();
                }else {
                    //TH cung cha or me
                    higher1 = member1.get().getChildIndex() < member2.get().getChildIndex();
                    higher2 = member2.get().getChildIndex() < member1.get().getChildIndex();
                }

            } else {
                //TH khac cha hoac me thi thi so sanh  child index cua cha hoac me
                NodeMemberModel parent1 = member1.get().getParent();
                if (parent1 == null) {
                    higher1 = false;
                    higher2 = false;
                }

                NodeMemberModel parent2 = member2.get().getParent();
                if (parent2 == null) {
                    higher1 = false;
                    higher2 = false;
                }

                /*if (parent1 != null && parent1.getLifeIndex() < member1.get().getLifeIndex()) {
                    parent1 = member1.get();
                }

                if (parent2 != null && parent2.getLifeIndex() < member2.get().getLifeIndex()) {
                    parent2 = member2.get();
                }*/

                if (parent1 != null && parent2 != null) {
                    higher1 = parent1.getChildIndex() < parent2.getChildIndex();
                    higher2 = parent2.getChildIndex() < parent1.getChildIndex();
                }
            }

        } else {
            higher1 = member1.get().getLifeIndex() < member2.get().getLifeIndex();
            higher2 = member2.get().getLifeIndex() < member1.get().getLifeIndex();
        }
        boolean isOutSide1 = false, isOutSide2 = false;
        if (member1.get().getRelation() == Relation.VO.ordinal() || member1.get().getRelation() == Relation.CHONG.ordinal()) {
            isOutSide1 = true;
        }

        if (member2.get().getRelation() == Relation.VO.ordinal() || member2.get().getRelation() == Relation.CHONG.ordinal()) {
            isOutSide2 = true;
        }

        int isHigherParent1 = 0, isHigherParent2 = 0;
        Relation sideRelation1 = Relation.NONE;
        Relation sideRelation2 = Relation.NONE;
        if (higher1 && level != 0) {
            NodeMemberModel parent22 = member2.get().getParent();

            NodeMemberModel parent11 = member1.get();
            if (parent11.getRelation() == Relation.VO.ordinal() || parent11.getRelation() == Relation.CHONG.ordinal()) {
                parent11 = member1.get().getParent();
            }
            if (parent11.getChildIndex() > parent22.getChildIndex()) {
                isHigherParent1 = -1;
            } else if (parent11.getChildIndex() < parent22.getChildIndex()) {
                isHigherParent1 = 1;
            }

            sideRelation1 = parent22.getGender() == GioiTinh.NAM.ordinal() ? Relation.CHA : Relation.ME;
        }

        if (higher2 && level != 0) {
            NodeMemberModel parent11 = member1.get().getParent();

            NodeMemberModel parent22 = member2.get();
            if (parent22.getRelation() == Relation.VO.ordinal() || parent22.getRelation() == Relation.CHONG.ordinal()) {
                parent22 = member2.get().getParent();
            }
            if (parent22.getChildIndex() > parent11.getChildIndex()) {
                isHigherParent2 = -1;
            } else if (parent22.getChildIndex() < parent11.getChildIndex()) {
                isHigherParent2 = 1;
            }
            sideRelation2 = parent11.getGender() == GioiTinh.NAM.ordinal() ? Relation.CHA : Relation.ME;
        }

        String relation1 = CachGoiTen.getInstance().getName(member1.get().getGender(), level, higher1, isOutSide1, isParent, member1.get().getRelation(), isHigherParent1, sideRelation1);
        String relation2 = CachGoiTen.getInstance().getName(member2.get().getGender(), level, higher2, isOutSide2, isParent, member2.get().getRelation(), isHigherParent2, sideRelation2);
        System.out.println("relation 1" + relation1);
        System.out.println("relation 2" + relation2);
        System.out.println("detail 1:" + member1.get().getGender() + "_" + level + "_" + higher1 + "_" + isOutSide1 + "_" + isParent + "_" + member1.get().getRelation()+ "_" + isHigherParent1 + "_" + sideRelation1);
        System.out.println("detail 2:" + member2.get().getGender() + "_" + level + "_" + higher2 + "_" + isOutSide2 + "_" + isParent + "_" + member2.get().getRelation()+ "_" + isHigherParent2 + "_" + sideRelation2);
        String resultRelation;
        if(relation1 != null && relation1.split("-").length > 1 && higher1) {
            resultRelation = relation1;
        }else if(relation2 != null && relation2.split("-").length > 1 && higher2) {
            resultRelation = relation2;
        }else {
            //result relation luon la cao-thap
            if(higher1) {
                resultRelation = relation1 + "-" + relation2;
            }else {
                resultRelation = relation2 + "-" + relation1;
            }
        }

        //kiem tra lai vi tri ai lon hon
        String resultRelation1 = resultRelation.split("-")[0]; // cao
        String resultRelation2 = resultRelation.split("-")[1]; // thap
        if(higher1) {
            //cao-thap
            resultRelation = resultRelation1 + "-" + resultRelation2;
        }else {
            //thap- cao
            resultRelation = resultRelation2 + "-" + resultRelation1;
        }
        System.out.println("member 1 goi 2 la " + relation1);
        System.out.println("member 2 goi 1 la " + relation2);
        return new ResponseEntity<>(resultRelation, HttpStatus.OK);
    }

    /*@GetMapping(value = "/rest/genealogy/{idGenealogy}/pedigree/{idPedigree}/list-member-tree")
    public ResponseEntity<?> getListMemberTree (Principal principal,
                                                @PathVariable(value = "idGenealogy") int idGenealogy,
                                                @PathVariable(value = "idPedigree") int idPedigree
    ) {
        Optional<PedigreeModel> pedigreeModel = pedigreeService.findById(idGenealogy);
        List<NodeMemberModel> nodeMemberModels = nodeMemberService.findAllByPedigreeAndPatchKeyStartsWith(pedigreeModel.get(), "r");
        List<ResUploadMember> dUploadMembers = new ArrayList<>();
        for(NodeMemberModel nodeMemberModel: nodeMemberModels) {
            ResUploadMember resUploadMember = new ResUploadMember();
            resUploadMember.setId(nodeMemberModel.getId());
            resUploadMember.setIdParent(nodeMemberModel.getParent() == null ? -1: nodeMemberModel.getParent().getId());
            resUploadMember.setIdMotherOrFather(nodeMemberModel.getMotherFatherId());
            resUploadMember.setRelation(nodeMemberModel.getRelation());
            resUploadMember.setLifeIndex(nodeMemberModel.getLifeIndex());
            resUploadMember.setName(nodeMemberModel.getName());
            resUploadMember.setNickName(nodeMemberModel.getDescriptionMemberModel().getNickName());
            resUploadMember.setBirthday(MyUltils.getStringFromDate(nodeMemberModel.getDescriptionMemberModel().getBirthday()));
            resUploadMember.setDeadday(MyUltils.getStringFromDate(nodeMemberModel.getDescriptionMemberModel().getDeadDay()));
            resUploadMember.setGender(nodeMemberModel.getGender());
            dUploadMembers.add(resUploadMember);
        }
        return new ResponseEntity<>(dUploadMembers, HttpStatus.OK);
    }*/
    @PostMapping(value = "/rest/member-tree/find/{idPedigree}", produces = "application/json")
    public Collection<DMemberTree> findMemberTree (Principal principal, @RequestParam(value = "textSearch", required = false, defaultValue = "") String strSearch, @PathVariable(value = "idPedigree") int idPedigree) {
        UserModel userModel = userRepository.findByEmail(principal.getName());
        Optional<PedigreeModel> pedigreeModel = pedigreeService.findById(idPedigree);
        List<NodeMemberModel> nodeMemberModels = nodeMemberService.findAllByPedigreeAndNameLike(pedigreeModel.get(), "%" + strSearch + "%");
        List<DMemberTree> dMemberTrees = new ArrayList<>(1);
        for(NodeMemberModel nodeMemberModel: nodeMemberModels) {
            DMemberTree dMemberTree = new DMemberTree();
            dMemberTree.setName(nodeMemberModel.getName());
            dMemberTree.setBirthday(MyUltils.getStringFromDate(nodeMemberModel.getDescriptionMemberModel().getBirthday()));
            dMemberTree.setDeadday(MyUltils.getStringFromDate(nodeMemberModel.getDescriptionMemberModel().getDeadDay()));
            dMemberTree.setGender(nodeMemberModel.getGender());
            dMemberTree.setId(nodeMemberModel.getId());
            dMemberTrees.add(dMemberTree);
        }
        return dMemberTrees;
    }

    @GetMapping(value = "/rest/member-tree/view-one-member-tree/{idMemberTree}", produces = "application/json")
    public ChartConfig getViewOneMemberTree(Principal principal,
                                         @PathVariable(name = "idMemberTree") int idMemberTree
    ) {
        ChartConfig chartConfig = new ChartConfig();
        Optional<NodeMemberModel> findNodeMember = nodeMemberService.findById(idMemberTree);
        addChildByPatchKey(findNodeMember, chartConfig, false);
        return chartConfig;
    }

    @GetMapping(value = "/rest/member-tree/view-two-member-tree/{idMemberTree1}/{idMemberTree2}", produces = "application/json")
    public ChartConfig getViewOneMemberTree(Principal principal,
                                            @PathVariable(name = "idMemberTree1") int idMemberTree1,
                                            @PathVariable(name = "idMemberTree2") int idMemberTree2
    ) {
        System.out.println("idMember1" + idMemberTree1);
        System.out.println("idMember2" + idMemberTree2);
        ChartConfig chartConfig = new ChartConfig();
        Optional<NodeMemberModel> findNodeMember1 = nodeMemberService.findById(idMemberTree1);
        addChildByPatchKey(findNodeMember1, chartConfig, true);
        Optional<NodeMemberModel> findNodeMember2 = nodeMemberService.findById(idMemberTree2);
        addChildByPatchKey(findNodeMember2, chartConfig, true);
        return chartConfig;
    }

    public void addChildByPatchKey (Optional<NodeMemberModel> findNodeMember, ChartConfig chartConfig, boolean addChildV2) {
        String patchKey = findNodeMember.get().getPatchKey();
        System.out.println("patch key" + patchKey);
        String[] listIdNodeMember = patchKey.split("_");
        for(int i = 0; i < listIdNodeMember.length; i++) {
            if(!listIdNodeMember[i].equals("r")) {
                Optional<NodeMemberModel> nodeMemberModel = nodeMemberService.findById(Integer.parseInt(listIdNodeMember[i]));
                System.out.println("node id " + nodeMemberModel.get().getId() + "mother fater" + nodeMemberModel.get().getMotherFatherId());
                if(nodeMemberModel.get().getMotherFatherId() != -1) {
                    Optional<NodeMemberModel> nodeMemberModelMother = nodeMemberService.findById(nodeMemberModel.get().getMotherFatherId());;
                    Child child = new Child();
                    child.setHTMLid(nodeMemberModelMother.get().getId() + "");
                    child.setId(nodeMemberModelMother.get().getId());
                    child.setChildrenDropLevel(nodeMemberModelMother.get().getChildIndex());
                    child.setRelation(nodeMemberModelMother.get().getRelation());
                    child.setGender(nodeMemberModelMother.get().getGender());
                    child.setImage(nodeMemberModelMother.get().getImage());
                    child.setHTMLclass("people_chart_node");
                    child.setPatchKey(nodeMemberModelMother.get().getPatchKey());
                    child.setIdFather(nodeMemberModelMother.get().getParent() == null ? -1 : nodeMemberModelMother.get().getParent().getId());
                    child.setIdMother(nodeMemberModelMother.get().getMotherFatherId() == null ? -1 : nodeMemberModelMother.get().getMotherFatherId());
                    Text text = new Text();
                    text.setTitle(MyUltils.getStringFromDate(nodeMemberModelMother.get().getDescriptionMemberModel().getBirthday()) + " - " + MyUltils.getStringFromDate(nodeMemberModelMother.get().getDescriptionMemberModel().getDeadDay()));
                    text.setName(nodeMemberModelMother.get().getName());
                    child.setText(text);
                    if(addChildV2) {
                        chartConfig.addChildVersion2(child);
                    }else {
                        chartConfig.addChild(child);
                    }
                }
                Child child = new Child();
                child.setHTMLid(nodeMemberModel.get().getId() + "");
                child.setId(nodeMemberModel.get().getId());
                child.setChildrenDropLevel(nodeMemberModel.get().getChildIndex());
                child.setRelation(nodeMemberModel.get().getRelation());
                child.setGender(nodeMemberModel.get().getGender());
                child.setImage(nodeMemberModel.get().getImage());
                child.setHTMLclass("people_chart_node");
                child.setPatchKey(nodeMemberModel.get().getPatchKey());
                child.setIdFather(nodeMemberModel.get().getParent() == null ? -1 : nodeMemberModel.get().getParent().getId());
                child.setIdMother(nodeMemberModel.get().getMotherFatherId() == null ? -1 : nodeMemberModel.get().getMotherFatherId());
                Text text = new Text();
                text.setTitle(MyUltils.getStringFromDate(nodeMemberModel.get().getDescriptionMemberModel().getBirthday()) + " - " + MyUltils.getStringFromDate(nodeMemberModel.get().getDescriptionMemberModel().getDeadDay()));
                text.setName(nodeMemberModel.get().getName());
                child.setText(text);
                if(addChildV2) {
                    chartConfig.addChildVersion2(child);
                }else {
                    chartConfig.addChild(child);
                }
            }
        }

        if(findNodeMember.get().getMotherFatherId() != -1) {
            Optional<NodeMemberModel> nodeMemberModelMother = nodeMemberService.findById(findNodeMember.get().getMotherFatherId());
            Child child = new Child();
            child.setHTMLid(nodeMemberModelMother.get().getId() + "");
            child.setId(nodeMemberModelMother.get().getId());
            child.setChildrenDropLevel(nodeMemberModelMother.get().getChildIndex());
            child.setRelation(nodeMemberModelMother.get().getRelation());
            child.setGender(nodeMemberModelMother.get().getGender());
            child.setImage(nodeMemberModelMother.get().getImage());
            child.setHTMLclass("people_chart_node");
            child.setPatchKey(nodeMemberModelMother.get().getPatchKey());
            child.setIdFather(nodeMemberModelMother.get().getParent() == null ? -1 : nodeMemberModelMother.get().getParent().getId());
            child.setIdMother(nodeMemberModelMother.get().getMotherFatherId() == null ? -1 : nodeMemberModelMother.get().getMotherFatherId());
            Text text = new Text();
            text.setTitle(MyUltils.getStringFromDate(nodeMemberModelMother.get().getDescriptionMemberModel().getBirthday()) + " - " + MyUltils.getStringFromDate(nodeMemberModelMother.get().getDescriptionMemberModel().getDeadDay()));
            text.setName(nodeMemberModelMother.get().getName());
            child.setText(text);
            if(addChildV2) {
                chartConfig.addChildVersion2(child);
            }else {
                chartConfig.addChild(child);
            }
        }

        Child child = new Child();
        child.setHTMLid(findNodeMember.get().getId() + "");
        child.setId(findNodeMember.get().getId());
        child.setChildrenDropLevel(findNodeMember.get().getChildIndex());
        child.setRelation(findNodeMember.get().getRelation());
        child.setGender(findNodeMember.get().getGender());
        child.setImage(findNodeMember.get().getImage());
        child.setHTMLclass("people_chart_node");
        child.setPatchKey(findNodeMember.get().getPatchKey());
        child.setIdFather(findNodeMember.get().getParent() == null ? -1 : findNodeMember.get().getParent().getId());
        child.setIdMother(findNodeMember.get().getMotherFatherId() == null ? -1 : findNodeMember.get().getMotherFatherId());
        Text text = new Text();
        text.setTitle(MyUltils.getStringFromDate(findNodeMember.get().getDescriptionMemberModel().getBirthday()) + " - " + MyUltils.getStringFromDate(findNodeMember.get().getDescriptionMemberModel().getDeadDay()));
        text.setName(findNodeMember.get().getName());
        child.setText(text);
        if(addChildV2) {
            chartConfig.addChildVersion2(child);
        }else {
            chartConfig.addChild(child);
        }
    }
}
