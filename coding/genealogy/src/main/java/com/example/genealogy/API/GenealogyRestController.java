//package com.example.genealogy.API;
//
//import com.example.genealogy.data.model.DGenealogyModel;
//import com.example.genealogy.model.GenealogyModel;
//import com.example.genealogy.model.UserModel;
//import com.example.genealogy.repository.UserRepository;
//import com.example.genealogy.service.GenealogyService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.util.HtmlUtils;
//
//import java.security.Principal;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//
//@RestController
//@Transactional
//public class GenealogyRestController {
//
//    @Autowired
//    GenealogyService genealogyService;
//
//
//    @Autowired
//    UserRepository userRepository;
//
//    @GetMapping(value = "/rest/genealogy/list", produces = "application/json")
//    public Collection<DGenealogyModel> getAll(Principal principal) {
//        System.out.println("atuhen" + principal.getName());
//        return null;
////        UserModel userModel = userRepository.findByEmail(principal.getName());
////        List<GenealogyModel> all = genealogyService.findAllByEmailUser(principal.getName());
////        List<DGenealogyModel> result = new ArrayList<>();
////        for (GenealogyModel gene : all) {
////            DGenealogyModel item = new DGenealogyModel();
////            item.setId(gene.getId());
////            item.setName(gene.getName());
////            String his = gene.getHistory();
////            int length = his.length();
////            if(length>50) length = 50;
////            his = his.substring(0,length);
////            item.setHistory(HtmlUtils.htmlEscape(his));
////            item.setStatus(1);
////            result.add(item);
////        }
////        return result;
//    }
//
//
//    private ResponseEntity<?> notHavePermisstion(){
//        return new ResponseEntity<>("" , HttpStatus.NOT_FOUND);
//    }
//}
