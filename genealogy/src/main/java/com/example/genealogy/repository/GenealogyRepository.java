package com.example.genealogy.repository;

import com.example.genealogy.model.GenealogyModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GenealogyRepository extends JpaRepository<GenealogyModel, Integer> {
    List<GenealogyModel> findAll(Sort sort);

//    List<GenealogyModel> findAll(Iterable<Long> iterable);

    Page<GenealogyModel> findAll(Pageable pageable);

    List<GenealogyModel> findAllByNameLike(String search);

}
