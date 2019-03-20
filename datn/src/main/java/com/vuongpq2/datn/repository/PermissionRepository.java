package com.vuongpq2.datn.repository;

import com.vuongpq2.datn.model.PermissionModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<PermissionModel, Integer> {
    PermissionModel findByCode(Integer code);
}
