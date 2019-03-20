package com.example.genealogy.API;

import com.example.genealogy.data.model.DGenealogyModel;
import com.example.genealogy.model.GenealogyModel;
import com.example.genealogy.service.GenealogyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@Transactional
//@EnableAutoConfiguration
public class GenealogyAPI {
    @Autowired
    private GenealogyService genealogyService;

    @GetMapping(name = "/api/genealogy/list", produces = "application/json")
    public Collection<DGenealogyModel> getAll(Principal principal){
        System.out.println("vao day authen " + principal.getName());
        List<GenealogyModel> genealogyModelList = genealogyService.findAllByEmailUser(principal.getName());

        List<DGenealogyModel> result = new ArrayList<>(1);
        for(GenealogyModel genealogyModel: genealogyModelList) {
            DGenealogyModel dGe = new DGenealogyModel();
            dGe.setId(genealogyModel.getId());
            dGe.setName(genealogyModel.getName());
            dGe.setHistory(genealogyModel.getHistory());
            dGe.setStatus(1);
            result.add(dGe);
        }
        System.out.println("vao day");
        return result;
    }
}
