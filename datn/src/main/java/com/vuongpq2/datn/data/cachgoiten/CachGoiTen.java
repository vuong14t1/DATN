package com.vuongpq2.datn.data.cachgoiten;

import com.vuongpq2.datn.data.Enum.Relation;
import com.vuongpq2.datn.data.GioiTinh;
import com.vuongpq2.datn.data.model.DUploadMember;
import com.vuongpq2.datn.model.DescriptionMemberModel;
import com.vuongpq2.datn.model.NodeMemberModel;
import com.vuongpq2.datn.model.PedigreeModel;
import com.vuongpq2.datn.service.NodeMemberService;
import com.vuongpq2.datn.service.PedigreeService;
import com.vuongpq2.datn.utils.MyUltils;
import jxl.Sheet;
import jxl.Workbook;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.FileInputStream;
import java.nio.file.Paths;
import java.util.*;

public class CachGoiTen {
    private static CachGoiTen instance = null;
    @Autowired
    private NodeMemberService nodeMemberService;
    @Autowired
    private PedigreeService pedigreeService;
    public HashMap<String, String> container;
//    public ArrayList<String> container = new ArrayList<>();

    private CachGoiTen() {
        // Exists only to defeat instantiation.
        container = new HashMap<>();
        container.put(TenGoi.getKey(GioiTinh.NAM, 0, true, false, true, Relation.ME), "Anh ruột - Em ruột");
//        container.put(TenGoi.getKey(GioiTinh.NAM,0,false,false, true, Relation.ME),"Em ruột");

        container.put(TenGoi.getKey(GioiTinh.NAM, 0, true, false, false, Relation.ME), "Anh - Em");
//        container.put(TenGoi.getKey(GioiTinh.NAM,0,false,false, false, Relation.ME),"Em họ");

        container.put(TenGoi.getKey(GioiTinh.NU, 0, true, false, true, Relation.ME), "Chị ruột - Em ruột");
//        container.put(TenGoi.getKey(GioiTinh.NU,0,false,false, true, Relation.ME),"Em gái ruột");

        container.put(TenGoi.getKey(GioiTinh.NU, 0, true, false, false, Relation.ME), "Chị - Em");
//        container.put(TenGoi.getKey(GioiTinh.NU,0,false,false, false, Relation.ME),"Em họ");

        container.put(TenGoi.getKey(GioiTinh.NAM, 0, true, true, false, Relation.VO), "Anh rể - Em vợ");
//        container.put(TenGoi.getKey(GioiTinh.NAM,0,false,false, false, Relation.ME),"Em vợ");

        container.put(TenGoi.getKey(GioiTinh.NU, 0, true, true, false, Relation.CHONG), "Chị dâu - Em chồng");
//        container.put(TenGoi.getKey(GioiTinh.NU,0,false,false, false, Relation.ME),"Em chồng");

        container.put(TenGoi.getKey(GioiTinh.NAM, 0, false, false, false, Relation.ME), "Chồng - Vợ");
        container.put(TenGoi.getKey(GioiTinh.NU, 0, false, true, false, Relation.VO), "Vợ - Chồng");
        container.put(TenGoi.getKey(GioiTinh.NAM, 0, false, true, false, Relation.CHONG), "Chồng - Vợ");

        //
        container.put(TenGoi.getKey(GioiTinh.NAM, 1, true, false, false, Relation.NONE, 0, Relation.CHA), "Cha");
        container.put(TenGoi.getKey(GioiTinh.NAM, 1, true, false, false, Relation.CHA, 0, Relation.CHA), "Cha");
        container.put(TenGoi.getKey(GioiTinh.NAM, 1, true, true, true, Relation.CHONG, 0, Relation.ME), "Cha");

        container.put(TenGoi.getKey(GioiTinh.NAM, 1, false, false, false, Relation.ME, 0, Relation.NONE), "Con");
        container.put(TenGoi.getKey(GioiTinh.NU, 1, false, false, false, Relation.ME, 0, Relation.NONE), "Con");

        container.put(TenGoi.getKey(GioiTinh.NU, 1, true, false, false, Relation.NONE, 0, Relation.ME), "Mẹ");
        container.put(TenGoi.getKey(GioiTinh.NU, 1, true, false, false, Relation.CHA, 0, Relation.ME), "Mẹ");
        container.put(TenGoi.getKey(GioiTinh.NU, 1, true, true, true, Relation.VO, 0, Relation.CHA), "Mẹ");

        container.put(TenGoi.getKey(GioiTinh.NU, 1, false, false, true, Relation.ME, 0, Relation.NONE), "Con");
        container.put(TenGoi.getKey(GioiTinh.NAM, 1, false, false, true, Relation.ME, 0, Relation.NONE), "Con");

        //chông của dì hoặc cô
        container.put(TenGoi.getKey(GioiTinh.NAM, 1, true, true, false, Relation.CHONG, 0, Relation.CHA), "Dượng-Cháu");
        container.put(TenGoi.getKey(GioiTinh.NAM, 1, true, true, false, Relation.CHONG, 0, Relation.ME), "Dượng-Cháu");
        container.put(TenGoi.getKey(GioiTinh.NAM, 1, false, true, false, Relation.NONE, 0, Relation.NONE), "Cháu");

        // anh của ba mình
        container.put(TenGoi.getKey(GioiTinh.NAM, 1, true, false, false, Relation.ME, 1, Relation.CHA), "Bác-Cháu");
        container.put(TenGoi.getKey(GioiTinh.NAM, 1, false, false, false, Relation.NONE, 0, Relation.NONE), "Cháu");
        // vợ của bác
        container.put(TenGoi.getKey(GioiTinh.NU, 1, true, true, false, Relation.VO, 1, Relation.CHA), "Bác gái-Cháu");

        container.put(TenGoi.getKey(GioiTinh.NAM, 1, true, false, false, Relation.ME, -1, Relation.CHA), "Chú-Cháu");

        // là vợ của chú
        container.put(TenGoi.getKey(GioiTinh.NU, 1, true, true, false, Relation.VO, -1, Relation.CHA), "Thím-Cháu");
        container.put(TenGoi.getKey(GioiTinh.NU, 1, false, true, false, Relation.NONE, 0, Relation.NONE), "Cháu");

        // em /anh của me mình
        container.put(TenGoi.getKey(GioiTinh.NAM, 1, true, false, false, Relation.ME, -1, Relation.ME), "Cậu-Cháu");
        container.put(TenGoi.getKey(GioiTinh.NAM, 1, true, false, false, Relation.ME, 1, Relation.ME), "Cậu-Cháu");
        container.put(TenGoi.getKey(GioiTinh.NAM, 1, false, false, false, Relation.NONE, 0, Relation.NONE), "Cháu");

        //vợ của cậu
        container.put(TenGoi.getKey(GioiTinh.NU, 1, true, true, false, Relation.VO, -1, Relation.ME), "Mợ-Cháu");


        //chị hoặc e gái của ba mình
        container.put(TenGoi.getKey(GioiTinh.NU, 1, true, false, false, Relation.ME, -1, Relation.CHA), "Cô-Cháu");
        container.put(TenGoi.getKey(GioiTinh.NU, 1, true, false, false, Relation.ME, 1, Relation.CHA), "Cô-Cháu");
        container.put(TenGoi.getKey(GioiTinh.NU, 1, false, true, false, Relation.NONE, 0, Relation.NONE), "Cháu");

        //chong cua chi or e gai cua ba minh
        container.put(TenGoi.getKey(GioiTinh.NAM, 1, true, true, false, Relation.CHONG, -1, Relation.CHA), "Chú-Cháu");
        container.put(TenGoi.getKey(GioiTinh.NAM, 1, true, true, false, Relation.CHONG, 1, Relation.CHA), "Chú-Cháu");

        //chị hoặc e gái của mẹ mình
        container.put(TenGoi.getKey(GioiTinh.NU, 1, true, false, false, Relation.ME, -1, Relation.ME), "Gì-Cháu");
        container.put(TenGoi.getKey(GioiTinh.NU, 1, true, false, false, Relation.ME, 1, Relation.ME), "Gì-Cháu");

        // chong cua chi , e gai cua me minh
        container.put(TenGoi.getKey(GioiTinh.NAM, 1, true, true, false, Relation.CHONG, -1, Relation.ME), "Gì-Cháu");
        container.put(TenGoi.getKey(GioiTinh.NAM, 1, true, true, false, Relation.CHONG, 1, Relation.ME), "Gì-Cháu");

        container.put(TenGoi.getKey(GioiTinh.NU, 1, false, false, false, Relation.NONE, 0, Relation.NONE), "Cháu");

        container.put(TenGoi.getKey(GioiTinh.NAM, 2, true, false, false, Relation.NONE, 0, Relation.CHA), "Ông Nội");
        container.put(TenGoi.getKey(GioiTinh.NAM, 2, false, false, false, Relation.NONE, 0, Relation.NONE), "Cháu");
        container.put(TenGoi.getKey(GioiTinh.NU, 2, true, false, false, Relation.NONE, 0, Relation.CHA), "Bà nội");
        container.put(TenGoi.getKey(GioiTinh.NU, 2, false, false, false, Relation.NONE, 0, Relation.NONE), "Cháu");
        container.put(TenGoi.getKey(GioiTinh.NAM, 2, true, false, false, Relation.NONE, 0, Relation.ME), "Ông ngoại");
        container.put(TenGoi.getKey(GioiTinh.NAM, 2, false, false, false, Relation.NONE, 0, Relation.NONE), "Cháu");
        container.put(TenGoi.getKey(GioiTinh.NU, 2, true, false, false, Relation.NONE, 0, Relation.ME), "Bà ngoại");
        container.put(TenGoi.getKey(GioiTinh.NU, 2, false, false, false, Relation.NONE, 0, Relation.NONE), "Cháu");

        container.put(TenGoi.getKey(GioiTinh.NAM, 3, true, false, false, Relation.NONE, 0, Relation.CHA), "Cụ ông nội");
        container.put(TenGoi.getKey(GioiTinh.NAM, 3, false, false, false, Relation.NONE, 0, Relation.NONE), "Chắt");
        container.put(TenGoi.getKey(GioiTinh.NU, 3, true, false, false, Relation.NONE, 0, Relation.CHA), "Cụ bà nội");
        container.put(TenGoi.getKey(GioiTinh.NU, 3, false, false, false, Relation.NONE, 0, Relation.NONE), "Chắt");
        container.put(TenGoi.getKey(GioiTinh.NAM, 3, true, false, false, Relation.NONE, 0, Relation.ME), "Cụ ông ngoại");
        container.put(TenGoi.getKey(GioiTinh.NAM, 3, false, false, false, Relation.NONE, 0, Relation.NONE), "Chắt");
        container.put(TenGoi.getKey(GioiTinh.NU, 3, true, false, false, Relation.NONE, 0, Relation.ME), "Cụ bà ngoại");
        container.put(TenGoi.getKey(GioiTinh.NU, 3, false, false, false, Relation.NONE, 0, Relation.NONE), "Chắt");

        container.put(TenGoi.getKey(GioiTinh.NAM, 4, true, false, false, Relation.NONE, 0, Relation.CHA), "Kỵ ông nội");
        container.put(TenGoi.getKey(GioiTinh.NAM, 4, false, false, false, Relation.NONE, 0, Relation.NONE), "Chút");
        container.put(TenGoi.getKey(GioiTinh.NU, 4, true, false, false, Relation.NONE, 0, Relation.CHA), "Kỵ bà nội");
        container.put(TenGoi.getKey(GioiTinh.NU, 4, false, false, false, Relation.NONE, 0, Relation.NONE), "Chút");
        container.put(TenGoi.getKey(GioiTinh.NAM, 4, true, false, false, Relation.NONE, 0, Relation.ME), "Kỵ ông ngoại");
        container.put(TenGoi.getKey(GioiTinh.NAM, 4, false, false, false, Relation.NONE, 0, Relation.NONE), "Chút");
        container.put(TenGoi.getKey(GioiTinh.NU, 4, true, false, false, Relation.NONE, 0, Relation.ME), "Kỵ bà ngoại");
        container.put(TenGoi.getKey(GioiTinh.NU, 4, false, false, false, Relation.NONE, 0, Relation.NONE), "Chút");

        container.put(TenGoi.getKey(GioiTinh.NAM, 5, true, false, false, Relation.NONE, 0, Relation.CHA), "Tổ tiên nội");
        container.put(TenGoi.getKey(GioiTinh.NAM, 5, false, false, false, Relation.NONE, 0, Relation.NONE), "Chít");
        container.put(TenGoi.getKey(GioiTinh.NU, 5, true, false, false, Relation.NONE, 0, Relation.CHA), "Tổ tiên nội");
        container.put(TenGoi.getKey(GioiTinh.NU, 5, false, false, false, Relation.NONE, 0, Relation.NONE), "Chít");
        container.put(TenGoi.getKey(GioiTinh.NAM, 5, true, false, false, Relation.NONE, 0, Relation.ME), "Tổ tiên ngoại");
        container.put(TenGoi.getKey(GioiTinh.NAM, 5, false, false, false, Relation.NONE, 0, Relation.NONE), "Chít");
        container.put(TenGoi.getKey(GioiTinh.NU, 5, true, false, false, Relation.NONE, 0, Relation.ME), "Tổ tiên ngoại");
        container.put(TenGoi.getKey(GioiTinh.NU, 5, false, false, false, Relation.NONE, 0, Relation.NONE), "Chít");
    }

    public static CachGoiTen getInstance() {
        if (instance == null) {
            instance = new CachGoiTen();
        }
        return instance;
    }

    public String getName(GioiTinh gender, int level, boolean isHigher, boolean isOutSide) {
        return container.get(TenGoi.getKey(gender, level, isHigher, isOutSide));
    }

    public String getName(int gender, int level, boolean isHigher, boolean isOutSide) {
        return container.get(TenGoi.getKey(gender, level, isHigher, isOutSide));
    }

    public String getName(GioiTinh gender, int level, boolean isHigher, boolean isOutSide, boolean isParent) {
        return container.get(TenGoi.getKey(gender, level, isHigher, isOutSide, isParent));
    }

    public String getName(int gender, int level, boolean isHigher, boolean isOutSide, boolean isParent) {
        return container.get(TenGoi.getKey(gender, level, isHigher, isOutSide, isParent));
    }

    public String getName(int gender, int level, boolean isHigher, boolean isOutSide, boolean isParent, int relation, int isHigherParent, Relation sideRelation) {
        if (relation < 0) relation = 0;
        return container.get(TenGoi.getKey(GioiTinh.values()[gender], level, isHigher, isOutSide, isParent, Relation.values()[relation], isHigherParent, sideRelation));
    }

    public static void main(String[] args) {


    }

    public void saveMemberFromReading (TreeMap<Integer, DUploadMember> dUploadMemberTreeMap, PedigreeModel pedigreeModel) {
        nodeMemberService.deleteAllByPedigreeAndPatchKeyStartsWith(pedigreeModel, "r");
        Map<Integer, Integer> savedIdByKey = new HashMap<>();
        dUploadMemberTreeMap.forEach((key, value) -> {
            NodeMemberModel nodeMemberModel = new NodeMemberModel();
            DescriptionMemberModel descriptionMemberModel = new DescriptionMemberModel();
            if(value.getIdParent() == -1) {
                nodeMemberModel.setParent(null);
                nodeMemberModel.setPatchKey("r");
            }else {
                int idParent = savedIdByKey.get(value.getIdParent()) == null ? value.getId(): savedIdByKey.get(value.getIdParent());
                Optional<NodeMemberModel> parent = nodeMemberService.findById(idParent);
                nodeMemberModel.setParent(parent.get());
                nodeMemberModel.setPatchKey(NodeMemberModel.getPathkeyByParent(parent.get()));
            }
            nodeMemberModel.setName(value.getName());
            nodeMemberModel.setMotherFatherId(value.getIdMotherOrFather());
            nodeMemberModel.setGender(value.getGender().ordinal());
            nodeMemberModel.setChildIndex(value.getChildIdx());
            nodeMemberModel.setLifeIndex(value.getLiftIdx());
            nodeMemberModel.setImage(value.getImage());
            nodeMemberModel.setRelation(value.getRelation().ordinal());
            descriptionMemberModel.setNickName(value.getNickName());
            descriptionMemberModel.setDegree(value.getDegree());
            descriptionMemberModel.setDescription(value.getDes());
            descriptionMemberModel.setExtraData(value.getExtraData());
            descriptionMemberModel.setBirthday(value.getBirthDay());
            descriptionMemberModel.setDeadDay(value.getDeadDay());
            descriptionMemberModel.setAddress(value.getAddress());
            savedIdByKey.put(value.getId(), nodeMemberService.add(nodeMemberModel, descriptionMemberModel).getId());
        });
    }

    public TreeMap<Integer, DUploadMember> readExcel(String fileName) {
        TreeMap<Integer, DUploadMember> listMember = new TreeMap<>();
        try {
            FileInputStream fs = new FileInputStream(Paths.get("upload-dir") + "/1556712756277update.xls");
            Workbook wb = Workbook.getWorkbook(fs);
            Sheet sh = wb.getSheet(0);
            int totalRows = sh.getRows();
            int totalColumns = sh.getColumns();
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
                result = Relation.VO;
                break;
            case "chong":
            case "chồng":
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
}