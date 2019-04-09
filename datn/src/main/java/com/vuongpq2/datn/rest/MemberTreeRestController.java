package com.vuongpq2.datn.rest;

import com.vuongpq2.datn.config.ConfigFormat;
import com.vuongpq2.datn.config.tree.ChartConfig;
import com.vuongpq2.datn.config.tree.Child;
import com.vuongpq2.datn.config.tree.Text;
import com.vuongpq2.datn.data.Enum.Permission;
import com.vuongpq2.datn.data.Enum.Relation;
import com.vuongpq2.datn.data.GioiTinh;
import com.vuongpq2.datn.data.model.DDetailNodeMember;
import com.vuongpq2.datn.data.model.DHusbandOrWife;
import com.vuongpq2.datn.data.model.DInfoFormAddChild;
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
        for(int i = 0; i < nodeMemberModels.size(); i++) {
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
            child.setIdFather(nodeMemberModel.getParent() == null ? - 1: nodeMemberModel.getParent().getId());
            child.setIdMother(nodeMemberModel.getMotherFatherId() == null ? -1: nodeMemberModel.getMotherFatherId());
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
        if(userPermissionModel == null || !PermissionUtils.isCanAddMemberTree(Permission.byCode(userPermissionModel.getPermission().getCode()))) {
            return null;
        }

        Optional<PedigreeModel> pedigreeModel = pedigreeService.findById(idPedigree);
        NodeMemberModel nodeMemberModel = new NodeMemberModel();
        DescriptionMemberModel descriptionMemberModel = new DescriptionMemberModel();

        if(addChildInputIdMotherFather == null || addChildInputIdMotherFather.equals("")) {
            addChildInputIdMotherFather = "-1";
        }
        if(addChildIdParent == null || addChildIdParent.equals("")) {
            addChildIdParent = "-1";
        }
        if(addChildInputConThu.equals("")) {
            addChildInputConThu = "-1";
        }

        if(addChildInputRelation.equals("")) {
            addChildInputRelation = "-1";
        }
        Optional<NodeMemberModel> parent = null;
        parent = nodeMemberService.findById(Integer.parseInt(addChildIdParent));
        if(!parent.isPresent()) {
            nodeMemberModel.setParent(null);
            nodeMemberModel.setLifeIndex(0);
        }else {
            nodeMemberModel.setParent(parent.get());
            nodeMemberModel.setLifeIndex(parent.get().getLifeIndex() + 1);
        }


        nodeMemberModel.setMotherFatherId(Integer.parseInt(addChildInputIdMotherFather));
        nodeMemberModel.setRelation(Integer.parseInt(addChildInputRelation));
        nodeMemberModel.setChildIndex(Integer.parseInt(addChildInputConThu));
        nodeMemberModel.setGender(Integer.parseInt(addChildInputGender));
        nodeMemberModel.setName(addChildInputName);
        nodeMemberModel.setPedigree(pedigreeModel.get());
        nodeMemberModel.setPatchKey(NodeMemberModel.getPathkeyByParent(parent.isPresent()? parent.get(): null));
        descriptionMemberModel.setNickName(addChildInputNickName);
        descriptionMemberModel.setAddress(addChildInputAddress);
        descriptionMemberModel.setBirthday(MyUltils.getDate(addChildInputBirthday));
        descriptionMemberModel.setDeadDay(MyUltils.getDate(addChildInputDeadDay));
        descriptionMemberModel.setDegree(addChildInputDegree);
        descriptionMemberModel.setDescription(addChildInputDes);
        descriptionMemberModel.setExtraData(addChildInputDataExtra);
        nodeMemberService.add(nodeMemberModel, descriptionMemberModel);
        return ResponseEntity.ok("success");

    }

    @GetMapping(value = "/rest/genealogy/{idGenealogy}/pedigree/{idPedigree}/member-tree/{idMemberTree}/get-info-add")
    public ResponseEntity<?> getInfoFormAddChild (Principal principal,
                                                  @PathVariable(value = "idGenealogy") int idGenealogy,
                                                  @PathVariable(value = "idPedigree") int idPedigree,
                                                  @PathVariable(value = "idMemberTree") int idMemberTree
                                                  ) {
        UserModel userModel = userRepository.findByEmail(principal.getName());
        UserPermissionModel userPermissionModel = userPermissionRepository.findTopByUserAndGenealogy_Id(userModel, idGenealogy);
        if(!PermissionUtils.isCanAddMemberTree(Permission.byCode(userPermissionModel.getPermission().getCode()))) {
            return new ResponseEntity<>("", HttpStatus.EXPECTATION_FAILED);
        }
        Optional<NodeMemberModel> parent = nodeMemberService.findById(idMemberTree);
        if(!parent.isPresent()) {
            return new ResponseEntity<>(idMemberTree, HttpStatus.NOT_FOUND);
        }
        DInfoFormAddChild dInfoFormAddChild = new DInfoFormAddChild();
        dInfoFormAddChild.setId(parent.get().getId());
        dInfoFormAddChild.setName(parent.get().getName());
        int relationNeedFind = parent.get().getGender() == GioiTinh.NAM.ordinal() ? Relation.VO.ordinal(): Relation.CHONG.ordinal();
        Optional<PedigreeModel> pedigreeModel = pedigreeService.findById(idPedigree);
        List<NodeMemberModel> listHusbandWifes = nodeMemberService.findAllByPedigreeAndPatchKeyAndRelationEquals(pedigreeModel.get(), NodeMemberModel.getPathkeyByParent(parent.get()), relationNeedFind);

        List<DHusbandOrWife> husbandOrWifeList = new ArrayList<>(1);
        DHusbandOrWife unknown = new DHusbandOrWife();
        unknown.setName("Không rõ");
        unknown.setId(-1);
        husbandOrWifeList.add(unknown);
        for(NodeMemberModel nodeMemberModel: listHusbandWifes) {
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
        if(!nodeMemberModel.isPresent()) {
            return new ResponseEntity<>(idMemberTree, HttpStatus.NOT_FOUND);
        }
        Optional<NodeMemberModel> nodeParent = nodeMemberService.findById(nodeMemberModel.get().getParent() != null ? nodeMemberModel.get().getParent().getId(): -1);
        Optional<NodeMemberModel> nodeFatherOrMother = nodeMemberService.findById(nodeMemberModel.get().getMotherFatherId());
        DescriptionMemberModel descriptionMemberModel = nodeMemberModel.get().getDescriptionMemberModel();
        DDetailNodeMember dDetailNodeMember = new DDetailNodeMember();
        dDetailNodeMember.setId(idMemberTree);
        dDetailNodeMember.setImg(nodeMemberModel.get().getImage());
        dDetailNodeMember.setChildIndex(nodeMemberModel.get().getChildIndex());
        dDetailNodeMember.setName(nodeMemberModel.get().getName());
        dDetailNodeMember.setNameParent(nodeParent.isPresent()? nodeParent.get().getName(): "Không rõ");
        dDetailNodeMember.setNameFatherOrMother(nodeFatherOrMother.isPresent()? nodeFatherOrMother.get().getName(): "Không rõ");
        dDetailNodeMember.setNickname(descriptionMemberModel.getNickName());
        dDetailNodeMember.setAddress(descriptionMemberModel.getAddress());
        dDetailNodeMember.setRelation(nodeMemberModel.get().getRelation());
        dDetailNodeMember.setBirthDay(MyUltils.getStringFromDate(descriptionMemberModel.getBirthday()));
        dDetailNodeMember.setDeadDay(MyUltils.getStringFromDate(descriptionMemberModel.getDeadDay()));
        dDetailNodeMember.setDegree(descriptionMemberModel.getDegree());
        dDetailNodeMember.setDes(descriptionMemberModel.getDescription());
        dDetailNodeMember.setExtraData(descriptionMemberModel.getExtraData());
        return new ResponseEntity<>(dDetailNodeMember, HttpStatus.OK);
    }
}
