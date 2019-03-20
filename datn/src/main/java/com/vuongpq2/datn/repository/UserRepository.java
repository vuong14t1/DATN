package com.vuongpq2.datn.repository;

import com.vuongpq2.datn.model.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<UserModel, Integer> {
    UserModel findByEmail(String email);
    UserModel findById(int id);
    Page<UserModel> findAll(Pageable pageable);
    List<UserModel> findAll();
    void deleteByEmail(String email);
}
