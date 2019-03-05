package com.example.genealogy.repository;

import com.example.genealogy.model.PermissionModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<PermissionModel, Integer> {
    PermissionModel findByCode(Integer code);
}
