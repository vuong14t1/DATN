package com.vuongpq2.datn.repository;

import com.vuongpq2.datn.model.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleModel, Integer> {
    RoleModel findByName(String name);
}
