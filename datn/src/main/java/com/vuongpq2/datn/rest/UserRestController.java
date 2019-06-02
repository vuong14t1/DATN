package com.vuongpq2.datn.rest;

import com.vuongpq2.datn.data.model.DGenealogyModel;
import com.vuongpq2.datn.data.model.DMemberUser;
import com.vuongpq2.datn.model.GenealogyModel;
import com.vuongpq2.datn.model.UserModel;
import com.vuongpq2.datn.repository.UserRepository;
import com.vuongpq2.datn.service.GenealogyService;
import com.vuongpq2.datn.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import java.security.Principal;
import java.util.*;

@Controller
public class UserRestController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;
    @Autowired
    GenealogyService genealogyService;

    @RequestMapping(value = "/avatar", method = RequestMethod.GET)
    public ResponseEntity<String> getImage(
            Principal principal
    ){

        UserModel userModel = userRepository.findByEmail(principal.getName());
        if(userModel.getImage() == null || userModel.getImage().equals("")){
            return  new ResponseEntity<>("/dist/img/user2-160x160.jpg", HttpStatus.OK);
        }else{
            return  new ResponseEntity<>(userModel.getImage(), HttpStatus.OK);
        }
    }

    @GetMapping(value = "/admin/account/findAll")
    public Collection<UserModel> getALl() {

        return userService.findAll();
    }

    @GetMapping(value = "/admin/account/findAllBy", produces = "application/json")
    public Collection<UserModel> getAllBy() {

        return userService.findAll();
    }

    @GetMapping(value = "/rest/admin/account/list")
    public ResponseEntity<?> getAllAccount(Authentication authentication) {

        if (!isHavePermission(authentication)) return notHavePermisstion();


        List<UserModel> all    = userService.findAll();
        List<DMemberUser>  result = new ArrayList<>();
        for (UserModel u : all) {
            DMemberUser item = new DMemberUser();
            item.setId(u.getId());
            item.setName(u.getName());
            item.setEmail(u.getEmail());
            result.add(item);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);

    }
    @PostMapping(value = "/rest/admin/account/delete/{id}")
    public ResponseEntity<?> getAllAccount(Authentication authentication, @PathVariable(name = "id") int id) {

        if (!isHavePermission(authentication)) return notHavePermisstion();
        UserModel user = userRepository.findById(id);
        userRepository.delete(user);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @PostMapping(value = "/admin/account/")
    public ResponseEntity<String> editAccount(UserModel userModel) {

        UserModel userById = userService.findUserByUserId(userModel.getId());
        BeanUtils.copyProperties(userModel, userById, getNullPropertyNames(userModel));
        System.out.println(userModel.toString());
        System.out.println(userById.toString());
        return ResponseEntity.status(HttpStatus.OK).build();

    }

    @GetMapping(value = "/rest/admin/genealogy/list")
    public ResponseEntity<?> getAllGenealogy(Authentication authentication) {

        if (!isHavePermission(authentication)) return notHavePermisstion();

        List<GenealogyModel> all    = genealogyService.findAll();
        List<DGenealogyModel>  result = new ArrayList<>();
        for (GenealogyModel gene : all) {
            DGenealogyModel item = new DGenealogyModel();
            item.setId(gene.getId());
            item.setName(gene.getName());
            String his    = gene.getHistory();
            int    length = his.length();
            if (length > 50) length = 50;
            his = his.substring(0, length);
            item.setHistory(HtmlUtils.htmlEscape(his));
            result.add(item);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);

    }

    public static String[] getNullPropertyNames(Object source) {

        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }


    private boolean isHavePermission(Authentication authentication) {

        if (authentication != null) {
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            for (GrantedAuthority authority : authorities) {
                if (authority.getAuthority()
                        .equals("ROLE_ADMIN")) {
                    return true;
                } else if (authority.getAuthority()
                        .startsWith("ROLE_USER")) {
                    return false;
                }
            }
        }
        return false;
    }

    private ResponseEntity<?> notHavePermisstion() {
//        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
