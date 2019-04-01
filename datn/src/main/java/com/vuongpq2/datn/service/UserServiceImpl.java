package com.vuongpq2.datn.service;

import com.vuongpq2.datn.data.Enum.NameRole;
import com.vuongpq2.datn.data.Enum.UserStatus;
import com.vuongpq2.datn.model.RoleModel;
import com.vuongpq2.datn.model.UserModel;
import com.vuongpq2.datn.repository.RoleRepository;
import com.vuongpq2.datn.repository.UserPermissionRepository;
import com.vuongpq2.datn.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserPermissionRepository userPermissionRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public List<UserModel> findAll() {
        return userRepository.findAll();
    }

    @Override
    public UserModel findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserModel findUserByUserId(int userId) {
        return userRepository.findById(userId);
    }

    @Override
    public void saveUser(UserModel user) {
        //encode
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(UserStatus.ACTIVE.getCode());
        RoleModel roleByName = roleRepository.findByName(NameRole.USER);
        user.setRoles(new HashSet<>(Arrays.asList(roleByName)));
        userRepository.save(user);
    }

    @Override
    public void saveUser(UserModel user, String nameRole) {
        //encode
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(UserStatus.ACTIVE.getCode());
        RoleModel roleByName = roleRepository.findByName(nameRole);
        user.setRoles(new HashSet<RoleModel>(Arrays.asList(roleByName)));
        userRepository.save(user);
    }

    @Override
    public void deleteUser(UserModel userModel) {
        if(userModel == null) {
            System.out.println("user Model null");
            return;
        }
        userPermissionRepository.deleteAllByUserId(userModel.getId());
        userRepository.delete(userModel);
    }

    @Override
    public void deleteUserById(int userId) {
        userPermissionRepository.deleteAllByUserId(userId);
        userRepository.deleteById(userId);
    }

    @Override
    public void deleteUserByEmail(String email) {
        userRepository.deleteByEmail(email);
    }
}
