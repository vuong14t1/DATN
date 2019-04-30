package com.vuongpq2.datn.controller;

import com.vuongpq2.datn.data.Enum.NameRole;
import com.vuongpq2.datn.model.UserModel;
import com.vuongpq2.datn.repository.UserRepository;
import com.vuongpq2.datn.service.StorageService;
import com.vuongpq2.datn.service.UserService;
import com.vuongpq2.datn.utils.MyUltils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
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
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    StorageService storageService;



    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @GetMapping("/confirm")
    public String confirm() {
        return "/account/confirm";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(
            @ModelAttribute(value = "user")
                    UserModel user, HttpServletRequest request) {
        ModelAndView mv;
        mv = new ModelAndView("/account/login");
        return mv;
    }


    @GetMapping(value = "/registration")
    public ModelAndView getRegistration() {
        ModelAndView mv = new ModelAndView("/account/register");
        UserModel userModel = new UserModel();
        mv.addObject("user", userModel);
        mv.addObject("successMessage", "");
        return mv;
    }

    @PostMapping(value = "/registration", produces = "application/json")
    public ModelAndView postRegistration(@ModelAttribute("user")UserModel userModel) {
        UserModel findUser = userService.findUserByEmail(userModel.getEmail());
        if(findUser != null) {
            ModelAndView mv = new ModelAndView("/account/register");
            mv.addObject("successMessage", "Tên email đã tồn tại!");
            return mv;
        }
        ModelAndView mv = new ModelAndView();
        UserModel newUser = new UserModel();
        newUser.setEmail(userModel.getEmail());
        newUser.setName(userModel.getName());
        newUser.setPassword(userModel.getPassword());
        userService.saveUser(newUser, NameRole.USER);
        mv.setViewName("redirect:/genealogy");
//        mv.addObject("user", userModel);
        return mv;
    }

    @GetMapping(value = "/profile")
    public ModelAndView viewProfile (Principal principal) {
        UserModel userModel = userService.findUserByEmail(principal.getName());
        ModelAndView mv = new ModelAndView("/account/profile");
        mv.addObject("user", userModel);
        return mv;
    }

    @GetMapping(value = "/profile/edit")
    public ModelAndView editProfile (Principal principal) {
        UserModel userModel = userService.findUserByEmail(principal.getName());
        ModelAndView mv = new ModelAndView("/account/profile-edit");
        mv.addObject("user", userModel);
        return mv;
    }

    @PostMapping(value = "/profile/edit", produces = "application/json")
    public ResponseEntity<?> editProfile (Principal principal,
                                          @RequestParam(value = "name", required = true, defaultValue = "") String name,
                                          @RequestParam(value = "phone", required = true, defaultValue = "") String phone,
                                          @RequestParam(value = "email", required = true, defaultValue = "") String email,
                                          @RequestParam(value = "address", required = true, defaultValue = "") String address,
                                          @RequestParam(value = "birthday", required = true, defaultValue = "") String birthday,
                                          @RequestParam("img") MultipartFile img
    ) {
        UserModel findUser = userService.findUserByEmail(principal.getName());
        findUser.setName(name);
        findUser.setPhone(phone);
        findUser.setAddress(address);
        String uploadedFileName = img.getOriginalFilename();
        if(!uploadedFileName.isEmpty()){
            uploadedFileName = System.currentTimeMillis() + MyUltils.getExtension(uploadedFileName);
            String fileImg = "img/"  + uploadedFileName;
            storageService.store(img,fileImg);
            findUser.setImage("/image/" + uploadedFileName);
        }
        findUser.setEmail(email);
        findUser.setBirthday(MyUltils.getDate(birthday));
        userRepository.save(findUser);

        return new ResponseEntity<>("success" , HttpStatus.OK);
    }

    @PostMapping(value = "/profile/changePass", produces = "application/json")
    public ResponseEntity<?> changePassProfile (Principal principal,
                                                @RequestParam(value = "oldPass", required = true, defaultValue = "") String oldPass,
                                                @RequestParam(value = "newPass", required = true, defaultValue = "") String newPass,
                                                @RequestParam(value = "renewPass", required = true, defaultValue = "") String renewPass) {
        UserModel userModel = userService.findUserByEmail(principal.getName());
        if(!bCryptPasswordEncoder.matches(oldPass, userModel.getPassword())) {
            return new ResponseEntity<>("3" , HttpStatus.OK);
        }
        if(!newPass.equals(renewPass)) {
            return new ResponseEntity<>("2" , HttpStatus.OK);
        }
        userModel.setPassword(bCryptPasswordEncoder.encode(newPass));
        userRepository.save(userModel);
        return new ResponseEntity<>("1" , HttpStatus.OK);
    }
}
