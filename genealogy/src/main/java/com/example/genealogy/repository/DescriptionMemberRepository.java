package com.example.genealogy.repository;

import com.example.genealogy.model.DescriptionMemberModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DescriptionMemberRepository extends JpaRepository<DescriptionMemberModel, Long> {
}
