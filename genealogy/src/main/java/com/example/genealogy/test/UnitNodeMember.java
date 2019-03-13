package com.example.genealogy.test;

import com.example.genealogy.model.DescriptionMemberModel;
import com.example.genealogy.model.GenealogyModel;
import com.example.genealogy.model.NodeMemberModel;
import com.example.genealogy.model.PedigreeModel;
import com.example.genealogy.service.GenealogyService;
import com.example.genealogy.service.NodeMemberService;
import com.example.genealogy.service.PedigreeService;
import jxl.Sheet;
import jxl.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
public class UnitNodeMember {
    @Autowired
    private GenealogyService genealogyService;

    @Autowired
    private PedigreeService pedigreeService;

    @Autowired
    private NodeMemberService nodeMemberService;

    @GetMapping("/node_m/create")
    public ModelAndView create(){
        Workbook workbook;
        try {
            workbook = Workbook.getWorkbook(new File("F:\\DATN\\source\\genealogy\\src\\main\\resources\\uploadFile\\data.xls"));
            Sheet sheet = workbook.getSheet("Sheet1");
            int rows =  sheet.getRows();
            for (int row = 1; row < rows; row++) {
                String id = sheet.getCell(0, row).getContents();
                String pathKey = sheet.getCell(1, row).getContents();
                String idParent = sheet.getCell(2, row).getContents();
                String idMonther = sheet.getCell(3, row).getContents();
                String liftIndex = sheet.getCell(4, row).getContents();
                String name = sheet.getCell(5, row).getContents();
                String relation = sheet.getCell(6, row).getContents();
                Optional<PedigreeModel> pedigreeModel = pedigreeService.findById(23);
                NodeMemberModel nodeMemberModel = new NodeMemberModel();
//                nodeMemberModel.setId(Integer.valueOf(id));
                nodeMemberModel.setName(name);
                nodeMemberModel.setLifeIndex(Integer.valueOf(liftIndex));
                nodeMemberModel.setGender(1);
                nodeMemberModel.setImage("image");
                nodeMemberModel.setPedigree(pedigreeModel.get());
                if(idMonther.equals("")){
                    nodeMemberModel.setMotherId(-1);
                }else {
                    nodeMemberModel.setMotherId(Integer.valueOf(idMonther));
                }
                nodeMemberModel.setPatchKey(pathKey);
                if(idParent.equals("")) {
                    nodeMemberModel.setParent(null);
                }else {
//                    List<NodeMemberModel> parent =  nodeMemberService.findAllByPedigreeAndPatchKey(pedigreeModel.get(), pathKey);
//                    nodeMemberModel.setParent(parent.get());
                }
                nodeMemberModel.setChildIndex(1);
                nodeMemberModel.setRelation(Integer.valueOf(relation));
                DescriptionMemberModel descriptionMemberModel = new DescriptionMemberModel();
                descriptionMemberModel.setAddress("address " + row);
                descriptionMemberModel.setNickName("nick name " + row);

                nodeMemberService.add(nodeMemberModel, descriptionMemberModel);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ModelAndView("home");
    }

    @GetMapping("/node_m/delete")
    public ModelAndView deleteAll() {
        nodeMemberService.deleteAll();
        return new ModelAndView("home");
    }

    @GetMapping("/node_m/get")
    public ModelAndView get() {
        Optional<NodeMemberModel> nodeMemberModel = nodeMemberService.findById(175);
        return new ModelAndView("home");
    }
}
