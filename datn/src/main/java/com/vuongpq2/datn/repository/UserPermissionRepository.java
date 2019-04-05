package com.vuongpq2.datn.repository;

import com.vuongpq2.datn.model.UserModel;
import com.vuongpq2.datn.model.UserPermissionModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserPermissionRepository extends JpaRepository<UserPermissionModel, Integer> {
    List<UserPermissionModel> findAll(Sort sort);
    Page<UserPermissionModel> findAll(Pageable pageable);

    List<UserPermissionModel> findAllByUser(UserModel userModel);

    List<UserPermissionModel> findByUser(UserModel userModel);

    List<UserPermissionModel> findByUserAndGenealogy_Id(UserModel userModel, Integer genealogyId);

    List<UserPermissionModel> findByGenealogy_Id(Integer genealogyId);

    UserPermissionModel findTopByUserAndGenealogy_Id(UserModel user, Integer genealogyId);

    void deleteAllByGenealogyId(Integer id);
    void deleteAllByUserId(Integer id);
}
