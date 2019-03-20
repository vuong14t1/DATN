package com.vuongpq2.datn.service;

import com.vuongpq2.datn.model.UserModel;

import java.util.List;

public interface UserService {
    List<UserModel> findAll();
    UserModel findUserByEmail(String email);
    UserModel findUserByUserId(int userId);

    void saveUser(UserModel user);
    void saveUser(UserModel user, String nameRole);

    void deleteUser(UserModel userModel);
    void deleteUserById(int userId);
    void deleteUserByEmail(String email);

}
