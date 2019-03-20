package com.example.genealogy.controller;

import com.example.genealogy.Utils.ConvertUtils;
import com.example.genealogy.model.UserModel;
import com.example.genealogy.service.StorageService;
import com.example.genealogy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
public class AccountController {

    @Autowired
    StorageService storageService;

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(
            @ModelAttribute(value = "user")
                    UserModel user, HttpServletRequest request) {
        System.out.println(user.toString());
        ModelAndView mv;
        mv = new ModelAndView("account/login");
        return mv;
    }

    @RequestMapping(value = "/account/profile", method = RequestMethod.GET)
    public ModelAndView profile(HttpServletRequest request) {
        UserModel userModel = userService.findUserByEmail(request.getUserPrincipal().getName());
        ModelAndView mv = new ModelAndView("account/profile");
        mv.addObject("user", userModel);
        return mv;
    }

    @RequestMapping(value = "/account/profile/edit", method = RequestMethod.GET)
    public ModelAndView editProfile(HttpServletRequest request){
        UserModel userModel = userService.findUserByEmail(request.getUserPrincipal().getName());
        ModelAndView mv = new ModelAndView("account/profile-edit");
        mv.addObject("user", userModel);
        return mv;
    }

    @PostMapping(value = "/account/profile/edit")
    public ResponseEntity<?> editProfile(Principal principal,
                                         @RequestParam(value = "name", required = true, defaultValue = "") String name,
                                         @RequestParam(value = "phone", required = true, defaultValue = "") String phone,
                                         @RequestParam(value = "email", required = true, defaultValue = "") String email,
                                         @RequestParam(value = "address", required = true, defaultValue = "") String address,
                                         @RequestParam(value = "birthday", required = true, defaultValue = "") String birthday,
                                         @RequestParam("img") MultipartFile img
                                         ){
        UserModel userModel = userService.findUserByEmail(principal.getName());
        if(userModel != null) {
            userModel.setName(name);
            userModel.setPhone(phone);
            userModel.setEmail(email);
            userModel.setAddress(address);
            userModel.setBirthday(ConvertUtils.getDate(birthday));
            String uploadedFileName = img.getOriginalFilename();
            if(!uploadedFileName.isEmpty()){
                uploadedFileName = +  userModel.getId() + "_" + System.currentTimeMillis() + uploadedFileName.substring(uploadedFileName.lastIndexOf("."));
                String fileImg = "img/"  + uploadedFileName;
                storageService.store(img,fileImg);
                userModel.setImage("/image/" + uploadedFileName);
            }
            userService.saveUser(userModel);
        }else {
            return new ResponseEntity<>("NO" , HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("OK" , HttpStatus.OK);
    }
}
