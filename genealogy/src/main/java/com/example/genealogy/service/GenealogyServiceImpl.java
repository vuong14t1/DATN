package com.example.genealogy.service;

import com.example.genealogy.data.Enum.Permission;
import com.example.genealogy.model.GenealogyModel;
import com.example.genealogy.model.PermissionModel;
import com.example.genealogy.model.UserModel;
import com.example.genealogy.model.UserPermissionModel;
import com.example.genealogy.repository.GenealogyRepository;
import com.example.genealogy.repository.PermissionRepository;
import com.example.genealogy.repository.UserPermissionRepository;
import com.example.genealogy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GenealogyServiceImpl implements GenealogyService {

    @Autowired
    private GenealogyRepository genealogyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserPermissionRepository userPermissionRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public List<GenealogyModel> findAll() {
        return genealogyRepository.findAll();
    }

    @Override
    public List<GenealogyModel> findAllByEmailUser(String emailUser) {
        //tim user
        UserModel userModel = userRepository.findByEmail(emailUser);
        List<UserPermissionModel> userPermissionModels = userPermissionRepository.findAllByUser(userModel);
        List<GenealogyModel> genealogyModels = new ArrayList<>(1);
        for (UserPermissionModel upm: userPermissionModels) {
            genealogyModels.add(upm.getGenealogyModel());
        }
        return genealogyModels;
    }

    @Override
    public List<GenealogyModel> findAllByLike(String search) {
        return genealogyRepository.findAllByNameLike(search);
    }

    @Override
    public void create(GenealogyModel genealogyModel, String email) {
        if(genealogyModel == null) {
            System.out.println("genealogy null");
            return;
        }
        //TODO thu TH chi luu userpermission thoi xem thu no luu may cai kia khong
        UserPermissionModel userPermissionModel = new UserPermissionModel();
        genealogyRepository.save(genealogyModel);
        UserModel userModel = userRepository.findByEmail(email);
        PermissionModel permissionModel = permissionRepository.findByCode(Permission.ADMIN.getCode());
        if(permissionModel == null) {
            permissionModel = new PermissionModel();
            permissionModel.setName("ADMIN");
            permissionModel.setCode(Permission.ADMIN.getCode());
            permissionRepository.save(permissionModel);
        }
        userPermissionModel.setGenealogyModel(genealogyModel);
        userPermissionModel.setUser(userModel);
        userPermissionModel.setPermission(permissionModel);
        userPermissionRepository.save(userPermissionModel);
    }

    @Override
    public void update(GenealogyModel genealogyModel) {
        genealogyRepository.save(genealogyModel);
    }

    @Override
    public Optional<GenealogyModel> findById(Integer id) {
        return genealogyRepository.findById(id);
    }

    @Override
    public boolean delete(GenealogyModel genealogyModel) {
        genealogyRepository.delete(genealogyModel);
        return false;
    }

    @Override
    public boolean delete(Integer id) {
        genealogyRepository.deleteById(id);
        return false;
    }
}
