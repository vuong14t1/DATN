package com.vuongpq2.datn.rest;

import com.vuongpq2.datn.config.tree.ChartConfig;
import com.vuongpq2.datn.config.tree.Child;
import com.vuongpq2.datn.data.Enum.Permission;
import com.vuongpq2.datn.data.model.DMemberUser;
import com.vuongpq2.datn.model.*;
import com.vuongpq2.datn.repository.DescriptionMemberRepository;
import com.vuongpq2.datn.repository.PermissionRepository;
import com.vuongpq2.datn.repository.UserPermissionRepository;
import com.vuongpq2.datn.repository.UserRepository;
import com.vuongpq2.datn.service.GenealogyService;
import com.vuongpq2.datn.service.NodeMemberService;
import com.vuongpq2.datn.service.PedigreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
@Transactional
public class MemberUserRestController {

    @Autowired
    private NodeMemberService nodeMemberService;

    @Autowired
    private DescriptionMemberRepository descriptionMemberRepository;

    @Autowired
    private GenealogyService genealogyService;

    @Autowired
    private UserPermissionRepository userPermissionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PedigreeService pedigreeService;

    @Autowired
    private PermissionRepository permissionRepository;

    @GetMapping(value = "/rest/genealogy/{idGenealogy}/member-user", produces = "application/json")
    public Collection<DMemberUser> getAllMemberByIdGenealogy (Principal principal, @PathVariable(value = "idGenealogy") int idGenealogy) {
        List<DMemberUser> members = new ArrayList<>(1);
        List<UserPermissionModel> userPermissionModels = userPermissionRepository.findByGenealogy_Id(idGenealogy);
        for (UserPermissionModel userPermissionModel: userPermissionModels) {
            DMemberUser dMemberUser = new DMemberUser();
            dMemberUser.setId(userPermissionModel.getId());
            dMemberUser.setIdUser(userPermissionModel.getUser().getId());
            dMemberUser.setEmail(userPermissionModel.getUser().getEmail());
            dMemberUser.setName(userPermissionModel.getUser().getName());
            dMemberUser.setPermission(userPermissionModel.getPermission().getCode());
            members.add(dMemberUser);
        }
        return members;
    }

    @PostMapping(value = "/rest/genealogy/member-user/update", produces = "application/json")
    public ResponseEntity<?> updateMember(Principal principal,
                                          @RequestParam(value = "idUserPermisson") int idUserPermisson,
                                          @RequestParam(value = "idPermission") int idPermission
                                          ) {
        PermissionModel permissionModel = permissionRepository.findByCode(idPermission);
        if(permissionModel == null) {
            permissionModel = new PermissionModel();
            permissionModel.setCode(idPermission);
            switch (idPermission){
                case 0:
                    permissionModel.setName("UNREGISTER");
                    break;
                case 1:
                    permissionModel.setName("ADMIN");
                    break;
                case 2:
                    permissionModel.setName("MOD");
                    break;
                case 3:
                    permissionModel.setName("REGISTER");
                    break;
                case 4:
                    permissionModel.setName("MEMBER");
                    break;
            }
            permissionRepository.save(permissionModel);
        }
        Optional<UserPermissionModel> userPermissionModel = userPermissionRepository.findById(idUserPermisson);
        if(idPermission == Permission.NO_REGISTER.ordinal()) {
            userPermissionRepository.delete(userPermissionModel.get());
        }else {
            userPermissionModel.get().setPermission(permissionModel);
            userPermissionRepository.save(userPermissionModel.get());
        }
        return ResponseEntity.ok("success");
    }

}
