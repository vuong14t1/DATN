package com.vuongpq2.datn.rest;

import com.vuongpq2.datn.model.UserModel;
import com.vuongpq2.datn.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

@Controller
public class UserRestController {

    @Autowired
    UserRepository userRepository;

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
}
