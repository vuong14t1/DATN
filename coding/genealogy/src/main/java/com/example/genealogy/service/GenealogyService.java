package com.example.genealogy.service;

import com.example.genealogy.model.GenealogyModel;

import java.util.List;
import java.util.Optional;

public interface GenealogyService {
    List<GenealogyModel> findAll();
    List<GenealogyModel> findAllByEmailUser(String emailUser);
    List<GenealogyModel> findAllByLike(String search);

    void create(GenealogyModel genealogyModel, String email);

    void update(GenealogyModel genealogyModel);

    GenealogyModel findById(Integer id);

    boolean delete(GenealogyModel genealogyModel);
    boolean delete(Integer id);
}
