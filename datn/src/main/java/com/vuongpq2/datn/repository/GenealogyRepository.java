package com.vuongpq2.datn.repository;

import com.vuongpq2.datn.model.GenealogyModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GenealogyRepository extends JpaRepository<GenealogyModel, Integer> {
    List<GenealogyModel> findAll(Sort sort);

//    List<GenealogyModel> findAll(Iterable<Long> iterable);

    Page<GenealogyModel> findAll(Pageable pageable);

    List<GenealogyModel> findAllByNameLike(String search);

}
