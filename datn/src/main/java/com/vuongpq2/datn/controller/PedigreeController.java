package com.vuongpq2.datn.controller;

import com.vuongpq2.datn.config.ErrorKey;
import com.vuongpq2.datn.data.Enum.Permission;
import com.vuongpq2.datn.data.Enum.Relation;
import com.vuongpq2.datn.data.GioiTinh;
import com.vuongpq2.datn.data.model.DUploadMember;
import com.vuongpq2.datn.data.model.ResUploadMember;
import com.vuongpq2.datn.model.*;
import com.vuongpq2.datn.repository.UserPermissionRepository;
import com.vuongpq2.datn.repository.UserRepository;
import com.vuongpq2.datn.service.GenealogyService;
import com.vuongpq2.datn.service.NodeMemberService;
import com.vuongpq2.datn.service.PedigreeService;
import com.vuongpq2.datn.service.StorageService;
import com.vuongpq2.datn.utils.MyUltils;
import com.vuongpq2.datn.utils.PermissionUtils;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.swing.text.html.Option;
import javax.validation.Valid;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.*;

@Controller
public class PedigreeController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PedigreeService pedigreeService;
    @Autowired
    UserPermissionRepository userPermissionRepository;
    @Autowired
    GenealogyService genealogyService;
    @Autowired
    StorageService storageService;
    @Autowired
    NodeMemberService nodeMemberService;

    @GetMapping(value = "/genealogy/{idGenealogy}/pedigree")
    public ModelAndView getListPedigreeByGenealogyId(Principal principal, @PathVariable(value = "idGenealogy", required = false) int idGenealogy) {
        UserModel userModel = userRepository.findByEmail(principal.getName());
        if (userModel == null) {
            return new ModelAndView("/genealogy");
        }
        UserPermissionModel userPermissionModel = userPermissionRepository.findTopByUserAndGenealogy_Id(userModel, idGenealogy);
        if (userPermissionModel != null) {
            Permission permission = Permission.byCode(userPermissionModel.getPermission().getCode());
            if (PermissionUtils.isCanViewPedigree(permission)) {
                ModelAndView mv = new ModelAndView("/genealogy/pedigree");
                mv.addObject("genealogy", userPermissionModel.getGenealogyModel());
                mv.addObject("idGenealogy", idGenealogy);
                mv.addObject("idPermission", permission.getCode());
                return mv;
            } else {
                return new ModelAndView("/genealogy");
            }
        }
        return new ModelAndView("/genealogy");
    }

    @GetMapping(value = "/genealogy/{idGenealogy}/pedigree/add")
    public ModelAndView getAddPedigree(Principal principal, @PathVariable(value = "idGenealogy", required = false) int idGenealogy) {
        if (principal == null) {
            return new ModelAndView("/genealogy");
        }
        PedigreeModel pedigreeModel = new PedigreeModel();
        pedigreeModel.setName("");
        pedigreeModel.setHistory("");
        ModelAndView mv = new ModelAndView("/genealogy/pedigree-add");
        mv.addObject("idGenealogy", idGenealogy);
        mv.addObject("pedigree", pedigreeModel);
        return mv;
    }

    @PostMapping(value = "/genealogy/{idGenealogy}/pedigree/add", produces = "application/json")
    public ModelAndView postAddPedigree(Principal principal, @Valid @ModelAttribute(value = "pedigree") PedigreeModel pedigreeModel, @PathVariable(name = "idGenealogy") int idGenealogy) {
        ModelAndView mv = new ModelAndView("/genealogy/pedigree-detail");
        Optional<GenealogyModel> genealogyModel = genealogyService.findById(idGenealogy);
        pedigreeService.add(pedigreeModel, idGenealogy);

        UserModel user = userRepository.findByEmail(principal.getName());
        UserPermissionModel userPermissionModel = userPermissionRepository.findTopByUserAndGenealogy_Id(user, idGenealogy);
        mv.addObject("idPermission", userPermissionModel.getPermission().getCode());
        mv.addObject("genealogy", genealogyModel.get());
        mv.addObject("pedigree", pedigreeModel);
        return mv;
    }

    @GetMapping(value = "/genealogy/{idGenealogy}/pedigree/{idPedigree}/detail", produces = "application/json")
    public ModelAndView getDetailPedigree(Principal principal, @PathVariable(value = "idGenealogy", required = false) int idGenealogy, @PathVariable(value = "idPedigree", required = false) int idPedigree) {
        ModelAndView mv = new ModelAndView("/genealogy/pedigree-detail");
        if (principal == null) {
            return new ModelAndView("/genealogy");
        }
        UserModel userModel = userRepository.findByEmail(principal.getName());
        Optional<GenealogyModel> genealogyModel = genealogyService.findById(idGenealogy);
        Optional<PedigreeModel> pedigreeModel = pedigreeService.findById(idPedigree);
        UserPermissionModel userPermissionModel = userPermissionRepository.findTopByUserAndGenealogy_Id(userModel, idGenealogy);
        mv.addObject("idPermission", userPermissionModel.getPermission().getCode());
        mv.addObject("pedigree", pedigreeModel.get());
        mv.addObject("genealogy", genealogyModel.get());
        return mv;
    }

    @GetMapping(value = "/genealogy/{idGenealogy}/pedigree/{idPedigree}/edit", produces = "application/json")
    public ModelAndView getEditPedigree(Principal principal, @PathVariable(value = "idGenealogy", required = false) int idGenealogy, @PathVariable(value = "idPedigree", required = false) int idPedigree) {
        if (principal == null) {
            return new ModelAndView("/genealogy");
        }
        ModelAndView mv = new ModelAndView("/genealogy/pedigree-edit");
        UserModel userModel = userRepository.findByEmail(principal.getName());
        UserPermissionModel userPermissionModel = userPermissionRepository.findTopByUserAndGenealogy_Id(userModel, idGenealogy);
        Permission permission = Permission.byCode(userPermissionModel.getPermission().getCode());
        Optional<GenealogyModel> genealogyModel = genealogyService.findById(idGenealogy);
        Optional<PedigreeModel> pedigreeModel = pedigreeService.findById(idPedigree);
        if (PermissionUtils.isCanEditPedigree(permission)) {
            mv.addObject("genealogy", genealogyModel.get());
            mv.addObject("pedigree", pedigreeModel.get());
            return mv;
        } else {
            return new ModelAndView("/genealogy");
        }
    }

    @PostMapping(value = "/genealogy/{idGenealogy}/pedigree/{idPedigree}/edit", produces = "application/json")
    public ModelAndView postEditPedigree(Principal principal, @Valid @ModelAttribute(value = "pedigree") PedigreeModel pedigreeModel, @PathVariable(value = "idGenealogy", required = false) int idGenealogy, @PathVariable(value = "idPedigree", required = false) int idPedigree) {
        if(principal == null) {
            return new ModelAndView("/genealogy");
        }
        Optional<PedigreeModel> pedigreeModel1 = pedigreeService.findById(idPedigree);
        pedigreeModel1.get().setHistory(pedigreeModel.getHistory());
        pedigreeModel1.get().setName(pedigreeModel.getName());
        pedigreeService.update(pedigreeModel1.get());
        UserModel userModel = userRepository.findByEmail(principal.getName());
        UserPermissionModel userPermissionModel = userPermissionRepository.findTopByUserAndGenealogy_Id(userModel, idGenealogy);
        if (userPermissionModel != null) {
            Permission permission = Permission.byCode(userPermissionModel.getPermission().getCode());
            if (PermissionUtils.isCanViewPedigree(permission)) {
                ModelAndView mv = new ModelAndView("/genealogy/pedigree");
                mv.addObject("genealogy", userPermissionModel.getGenealogyModel());
                mv.addObject("idGenealogy", idGenealogy);
                mv.addObject("idPermission", permission.getCode());
                return mv;
            } else {
                return new ModelAndView("/genealogy");
            }
        }
        return new ModelAndView("/genealogy");
    }

    @RequestMapping(value = "/genealogy/{idGenealogy}/pedigree/{idPedigree}/import", method = RequestMethod.GET)
    public ModelAndView getImportNodeMember (Principal principal,
                                            @PathVariable (value = "idGenealogy") int idGenealogy,
                                             @PathVariable (value = "idPedigree") int idPedigree
                                             ) {
        ModelAndView mv = new ModelAndView("/genealogy/pedigree-import");
        mv.addObject("idPedigree",idPedigree);
        mv.addObject("idGenealogy",idGenealogy);
        return mv;
    }
    @RequestMapping(value = "/genealogy/{idGenealogy}/pedigree/{idPedigree}/import", method = RequestMethod.POST)
    public ResponseEntity<?> uploadListPeople(
            @RequestParam(value = "idGenealogy",required = false,defaultValue = "") int idGenealogy,
            @RequestParam(value = "idPedigree",required = false,defaultValue = "") int idPedigree,
            @RequestParam("files") MultipartFile uploadfiles) {

        String uploadedFileName = uploadfiles.getOriginalFilename();
        Optional<PedigreeModel> pedigreeModel = pedigreeService.findById(idPedigree);
        if (StringUtils.isEmpty(uploadedFileName)) {
            return new ResponseEntity(ErrorKey.ERROR_EMPTY, HttpStatus.OK);
        }
        try {

            String fileName = idGenealogy + idPedigree + System.currentTimeMillis() + "update" + getExtensionsFile(uploadedFileName);
            saveUploadedFiles(uploadfiles, fileName);
            TreeMap<Integer, DUploadMember> listUploadMember = readExcel(fileName);
            List<ResUploadMember> resUploadMembers = saveMemberFromReading(listUploadMember, pedigreeModel.get());
            return new ResponseEntity<>(resUploadMembers , HttpStatus.OK);

        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    public List<ResUploadMember> saveMemberFromReading (TreeMap<Integer, DUploadMember> dUploadMemberTreeMap, PedigreeModel pedigreeModel) {
        List<ResUploadMember> resUploadMembers = new ArrayList<>();
        nodeMemberService.deleteAllByPedigreeAndPatchKeyStartsWith(pedigreeModel, "r");
        Map<Integer, Integer> savedIdByKey = new HashMap<>();
        dUploadMemberTreeMap.forEach((key, value) -> {
            ResUploadMember resUploadMember = new ResUploadMember();
            NodeMemberModel nodeMemberModel = new NodeMemberModel();
            DescriptionMemberModel descriptionMemberModel = new DescriptionMemberModel();
            if(value.getIdParent() == -1) {
                nodeMemberModel.setParent(null);
                resUploadMember.setIdParent(-1);
                nodeMemberModel.setPatchKey("r");
            }else {
                int idParent = savedIdByKey.get(value.getIdParent()) == null ? value.getId(): savedIdByKey.get(value.getIdParent());
                Optional<NodeMemberModel> parent = nodeMemberService.findById(idParent);
                nodeMemberModel.setParent(parent.get());
                resUploadMember.setIdParent(nodeMemberModel.getParent().getId());
                nodeMemberModel.setPatchKey(NodeMemberModel.getPathkeyByParent(parent.get()));
            }
            nodeMemberModel.setName(value.getName());
            nodeMemberModel.setPedigree(pedigreeModel);
            int idMotherOrFather = savedIdByKey.get(value.getIdMotherOrFather()) == null ? value.getIdMotherOrFather(): savedIdByKey.get(value.getIdMotherOrFather());
            nodeMemberModel.setMotherFatherId(idMotherOrFather);
            nodeMemberModel.setGender(value.getGender().ordinal());
            nodeMemberModel.setChildIndex(value.getChildIdx());
            nodeMemberModel.setLifeIndex(value.getLiftIdx());
            Random rd = new Random();
            String img = nodeMemberModel.getGender() == GioiTinh.NAM.ordinal()? "/img/avatar-default-nam_"+ (rd.nextInt(3) + 1)+ ".png" : "/img/avatar-default-nu_" + (rd.nextInt(4) + 1) + ".png";
            System.out.println("image " + img);
            nodeMemberModel.setImage(value.getImage().equals("") ? img: value.getImage());
            nodeMemberModel.setRelation(value.getRelation().ordinal());
            descriptionMemberModel.setNickName(value.getNickName());
            descriptionMemberModel.setDegree(value.getDegree());
            descriptionMemberModel.setDescription(value.getDes());
            descriptionMemberModel.setExtraData(value.getExtraData());
            descriptionMemberModel.setBirthday(value.getBirthDay());
            descriptionMemberModel.setDeadDay(value.getDeadDay());
            descriptionMemberModel.setAddress(value.getAddress());
            NodeMemberModel nodeMemberModel1 = nodeMemberService.add(nodeMemberModel, descriptionMemberModel);
            savedIdByKey.put(value.getId(), nodeMemberModel1.getId());
            resUploadMember.setId(nodeMemberModel.getId());
            resUploadMember.setBirthday(MyUltils.getStringFromDate(value.getBirthDay()));
            resUploadMember.setDeadday(MyUltils.getStringFromDate(value.getDeadDay()));
            resUploadMember.setIdMotherOrFather(nodeMemberModel.getMotherFatherId());
            resUploadMember.setNickName(value.getNickName());
            resUploadMember.setName(value.getName());
            resUploadMember.setLifeIndex(value.getLiftIdx());
            resUploadMember.setRelation(value.getRelation().ordinal());
            resUploadMember.setGender(value.getGender().ordinal());
            resUploadMembers.add(resUploadMember);
        });
        return resUploadMembers;
    }


    public TreeMap<Integer, DUploadMember> readExcel(String fileName) {
        TreeMap<Integer, DUploadMember> listMember = new TreeMap<>();
        try {
            FileInputStream fs = new FileInputStream(Paths.get("upload-dir") + "/" + fileName);
            Workbook wb = Workbook.getWorkbook(fs);
            Sheet sh = wb.getSheet(0);
            int totalRows = sh.getRows();

            for (int i = 1; i < totalRows; i++) {
                int stt = Integer.parseInt(sh.getCell(0, i).getContents());
                int idParent = Integer.parseInt(sh.getCell(1, i).getContents());
                int idMotherOrFather = Integer.parseInt(sh.getCell(2, i).getContents());
                int lifeIdx = Integer.parseInt(sh.getCell(3, i).getContents());
                Relation relation = getRelationByString(sh.getCell(4, i).getContents());
                String name = sh.getCell(5, i).getContents();
                String nickName = sh.getCell(6, i).getContents();
                GioiTinh gender = getGender(sh.getCell(7, i).getContents());
                int childIdx = Integer.parseInt(sh.getCell(8, i).getContents());
                Date birthDay = MyUltils.getDate(sh.getCell(9, i).getContents());
                System.out.println("birth day " + sh.getCell(9, i).getContents() + "_" + MyUltils.getStringFromDate(birthDay));
                Date deadDay = MyUltils.getDate(sh.getCell(10, i).getContents());
                String address = sh.getCell(11, i).getContents();
                String degree = sh.getCell(12, i).getContents();
                String image = sh.getCell(13, i).getContents();
                String des = sh.getCell(14, i).getContents();
                String extraData = sh.getCell(15, i).getContents();

                DUploadMember dUploadMember = new DUploadMember();
                dUploadMember.setId(stt);
                dUploadMember.setIdParent(idParent);
                dUploadMember.setIdMotherOrFather(idMotherOrFather);
                dUploadMember.setLiftIdx(lifeIdx);
                dUploadMember.setRelation(relation);
                dUploadMember.setName(name);
                dUploadMember.setNickName(nickName);
                dUploadMember.setGender(gender);
                dUploadMember.setChildIdx(childIdx);
                dUploadMember.setBirthDay(birthDay);
                dUploadMember.setDeadDay(deadDay);
                dUploadMember.setAddress(address);
                dUploadMember.setDegree(degree);
                dUploadMember.setImage(image);
                dUploadMember.setDes(des);
                dUploadMember.setExtraData(extraData);
                listMember.put(dUploadMember.getId(), dUploadMember);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listMember;
    }

    public GioiTinh getGender (String gender) {
        String gender1 = gender.toLowerCase();
        switch (gender1) {
            case "nam":
                return GioiTinh.NAM;
            case "nu":
                return GioiTinh.NU;
            case "nữ":
                return GioiTinh.NU;
        }
        return GioiTinh.KHONG_RO;
    }

    public Relation getRelationByString(String relation) {
        String relation1 = relation.toLowerCase();
        Relation result;
        switch (relation1) {
            case "vo":
            case "vợ":
            case "vơ":
                result = Relation.VO;
                break;
            case "chong":
            case "chồng":
            case "chông":
                result = Relation.CHONG;
                break;
            case "cha":
                result = Relation.CHA;
                break;
            case "me":
            case "mẹ":
                result = Relation.ME;
                break;
            default:
                result = Relation.NONE;
        }
        return result;
    }

    private void saveUploadedFiles(MultipartFile file,String fileName) throws IOException {
        if (file.isEmpty()) {
            return;
        }
        storageService.store(file,fileName);
    }

    private String getExtensionsFile(String uploadedFileName) {
        return uploadedFileName.substring(uploadedFileName.lastIndexOf("."));
    }

}
